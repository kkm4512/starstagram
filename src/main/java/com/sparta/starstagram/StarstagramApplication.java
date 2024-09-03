package com.sparta.starstagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StarstagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarstagramApplication.class, args);
    }
}
