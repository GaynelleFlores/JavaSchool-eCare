package com.example.application.validation;

import com.example.application.config.LoggerConfig;
import com.example.application.models.OptionsEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class OptionValidation {

    private final Logger logger;

    {
        this.logger = LogManager.getLogger(LoggerConfig.class);
    }

    private Set<OptionsEntity> createTempSet(Set<OptionsEntity> first, Set<OptionsEntity> second) {
        Set<OptionsEntity> temp = new HashSet<OptionsEntity>();
        if (first != null) {
            temp.addAll(first);
        }
        if (second != null) {
            temp.addAll(second);
        }
        return temp;
    }

    private boolean checkRules(OptionsEntity option) {
        Set<OptionsEntity> tempIncompatibleOptions = createTempSet(option.getIncompatibleOptions(), option.getIncompatibleOptionsMirror());
        Set<OptionsEntity> tempRequiredOptions = createTempSet(option.getRequiredOptions(), option.getRequiredOptionsMirror());
        return (!tempIncompatibleOptions.removeAll(tempRequiredOptions));
    }

    private boolean checkPrice(OptionsEntity option) {
        if (option.getPrice() == null || option.getConnectionCost() == null)
            return false;
       return option.getPrice().compareTo(BigInteger.ZERO) >= 0 && option.getConnectionCost().compareTo(BigInteger.ZERO) >= 0;
    }

    public boolean validateOption(OptionsEntity option) {
        if (!checkPrice(option)) {
            logger.error("Option has incorrect price or connection cost");
            return false;
        }
        if (!checkRules(option)) {
            logger.error("Option contains the same options in IncompatibleOptions and RequiredOptions");
            return false;
        }
        return true;
    }
}
