package com.example.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.activity.model.activityModel;
import com.example.activity.model.storageModel;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ActivityApplication {

	public static void main(String[] args) {
	SpringApplication.run(ActivityApplication.class, args);
		

	}

}
