package com.blessed.itemservice.model.dto;

import com.blessed.itemservice.model.Category;
import com.blessed.itemservice.model.Item_Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateItemRequest {
    @NotBlank
    private String name;
    @NotNull
    private Long category_id;

    private String description;
    @NotNull
    private Long ownerId;
}
