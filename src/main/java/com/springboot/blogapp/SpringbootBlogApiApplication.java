package com.springboot.blogapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Springboot Blog REST API",
                description = "Springboot Blog REST API documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Azcona",
                        email = "marceloazcona@gmail.com",
                        url = ""
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = ""
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Springboot Blog Documentation",
                url = "https://github.com/marcelo-azcona/Springboot-Api-BlogApp/tree/master"
        )
)
public class SpringbootBlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
