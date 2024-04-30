package com.inventory.inventoryservice.service;

import com.inventory.inventoryservice.model.Inventory;
import com.inventory.inventoryservice.model.InventoryResponse;
import com.inventory.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        List<Inventory> items = repository.findBySkuCodeIn(skuCode);
        List<InventoryResponse> availableItems = items.stream()
                .map(inventory -> InventoryResponse.builder()
                        .isInStock(inventory.getQuantity() > 0)
                        .skuCode(inventory.getSkuCode())
                        .build())
                .collect(Collectors.toList());
        return availableItems;
    }


}
