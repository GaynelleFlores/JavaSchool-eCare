package com.example.application.validation;

import com.example.application.models.OptionsEntity;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class OptionValidationTest {

    private final OptionValidation validation = new OptionValidation();

    @Test
    void validateOptionIncorrectPrice() {
        OptionsEntity option = new OptionsEntity();
        option.setPrice(new BigInteger("-10"));
        option.setConnectionCost(new BigInteger("10"));
        assertFalse(validation.validateOption(option));
        option.setPrice(null);
        assertFalse(validation.validateOption(option));
    }

    @Test
    void validateOptionConnectionCost() {
        OptionsEntity option = new OptionsEntity();
        option.setPrice(new BigInteger("10"));
        option.setConnectionCost(new BigInteger("-10"));
        assertFalse(validation.validateOption(option));
        option.setConnectionCost(null);
        assertFalse(validation.validateOption(option));
    }

    @Test
    void validateOptionCorrectOptions() {
        OptionsEntity option = new OptionsEntity();
        OptionsEntity reqOption = new OptionsEntity();
        OptionsEntity incOption = new OptionsEntity();
        Set<OptionsEntity> incOptions = new HashSet<>();
        Set<OptionsEntity> reqOptions = new HashSet<>();

        option.setPrice(new BigInteger("10"));
        option.setConnectionCost(new BigInteger("10"));
        reqOption.setId(2);
        incOption.setId(1);
        reqOptions.add(reqOption);
        incOptions.add(incOption);
        option.setRequiredOptions(reqOptions);
        option.setIncompatibleOptions(incOptions);
        assertTrue(validation.validateOption(option));
    }

    @Test
    void validateOptionSameOptions() {
        OptionsEntity option = new OptionsEntity();
        OptionsEntity errorOption = new OptionsEntity();
        Set<OptionsEntity> incOptions = new HashSet<>();
        Set<OptionsEntity> reqOptions = new HashSet<>();

        option.setPrice(new BigInteger("10"));
        option.setConnectionCost(new BigInteger("10"));
        errorOption.setId(1);
        reqOptions.add(errorOption);
        incOptions.add(errorOption);
        option.setRequiredOptions(reqOptions);
        option.setIncompatibleOptions(incOptions);
        assertFalse(validation.validateOption(option));
    }

    @Test
    void validateOptionMirror() {
        OptionsEntity option = new OptionsEntity();
        OptionsEntity errorOption = new OptionsEntity();
        Set<OptionsEntity> incOptions = new HashSet<>();
        Set<OptionsEntity> reqOptions = new HashSet<>();

        option.setPrice(new BigInteger("10"));
        option.setConnectionCost(new BigInteger("10"));
        errorOption.setId(1);
        reqOptions.add(errorOption);
        incOptions.add(errorOption);
        option.setRequiredOptionsMirror(reqOptions);
        option.setIncompatibleOptions(incOptions);
        assertFalse(validation.validateOption(option));
    }

    @Test
    void validateOptionWithoutRules() {
        OptionsEntity option = new OptionsEntity();

        option.setPrice(new BigInteger("10"));
        option.setConnectionCost(new BigInteger("10"));
        assertTrue(validation.validateOption(option));
    }
}