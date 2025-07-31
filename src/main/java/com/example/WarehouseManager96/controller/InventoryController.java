package com.example.WarehouseManager96.controller;

import com.example.WarehouseManager96.InventoryItem;
import com.example.WarehouseManager96.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository repository;

    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/scan")
    public ResponseEntity<InventoryItem> scanBarcode(@RequestBody ScanRequest request) {
        InventoryItem item = repository.findByBarcode(request.barcode());
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        item.setQuantity(item.getQuantity() - 1);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/all")
    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryItem> addItem(@RequestBody InventoryItem newItem) {
        // Validiere Eingabe
        if (newItem.getBarcode() == null || newItem.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        InventoryItem savedItem = repository.save(newItem);
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Request-Body-Klasse (innerhalb des Controllers oder separat)
    public record ScanRequest(String barcode) {}
}
