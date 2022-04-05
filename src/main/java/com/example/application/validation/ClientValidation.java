package com.example.application.validation;

import com.example.application.config.LoggerConfig;
import com.example.application.models.ClientsEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientValidation {
    private final Logger logger;

    {
        this.logger = LogManager.getLogger(LoggerConfig.class);
    }

    private boolean checkPassport(String passport) {
        if (passport == null)
            return false;
        String regexp = "\\d{4} \\d{4}";
        return passport.matches(regexp);
    }

    private boolean checkEmail(String email) {
        if (email == null)
            return true;
        String regexp = "^[A-Za-z0-9+_-]+@(.+)\\.(.+)$";
        return email.matches(regexp);
    }

    public boolean validateClient(ClientsEntity client) {
        if (!checkPassport(client.getPassport())) {
            logger.error("Client has incorrect passport.");
            return false;
        }
        if (!checkEmail(client.getEmail())) {
            logger.error("Client has incorrect email.");
            return false;
        }
        return true;
    }
}
