package com.geovane.ordermanager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class OrderManagerApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(OrderManagerApplication.class, args);
	}

}
