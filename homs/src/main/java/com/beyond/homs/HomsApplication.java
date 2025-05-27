package com.beyond.homs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomsApplication.class, args);
    }

}
