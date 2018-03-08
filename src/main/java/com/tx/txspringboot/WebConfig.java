package com.tx.txspringboot;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/3/2 9:59
 * Description
 */
@Configuration
@ConfigurationProperties(prefix = "connector")
public class WebConfig extends WebMvcConfigurerAdapter {

    /*@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            Ssl ssl = new Ssl();
            //Server.jks中包含服务器私钥和证书
            ssl.setKeyStore("classpath:ssl-server.jks");
            ssl.setKeyStorePassword("changeit");
            container.setSsl(ssl);
            container.setPort(443);
        };
    }*/

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        //SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
                        SecurityConstraint securityConstraint = new SecurityConstraint();
                        securityConstraint.setUserConstraint("CONFIDENTIAL");
                        SecurityCollection collection = new SecurityCollection();
                        collection.addPattern("/*");
                        securityConstraint.addCollection(collection);
                        context.addConstraint(securityConstraint);
                    }
                };
        factory.addAdditionalTomcatConnectors(createHttpConnector());
        return factory;
    }
    private Connector createHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(port);
        connector.setRedirectPort(redirectPort);
        return connector;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //Swagger ui Mapping
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // webjars Mapping
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/");

        // 静态文件处理
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // 证书文件验证
        registry.addResourceHandler("/.well-known/pki-validation/fileauth.txt")
                .addResourceLocations("classpath:/ssl/");
    }


    private int port;
    private int redirectPort;
    public void setPort(int port) {
        this.port = port;
    }
    public void setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
    }
}
