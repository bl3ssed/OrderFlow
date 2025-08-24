package com.blessed.itemservice.model.dto;

import com.blessed.itemservice.model.Category;
import com.blessed.itemservice.model.Item_Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Item_Status status;
    private Long owner_id;
}
