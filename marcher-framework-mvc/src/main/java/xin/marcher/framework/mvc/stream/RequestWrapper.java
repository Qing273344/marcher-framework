package xin.marcher.framework.mvc.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import xin.marcher.framework.mvc.utils.StreamConvert;
import xin.marcher.framework.common.util.EmptyUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 包装 HttpServletRequest 实现请求流可重复读
 *
 * @author marcher
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * body数据
     */
    private final byte[] body;

    /**  Map 集合, FROM 表单形式复制一份数据 */
    private Map<String, List<String>> paramHashValues;


    public RequestWrapper(HttpServletRequest request) throws IOException {
        this(request, false);
    }

    public RequestWrapper(HttpServletRequest request, boolean isWrite) throws IOException {
        super(request);
        // 将 body 数据存储起来
        body = StreamUtils.copyToByteArray(request.getInputStream());
        if (isWrite) {
            setParameterMap();
        }

        // url 上参数解析
        parseUrlParams(request.getParameterMap());
    }

    private void parseUrlParams(Map<String, String[]> parameterMap) {
        if (EmptyUtil.isEmpty(parameterMap)) {
            return;
        }

        if (EmptyUtil.isEmpty(paramHashValues)) {
            paramHashValues = new HashMap<>(4);
        }

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            List<String> values = paramHashValues.computeIfAbsent(key, k -> new ArrayList<>());
            values.addAll(Arrays.asList(value));
        }
    }

    private void setParameterMap() {
        paramHashValues = StreamConvert.form2Map(this);
    }

    public byte[] getBody() {
        return this.body;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    /**
     * 获取所有参数名
     *
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        if (EmptyUtil.isEmpty(paramHashValues)) {
            return null;
        }
        Vector<String> vector = new Vector<>(paramHashValues.keySet());
        return vector.elements();
    }

    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     *
     * @param name 指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        if (EmptyUtil.isEmpty(paramHashValues)) {
            return null;
        }

        List<String> values = paramHashValues.get(name);
        if (values == null) {
            return null;
        }
        return values.get(0);
    }

    /**
     * 获取指定参数名的所有值的数组，如：checkbox 的所有数据
     * 接收数组变量 ，如 checkobx 类型
     */
    @Override
    public String[] getParameterValues(String name) {
        if (EmptyUtil.isEmpty(paramHashValues)) {
            return null;
        }
        List<String> values = paramHashValues.get(name);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[0]);
    }


    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream;

        if (this.body.length <= 0) {
            byte[] bytes = "{}".getBytes();
            inputStream = new ByteArrayInputStream(bytes);
        } else {
            inputStream = new ByteArrayInputStream(body);
        }

        return new ServletInputStream() {
            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

}
