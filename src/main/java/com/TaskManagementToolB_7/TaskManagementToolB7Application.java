package com.TaskManagementToolB_7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@org.springframework.context.annotation.ComponentScan(basePackages="com")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages="com.TaskManagementToolB_Repositorye")
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages="com.TaskManagementToolB_Entity")
public class TaskManagementToolB7Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementToolB7Application.class, args);
	}

}
