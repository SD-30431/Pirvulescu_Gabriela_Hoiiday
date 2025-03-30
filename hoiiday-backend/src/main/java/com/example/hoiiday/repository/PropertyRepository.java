package com.example.hoiiday.repository;

import com.example.hoiiday.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
