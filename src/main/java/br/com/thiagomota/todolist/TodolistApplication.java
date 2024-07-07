package br.com.thiagomota.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class TodolistApplication {


	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
