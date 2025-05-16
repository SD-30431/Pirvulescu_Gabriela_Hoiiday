package com.example.hoiiday.service;

import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.model.SearchRequest;

import java.util.List;

public interface SearchService {
    List<PropertyDTO> search(SearchRequest r);
}
