package com.blessed.itemservice.controller;

import com.blessed.itemservice.model.dto.CreateItemRequest;
import com.blessed.itemservice.model.dto.ItemResponse;
import com.blessed.itemservice.model.dto.UpdateItemRequest;
import com.blessed.itemservice.model.dto.UserInfo;
import com.blessed.itemservice.service.ItemService;
import com.blessed.itemservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final JwtUtil jwtUtil;

    @PostMapping
    ResponseEntity<ItemResponse> createItem(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateItemRequest createItemRequest) {
        String token = authorizationHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(userId, createItemRequest));
    }

    @PutMapping("/{id}")
    ResponseEntity<ItemResponse> updateItem(@PathVariable Long id ,
                                            @RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody UpdateItemRequest updateItemRequest) {
        String token = authorizationHeader.substring(7);
        UserInfo userInfo= jwtUtil.extractUserInfo(token);
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id, userInfo,updateItemRequest));
    }

    @DeleteMapping("/{id}")
    HttpStatus deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return HttpStatus.OK;
    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<ItemResponse>> getUserItems(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemsByOwnerId(id));
    }

    @GetMapping("/available")
    ResponseEntity<List<ItemResponse>> getAvailableItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findAvailableItems());
    }

    @GetMapping("/category/{id}")
    ResponseEntity<List<ItemResponse>> getCategoryItems(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemsByCategoryId(id));
    }
}
