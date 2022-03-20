package com.example.application.controllers.RestControllers;

import com.example.application.dto.OptionDTO;
import com.example.application.dto.PlanDTO;
import com.example.application.services.PlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class PlansRestController {

    private final PlansService plansService;

    @GetMapping("allPlans")
    public List<PlanDTO> getPlansList() {
        return plansService.getPlansList();
    }

    @GetMapping("getOptionsByPlan/{id}")
    public Set<OptionDTO> getOptionsByPlan(@PathVariable("id") int id) {
        return plansService.getPlan(id).getAllowedOptions();
    }

    @PostMapping("plans/update")
    public ResponseEntity<Object> updatePlan(@RequestParam PlanDTO plan, @RequestParam Set<OptionDTO> allowedOptions) {
        plansService.updatePlan(plan, allowedOptions);
        return ResponseEntity.ok("Plan was updated");
    }

    @PostMapping("plans/create")
    public ResponseEntity<Object> createOption(@RequestParam PlanDTO plan, @RequestParam Set<OptionDTO> allowedOptions) {
        plansService.createPlan(plan, allowedOptions);
        return ResponseEntity.ok("Option was created");
    }

    @GetMapping("plans/{id}/get")
    public PlanDTO getPlanById(@PathVariable("id") int id) {
        return plansService.getPlan(id);
    }
}
