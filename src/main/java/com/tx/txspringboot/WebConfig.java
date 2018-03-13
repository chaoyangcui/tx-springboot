package com.tx.txspringboot;

import nz.net.ultraq.thymeleaf.LayoutDialect;
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
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

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

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/static/");
        templateResolver.setSuffix(".html");
        // templateResolver.setTemplateMode("HTML5");
        // 非严格验证HTML代码,需额外依赖net.sourceforge.nekohtml-nekohtml
        templateResolver.setTemplateMode("LEGACYHTML5");
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        // springTemplateEngine.addDialect(new SpringStandardDialect());
        springTemplateEngine.addDialect(new LayoutDialect());
        /**
         * @See https://github.com/thymeleaf/thymeleaf-extras-springsecurity
         * springTemplateEngine.addDialect(new SpringSecurityDialect());
         * springTemplateEngine.addDialect(new CmsDialect());
         */
        return springTemplateEngine;
    }
    @Bean
    public ThymeleafViewResolver viewResolver() {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setViewNames(new String[]{"*.html", "*.xhtml"});
        return viewResolver;
    }
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        final UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewNames(".jsp", ".tpl");
        urlBasedViewResolver.setViewClass(FreeMarkerView.class);
        urlBasedViewResolver.setOrder(2);
        return urlBasedViewResolver;
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
        registry.viewResolver(urlBasedViewResolver());
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
