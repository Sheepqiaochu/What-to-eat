package com.bootTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bootTest.dao.UserDao;
import com.bootTest.domain.User;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@MapperScan("com.bootTest.dao")
public class testApplication {
	public static void main(String[] args){
		SpringApplication.run(testApplication.class, args);
	}
	
	 @Bean
	 public HttpMessageConverters fastJsonHttpMessageConverters() {
		 //配置fastjson
		 FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
	     FastJsonConfig fastJsonConfig = new FastJsonConfig();
	     fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
	     fastConverter.setFastJsonConfig(fastJsonConfig);
	     HttpMessageConverter<?> converter = fastConverter;
	     return new HttpMessageConverters(converter);
	 }
	
	/*@Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(80);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(443);
        return connector;
    }*/
}
