package xin.marcher.framework.z.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池工具类
 * 不建议使用Executors, Executors默认使用的是无界LinkedBlockingQueue, 在其执行offer时可能会出现OOM情况
 *
 * @author marcher
 */
public class ThreadPoolManager {

    /**
     * 是否开启调度
     */
    private static final boolean HAS_OPEN_DISPATCH = true;

    /**
     * 线程池保有的最小线程数
     */
    private static final int CORE_POOL_SIZE = 3;

    /**
     * 表示线程池创建的最大线程数. 空闲时只会到corePoolSize.
     */
    private static final int MAXIMUM_POOL_SIZE = 5;

    /**
     * 当线程数量超过了corePoolSize指定的线程数, 并且空闲线程空闲的时间达到当前参数指定的时间该线程就会被销毁.
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 工作队列大小
     */
    private static final int WORK_QUEUE_SIZE = 300;

    /**
     * 任务调度周期
     */
    private static final int TASK_DISPATCH_QOS = 1000;

    /**
     * 任务缓冲队列
     */
    private final BlockingQueue<Runnable> bufferQueue = new LinkedBlockingQueue<>(1024);

    private ThreadPoolExecutor threadPool;

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    private ThreadPoolManager() {
        threadPool = threadPool();
        // 开启调度任务
        if (HAS_OPEN_DISPATCH) {
            taskDispatchSchedule();
        }
    }

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    /**
     * 将未分配到线程的任务存入缓存队列中
     * 当前任务队列已满, 并且没有可用线程执行任务时对新添加的任务的处理策略
     */
    private final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
            bufferQueue.offer(task);
        }
    };

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool() {
        /*
         * 线程起个有意义的名称
         */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();

        /*
         * 构造一个线程池
         * corePoolSize:    线程池保有的最小线程数.
         * maximumPoolSize: 表示线程池创建的最大线程数. 空闲时只会到corePoolSize.
         * keepAliveTime:   线程池中非核心线程限制超市的时长, 当线程数量超过了corePoolSize指定的线程数, 并且空闲线程空闲的时间达到当前参数指定的时间该线程就会被销毁.
         *                      如果调用过allowCoreThreadTimeOut(boolean value)方法允许核心线程过期, 那么该策略针对核心线程也是生效的.
         * unit:            指定了keepAliveTimed的单位, 毫秒/秒/分/小时...
         * bufferQueue:       工作队列, 存储未执行的任务队列.
         * threadFactory:   创建线程的工厂, 如果未指定则使用默认的线程工厂.
         * handler:         指定了当前任务队列已满, 并且没有可用线程执行任务时对新添加的任务的处理策略.
         *      ThreadPoolExecutor提供四种策略:
         *          1. CallerRunsPolicy: 提交任务的线程自己去执行该任务
         *          2. AbortPolicy: 默认的拒绝策略, 会throws RejectedExecutionException.
         *          3. DiscardPolicy: 直接丢弃任务, 没有任何异常抛出.
         *          4. DiscardOldestPolicy: 丢弃最老的任务, 把心任务加入到工作队列中.
         */
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE), namedThreadFactory, handler);
    }

    /**
     * 缓存队列中的任务重新加载到线程池
     */
    private final Runnable loadTask = new Runnable() {
        @Override
        public void run() {
            if (hasMoreTask()) {
                threadPool.execute(bufferQueue.poll());
            }
        }
    };

    /**
     * 任务调度线程
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 任务调度
     *
     * 启动一个线程将缓存队列中的任务加载到线程池中
     */
    private void taskDispatchSchedule() {
        /*
         * 通过调度线程周期性的执行缓冲队列中任务
         */
        scheduler.scheduleAtFixedRate(loadTask, 0, TASK_DISPATCH_QOS, TimeUnit.MILLISECONDS);
    }


    /**
     * 队列中是否有任务
     *
     * @return
     *      true: 有 false: 无
     */
    private boolean hasMoreTask() {
        return !bufferQueue.isEmpty();
    }

    /**
     * 添加任务
     *
     * @param task  任务
     */
    public void addTask(Runnable task) {
        if (null != task) {
            threadPool.execute(task);
        }
    }

    /**
     * 停止
     *
     * 1. 停止接收外部submit的任务
     * 2. 内部正在跑的任务和队列里等待的任务，会执行完
     * 3. 等到第二步完成后，才真正停止
     */
    public void shutdown() {
        // 停止线程池
        threadPool().shutdown();

        // 停止调度线程
        if (HAS_OPEN_DISPATCH && !scheduler.isShutdown()) {
            while (true) {
                if (!hasMoreTask()) {
                    scheduler.shutdown();
                    break;
                }
            }
        }
    }
}
