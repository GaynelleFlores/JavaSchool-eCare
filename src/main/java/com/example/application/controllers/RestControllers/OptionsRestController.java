package com.example.application.controllers.RestControllers;

import com.example.application.dto.OptionDTO;
import com.example.application.dto.PlanDTO;
import com.example.application.models.OptionsEntity;
import com.example.application.services.ContractService;
import com.example.application.services.OptionsService;
import com.example.application.services.PlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OptionsRestController {

    private final OptionsService optionsService;

    private final PlansService plansService;

    @GetMapping("getPlansList")
    public List<PlanDTO> getPlansList() {
        return plansService.getPlansList();
    }

    @GetMapping("allOptions")
    public List<OptionDTO> getAllOptions(Model model) {
        return optionsService.getOptionsList();
    }

    @GetMapping("requiredOptions/{id}")
    public Set<OptionsEntity> getRequiredOptions(@PathVariable("id") int id, Model model) {
        OptionDTO opt = optionsService.getOption(id);
        Set<OptionsEntity> result = new HashSet<OptionsEntity>();
        result.addAll(opt.getRequiredOptions());
        result.addAll(opt.getRequiredOptionsMirror());
        return result;
    }

    @GetMapping("incompatibleOptions/{id}")
    public Set<OptionsEntity> getIcompatibleOptions(@PathVariable("id") int id, Model model) {
        OptionDTO opt = optionsService.getOption(id);
        Set<OptionsEntity> result = new HashSet<OptionsEntity>();
        result.addAll(opt.getIncompatibleOptions());
        result.addAll(opt.getIncompatibleOptionsMirror());
        return result;
    }
}
