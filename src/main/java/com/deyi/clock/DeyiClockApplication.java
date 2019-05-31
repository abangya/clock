package com.deyi.clock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.deyi.clock.dao")
public class DeyiClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeyiClockApplication.class, args);
    }

}
