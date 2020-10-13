package xin.marcher.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项目启动类
 *
 * @author marcher
 */
@ComponentScan(basePackages = {"xin.marcher.framework.*", "xin.marcher.*"})
@SpringBootApplication
public class OssApplication {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start <<<<<<<<<<<<<<<<<<<<<<<<<<");
        SpringApplication.run(OssApplication.class, args);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> started <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


}