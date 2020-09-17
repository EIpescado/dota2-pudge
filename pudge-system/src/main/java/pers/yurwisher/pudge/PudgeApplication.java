package pers.yurwisher.pudge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yq
 * @date 2020/09/17 16:57
 * @description 启动入口
 * @since V1.0.0
 */
@EnableAsync
@SpringBootApplication
public class PudgeApplication {


    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        //允许查询参数中包含指定特殊字符 设置tomcat参数
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    public static void main(String[] args) {
        SpringApplication.run(PudgeApplication.class, args);
    }
}
