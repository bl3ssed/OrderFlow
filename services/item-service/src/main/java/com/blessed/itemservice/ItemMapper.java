package com.blessed.itemservice;

import com.blessed.itemservice.model.Item;
import com.blessed.itemservice.model.Item_Status;
import com.blessed.itemservice.model.dto.CreateItemRequest;
import com.blessed.itemservice.model.dto.ItemResponse;
import com.blessed.itemservice.model.dto.UpdateItemRequest;
import com.blessed.itemservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class ItemMapper {
    static public Item CreateRequestToEntity(CreateItemRequest request) {
        return Item.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Item_Status.AVAILABLE)
                .build();
    }
    static public ItemResponse EntityToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .category(item.getCategory())
                .status(item.getStatus())
                .owner_id(item.getOwnerId())
                .build();
    }

    public static Item UpdateRequestToEntity(UpdateItemRequest request) {
        return Item.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(request.getOwnerId())
                .status(request.getStatus())
                .build();
    }
}
