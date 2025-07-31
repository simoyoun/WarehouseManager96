package com.example.WarehouseManager96.repository;

import com.example.WarehouseManager96.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    InventoryItem findByBarcode(String barcode);
}
