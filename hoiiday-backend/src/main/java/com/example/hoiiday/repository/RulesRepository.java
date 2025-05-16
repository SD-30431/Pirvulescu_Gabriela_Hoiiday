package com.example.hoiiday.repository;

import com.example.hoiiday.model.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends JpaRepository<Rules, Long> {
}
