package com.example.hoiiday.mapper;

import com.example.hoiiday.DTO.RulesDTO;
import com.example.hoiiday.model.Rules;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RulesMapper {
    RulesDTO toDTO(Rules rules);
    Rules toEntity(RulesDTO rulesDTO);
}
