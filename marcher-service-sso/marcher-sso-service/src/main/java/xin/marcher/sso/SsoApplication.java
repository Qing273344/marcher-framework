package xin.marcher.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项目启动类
 *
 * @author marcher
 */
@ComponentScan(basePackages = {"xin.marcher.framework.*", "xin.marcher.*"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SsoApplication {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start <<<<<<<<<<<<<<<<<<<<<<<<<<");
        SpringApplication.run(SsoApplication.class, args);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> started <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


}