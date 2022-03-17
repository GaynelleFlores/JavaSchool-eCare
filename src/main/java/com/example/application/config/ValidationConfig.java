package com.example.application.config;

import com.example.application.validation.ContractValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public ContractValidation getContractValidation() {
        return new ContractValidation();
    }
}
