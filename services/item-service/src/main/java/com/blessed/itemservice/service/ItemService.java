package com.blessed.itemservice.service;

import com.blessed.itemservice.ItemMapper;
import com.blessed.itemservice.exception.CategoryNotFoundException;
import com.blessed.itemservice.exception.ItemNotFoundException;
import com.blessed.itemservice.exception.UserAccessException;
import com.blessed.itemservice.model.Category;
import com.blessed.itemservice.model.Item;
import com.blessed.itemservice.model.Item_Status;
import com.blessed.itemservice.model.dto.CreateItemRequest;
import com.blessed.itemservice.model.dto.ItemResponse;
import com.blessed.itemservice.model.dto.UpdateItemRequest;
import com.blessed.itemservice.model.dto.UserInfo;
import com.blessed.itemservice.repository.CategoryRepository;
import com.blessed.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    public ItemResponse createItem(Long userId, CreateItemRequest request) {
        Item item = ItemMapper.CreateRequestToEntity(request);
        item.setCategory(categoryRepository.findById(request.getCategory_id()).orElseThrow(() -> new CategoryNotFoundException("category with id: " + request.getCategory_id().toString() + " not found")));
        item.setOwnerId(userId);
        itemRepository.save(item);
        return ItemMapper.EntityToResponse(item);
    }

    public ItemResponse updateItem(Long id, UserInfo userInfo, UpdateItemRequest request) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("item with id: " + id + " not found"));
        if (!item.getOwnerId().equals(userInfo.getId()) && !(userInfo.getRole().equals("ADMIN")) ) {
            throw new UserAccessException("you can't update not your item");
        }
        Optional.ofNullable(request.getName()).ifPresent(item::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(item::setDescription);
        Optional.ofNullable(request.getOwnerId()).ifPresent(item::setOwnerId);
        Optional.ofNullable(request.getStatus()).ifPresent(item::setStatus);
        if (request.getCategory_id() != null) {
            item.setCategory(categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() ->
                            new CategoryNotFoundException("category with id: " + request.getCategory_id().toString() + " not found")));
        }
        item.setUpdatedAt(LocalDateTime.now());
        itemRepository.save(item);
        return ItemMapper.EntityToResponse(item);
    }

    public void deleteItem(Long id) throws ItemNotFoundException {
        itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("item with id: " + id + " not found"));
        itemRepository.deleteById(id);
    }

    public List<ItemResponse> findItemsByOwnerId(Long ownerId) {
        return itemRepository.findAllByOwnerId(ownerId).stream().map(ItemMapper::EntityToResponse).collect(Collectors.toList());
    }

    public List<ItemResponse> findAvailableItems() {
        return itemRepository.findAllByStatus(Item_Status.AVAILABLE).stream().map(ItemMapper::EntityToResponse).collect(Collectors.toList());
    }

    public List<ItemResponse> findItemsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("category with id: " + categoryId + " not found"));
        return itemRepository.findAllByCategory(category).stream().map(ItemMapper::EntityToResponse).collect(Collectors.toList());
    }
}
