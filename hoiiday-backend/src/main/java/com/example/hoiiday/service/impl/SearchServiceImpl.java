package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.mapper.PropertyMapper;
import com.example.hoiiday.model.SearchRequest;
import com.example.hoiiday.repository.PropertyRepository;
import com.example.hoiiday.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final PropertyRepository propertyRepo;
    private final PropertyMapper mapper;

    public SearchServiceImpl(PropertyRepository propertyRepo, PropertyMapper mapper) {
        this.propertyRepo = propertyRepo;
        this.mapper = mapper;
    }

    public List<PropertyDTO> search(SearchRequest r) {
        if (!r.from().isBefore(r.to()))
            throw new IllegalArgumentException("from must be before to");

        return propertyRepo.search(
                        r.type(),
                        r.rooms(),
                        r.from(),
                        r.to(),
                        r.to().minusDays(1))
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

