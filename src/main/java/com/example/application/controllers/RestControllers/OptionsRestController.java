package com.example.application.controllers.RestControllers;

import com.example.application.dto.OptionDTO;
import com.example.application.services.OptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OptionsRestController {

    private final OptionsService optionsService;

    @GetMapping("allOptions")
    public List<OptionDTO> getAllOptions(Model model) {
        return optionsService.getOptionsList();
    }

    @GetMapping("requiredOptions/{id}")
    public Set<OptionDTO> getRequiredOptions(@PathVariable("id") int id) {
        return optionsService.getRequiredOptions(id);
    }

    @PostMapping("options/update")
    public ResponseEntity<Object> updateOption(@RequestParam OptionDTO option, @RequestParam Set<OptionDTO> reqOptions, @RequestParam Set<OptionDTO> incOptions) {
        optionsService.updateOption(option, reqOptions, incOptions);
        return ResponseEntity.ok("Option was updated");
    }

    @PostMapping("options/create")
    public ResponseEntity<Object> createOption(@RequestParam OptionDTO option, @RequestParam Set<OptionDTO> reqOptions, @RequestParam Set<OptionDTO> incOptions) {
        optionsService.createOption(option, reqOptions, incOptions);
        return ResponseEntity.ok("Option was created");
    }

    @GetMapping("incompatibleOptions/{id}")
    public Set<OptionDTO> getIncompatibleOptions(@PathVariable("id") int id) {
        return optionsService.getIncompatibleOptions(id);
    }

    @GetMapping("getOption/{id}")
    public OptionDTO getOption(@PathVariable("id") int id) {
        return optionsService.getOption(id);
    }
}
