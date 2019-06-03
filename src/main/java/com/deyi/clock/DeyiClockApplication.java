package com.deyi.clock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*@SpringBootApplication 等价于以默认属性使用 @Configuration ， @EnableAutoConfiguration 和 @ComponentScan， 所以启动类无需再添加这三个注解
@Configuration ： 标注一个类为配置类。
@EnableAutoConfiguration ：开启自动配置。
@ComponentScan ：自动收集所有的 Spring 组件*/
// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.deyi.clock.dao")
@EnableScheduling
public class DeyiClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeyiClockApplication.class, args);
    }

}
