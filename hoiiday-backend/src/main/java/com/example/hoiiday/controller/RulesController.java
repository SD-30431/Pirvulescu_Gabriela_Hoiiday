package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.RulesDTO;
import com.example.hoiiday.service.RulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @GetMapping
    public ResponseEntity<List<RulesDTO>> getAllRules() {
        return new ResponseEntity<>(rulesService.getAllRules(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RulesDTO> getRuleById(@PathVariable Long id) {
        RulesDTO rule = rulesService.getRuleById(id);
        return rule != null ? new ResponseEntity<>(rule, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<RulesDTO> createRule(@RequestBody RulesDTO rulesDTO) {
        RulesDTO createdRule = rulesService.createRule(rulesDTO);
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RulesDTO> updateRule(@PathVariable Long id, @RequestBody RulesDTO rulesDTO) {
        RulesDTO updatedRule = rulesService.updateRule(id, rulesDTO);
        return updatedRule != null ? new ResponseEntity<>(updatedRule, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        try {
            rulesService.deleteRule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}