package com.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;


@Controller
@RequiredArgsConstructor
public class ErrorControllerImpl implements ErrorController {
    private final Logger logger;

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                logger.warn("Error code: " + statusCode);
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                logger.warn("Error code: " + statusCode);
                return "error-500";
            }
            logger.warn("Error code: " + statusCode);
        }
        return "error";
    }
}
