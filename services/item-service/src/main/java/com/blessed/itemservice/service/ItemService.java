package com.blessed.itemservice.service;

import com.blessed.itemservice.ItemMapper;
import com.blessed.itemservice.exception.CategoryNotFoundException;
import com.blessed.itemservice.model.Item;
import com.blessed.itemservice.model.dto.CreateItemRequest;
import com.blessed.itemservice.model.dto.ItemResponse;
import com.blessed.itemservice.repository.CategoryRepository;
import com.blessed.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    public ItemResponse createItem(Long userId, CreateItemRequest request) {
        Item item = ItemMapper.CreateRequestToEntity(request);
        item.setCategory(categoryRepository.findById(request.getCategory_id()).orElseThrow(() -> new CategoryNotFoundException("category with id: " + request.getCategory_id().toString() + " not found")));
        item.setOwnerId(userId);
        itemRepository.save(item);
        return ItemMapper.EntityToResponse(item);
    }
}
