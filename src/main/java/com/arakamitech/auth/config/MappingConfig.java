package com.arakamitech.auth.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}

}
