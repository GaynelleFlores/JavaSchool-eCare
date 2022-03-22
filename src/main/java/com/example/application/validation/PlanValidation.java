package com.example.application.validation;

import com.example.application.config.LoggerConfig;
import com.example.application.models.PlansEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

public class PlanValidation {
    private final Logger logger;

    {
        this.logger = LogManager.getLogger(LoggerConfig.class);
    }

    private boolean checkPrice(PlansEntity plan) {
        if (plan.getPrice() == null)
            return false;
        return plan.getPrice().compareTo(BigInteger.ZERO) >= 0;
    }

    public boolean validatePlan(PlansEntity plan) {
        if (!checkPrice(plan)) {
            logger.error("Plan has incorrect price");
            return false;
        }
        return true;
    }
}
