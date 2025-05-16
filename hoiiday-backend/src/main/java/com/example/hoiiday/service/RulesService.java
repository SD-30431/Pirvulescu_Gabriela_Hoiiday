package com.example.hoiiday.service;

import com.example.hoiiday.DTO.RulesDTO;

import java.util.List;

public interface RulesService {
    List<RulesDTO> getAllRules();
    RulesDTO getRuleById(Long id);
    RulesDTO createRule(RulesDTO rulesDTO);
    RulesDTO updateRule(Long id, RulesDTO rulesDTO);
    void deleteRule(Long id);
}