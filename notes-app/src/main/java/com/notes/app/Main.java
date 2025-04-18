package com.notes.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = {"com.notes"})
@EnableWebMvc
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
