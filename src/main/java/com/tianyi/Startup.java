package com.tianyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lingqingwan on 8/4/15
 */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class })
@ImportResource("classpath:spring-*.xml")
public class Startup {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Startup.class, args);
    }
}