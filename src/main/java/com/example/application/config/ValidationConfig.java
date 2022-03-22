package com.example.application.config;

import com.example.application.validation.ClientValidation;
import com.example.application.validation.ContractValidation;
import com.example.application.validation.OptionValidation;
import com.example.application.validation.PlanValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public ContractValidation getContractValidation() {
        return new ContractValidation();
    }

    @Bean
    public OptionValidation getOptionValidation() {
        return new OptionValidation();
    }

    @Bean
    public PlanValidation getPlanValidation() {
        return new PlanValidation();
    }

    @Bean
    public ClientValidation getClientValidation() {
        return new ClientValidation();
    }
}
