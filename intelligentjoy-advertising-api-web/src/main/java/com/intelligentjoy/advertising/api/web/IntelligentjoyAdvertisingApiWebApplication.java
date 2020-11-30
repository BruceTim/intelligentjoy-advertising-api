package com.intelligentjoy.advertising.api.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan({"com.intelligentjoy.advertising.api.core.service", "com.intelligentjoy.advertising.api.web"})
@MapperScan("com.intelligentjoy.advertising.api.core.dao")
public class IntelligentjoyAdvertisingApiWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligentjoyAdvertisingApiWebApplication.class, args);
	}

}
