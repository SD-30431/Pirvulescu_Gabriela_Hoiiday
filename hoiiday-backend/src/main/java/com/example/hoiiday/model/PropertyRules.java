package com.example.hoiiday.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_rules")
public class PropertyRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_rule_id")
    private Long propertyRuleId;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rules rules;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
