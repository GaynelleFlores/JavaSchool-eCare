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
        String regexp = "\\d{4} \\d{4}";
        return passport.matches(regexp);
    }

    public boolean validateClient(ClientsEntity client) {
        if (!checkPassport("string")) {
            logger.error("Client has incorrect passport.");
            return false;
        }
        return true;
    }
}
