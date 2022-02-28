package com.example.application.controllers;

import com.example.application.dto.PlanDTO;
import com.example.application.services.PlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlansController {

    private final PlansService plansService;

    @Autowired
    public PlansController(PlansService plansService){
        this.plansService = plansService;
    }

    @GetMapping("plans")
    public String index(Model model) {
        model.addAttribute("plans", plansService.getPlansList());
        return "plans";
    }

    @GetMapping("plans/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("plan", plansService.getPlan(id));
        return "plan";
    }

    @GetMapping("/createPlan")
    public String addPlan(Model model) {
        model.addAttribute("plan", new PlanDTO());
        return "createPlan";
    }

    @PostMapping("/plans")
    public String create(@ModelAttribute("plan") PlanDTO plan) {
        plansService.createPlan(plan);
        return "success";
    }

    @DeleteMapping("/plans/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        plansService.deletePlan(id);
        return "success";
    }

    @GetMapping("plans/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("plan", plansService.getPlan(id));
        return "editPlan";
    }

    @PatchMapping("plans/{id}")
    public String update(@ModelAttribute("contract") PlanDTO plan) {
        plansService.updatePlan(plan);
        return "success";
    }

}
