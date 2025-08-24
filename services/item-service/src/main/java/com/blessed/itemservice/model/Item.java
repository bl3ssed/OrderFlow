package com.blessed.itemservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    private String description;

    @Enumerated(EnumType.STRING)
    private Item_Status status;

    private Long ownerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
