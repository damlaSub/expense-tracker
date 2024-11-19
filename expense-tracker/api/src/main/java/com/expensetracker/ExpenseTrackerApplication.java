package com.expensetracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ExpenseTrackerApplication {
    public static void main( String[] args ) {
    SpringApplication.run(ExpenseTrackerApplication.class, args);
    }
    
    @Bean
    CommandLineRunner showSwaggerEndpoint() {
        return args -> {
            String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
            System.out.println("\n\n=============================");
            System.out.println("Swagger UI available at: "+ swaggerUrl);
            System.out.println("=============================\n\n");
        };
    }
}
