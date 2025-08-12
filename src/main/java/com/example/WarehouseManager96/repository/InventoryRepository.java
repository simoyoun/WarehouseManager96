package com.example.WarehouseManager96.repository;

import com.example.WarehouseManager96.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    InventoryItem findByBarcode(String barcode);

    List<InventoryItem> findByNameContainingIgnoreCaseOrBarcode(String name, String barcode);
}


