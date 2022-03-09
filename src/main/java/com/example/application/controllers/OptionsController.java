package com.example.application.controllers;

import com.example.application.dto.OptionDTO;
import com.example.application.services.OptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class OptionsController {
    private final OptionsService optionsService;

    @GetMapping("options")
    public String index(Model model) {
        model.addAttribute("options", optionsService.getOptionsList());
        return "options";
    }

    @GetMapping("options/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("option", optionsService.getOption(id));
        return "option";
    }

    @GetMapping("createOption")
    public String addOption(Model model) {
        model.addAttribute("option", new OptionDTO());
        return "createOption";
    }

    @PostMapping("options")
    public String create(@ModelAttribute("option") OptionDTO option) {
        optionsService.createOption(option);
        return "success";
    }

    @DeleteMapping("options/{id}")
    public String delete(@PathVariable("id") int id) {
        optionsService.deleteOption(id);
        return "success";
    }

    @GetMapping("options/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("option", optionsService.getOption(id));
        return "editOption";
    }

    @PostMapping("options/{id}")
    public String update(@ModelAttribute("contract") OptionDTO option) {
        optionsService.updateOption(option);
        return "success";
    }
}
