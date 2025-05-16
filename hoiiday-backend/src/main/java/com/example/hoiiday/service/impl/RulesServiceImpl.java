package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.RulesDTO;
import com.example.hoiiday.mapper.RulesMapper;
import com.example.hoiiday.model.Rules;
import com.example.hoiiday.repository.RulesRepository;
import com.example.hoiiday.service.RulesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RulesServiceImpl implements RulesService {

    private final RulesRepository rulesRepository;
    private final RulesMapper rulesMapper;

    public RulesServiceImpl(RulesRepository rulesRepository, RulesMapper rulesMapper) {
        this.rulesRepository = rulesRepository;
        this.rulesMapper = rulesMapper;
    }

    @Override
    public List<RulesDTO> getAllRules() {
        return rulesRepository.findAll().stream()
                .map(rulesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RulesDTO getRuleById(Long id) {
        return rulesRepository.findById(id)
                .map(rulesMapper::toDTO)
                .orElse(null);
    }

    @Override
    public RulesDTO createRule(RulesDTO rulesDTO) {
        Rules rule = rulesMapper.toEntity(rulesDTO);
        Rules savedRule = rulesRepository.save(rule);
        return rulesMapper.toDTO(savedRule);
    }

    @Override
    public RulesDTO updateRule(Long id, RulesDTO rulesDTO) {
        return rulesRepository.findById(id)
                .map(existingRule -> {
                    existingRule.setDescription(rulesDTO.getDescription());
                    Rules updatedRule = rulesRepository.save(existingRule);
                    return rulesMapper.toDTO(updatedRule);
                })
                .orElse(null);
    }

    @Override
    public void deleteRule(Long id) {
        if (rulesRepository.existsById(id)) {
            rulesRepository.deleteById(id);
        } else {
            throw new RuntimeException("Rule not found with id: " + id);
        }
    }
}