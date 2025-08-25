package com.blessed.itemservice.repository;

import com.blessed.itemservice.model.Category;
import com.blessed.itemservice.model.Item;
import com.blessed.itemservice.model.Item_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long id);
    List<Item> findAllByStatus(Item_Status status);

    List<Item> findAllByCategory(Category category);
}
