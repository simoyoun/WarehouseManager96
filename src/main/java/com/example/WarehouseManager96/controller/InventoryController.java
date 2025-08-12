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

    @GetMapping("/search")
    public ResponseEntity<List<InventoryItem>> searchItems(@RequestParam String query) {
        List<InventoryItem> results = repository.findByNameContainingIgnoreCaseOrBarcode(query, query);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }


    @PutMapping("/updateQuantity")
    public ResponseEntity<InventoryItem> updateQuantity(@RequestBody InventoryItem item) {
        InventoryItem item1 = repository.findByBarcode(item.getBarcode());
        if (item1 == null) {
            return ResponseEntity.notFound().build();
        }
        item1.setQuantity(item.getQuantity());
        repository.save(item1);
        return ResponseEntity.ok(item1);
    }

    @PutMapping("/quantity/{id}")
    public ResponseEntity<InventoryItem> updateQuantity(@PathVariable Long id, @RequestBody UpdateQuantityRequest request) {
        InventoryItem item1 = repository.findById(id).orElse(null);
        if (item1 == null) {
            return ResponseEntity.notFound().build();
        }

        item1.setQuantity(item1.getQuantity() + request.quantity());
        repository.save(item1);

        return ResponseEntity.ok(item1);
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

    @DeleteMapping("/barcode/{barcode}")
    public ResponseEntity<Void> deleteItemByBarcode(@PathVariable String barcode) {
        InventoryItem item = repository.findByBarcode(barcode);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(item);
        return ResponseEntity.noContent().build();
    }

    // Request-Body-Klasse (innerhalb des Controllers oder separat)
    public record ScanRequest(String barcode) {}

    public record UpdateQuantityRequest(int quantity) {}
}
