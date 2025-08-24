package com.blessed.itemservice.model.dto;

import com.blessed.itemservice.model.Item_Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateItemRequest {
    private String name;
    private Long category_id;
    private String description;
    private Long ownerId;
    private Item_Status status;
}
