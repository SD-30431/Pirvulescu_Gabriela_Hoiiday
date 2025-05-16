package com.example.hoiiday.controller;


import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.mapper.PropertyMapper;
import com.example.hoiiday.model.SearchRequest;
import com.example.hoiiday.repository.PropertyRepository;
import com.example.hoiiday.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public List<PropertyDTO> search(@RequestBody SearchRequest req) {
        return searchService.search(req);
    }
}
