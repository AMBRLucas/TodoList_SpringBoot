package com.lucas.todoSimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//! Annotation da classe principal
@SpringBootApplication
public class TodoSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoSimpleApplication.class, args);
	}
}
