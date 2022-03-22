package com.example.application.validation;

import com.example.application.models.PlansEntity;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PlanValidationTest {

    private final PlanValidation validation = new PlanValidation();

    @Test
    void validatePlanIncorrectPrice() {
        PlansEntity plan = new PlansEntity();

        plan.setPrice(new BigInteger("-1"));
        assertFalse(validation.validatePlan(plan));
        plan.setPrice(null);
        assertFalse(validation.validatePlan(plan));
    }
}