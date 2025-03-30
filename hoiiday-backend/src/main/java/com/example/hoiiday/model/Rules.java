package com.example.hoiiday.model;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "rules")
public class Rules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Long ruleId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "rules")
    private List<PropertyRules> propertyRulesList;

}
