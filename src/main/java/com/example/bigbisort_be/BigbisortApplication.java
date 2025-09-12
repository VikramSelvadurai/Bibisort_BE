package com.example.bigbisort_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BigbisortApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigbisortApplication.class, args);
    }

}
