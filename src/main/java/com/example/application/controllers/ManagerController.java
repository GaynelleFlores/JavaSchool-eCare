package com.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class    ManagerController {

    @GetMapping("manager/mainPage")
    public String mainPage() {
        return "mainPage";
    }
}
