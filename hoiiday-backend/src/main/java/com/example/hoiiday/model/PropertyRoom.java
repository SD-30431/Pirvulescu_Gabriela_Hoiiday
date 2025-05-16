package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "property_room")
public class PropertyRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Property property;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int qtyTotal;
    private BigDecimal price;
}


