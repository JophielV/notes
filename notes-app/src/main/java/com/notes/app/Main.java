package com.notes.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.notes"})
@EnableSwagger2
@EnableWebMvc
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
