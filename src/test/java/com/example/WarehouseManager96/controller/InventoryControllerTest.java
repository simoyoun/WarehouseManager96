package com.example.WarehouseManager96.controller;

import com.example.WarehouseManager96.InventoryItem;
import com.example.WarehouseManager96.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryRepository repository;

    @Test
    void scanBarcode_shouldReturn200() throws Exception {
        // Mock Repository
        InventoryItem item = new InventoryItem("12345", "Laptop", 10); // barcode as String
        when(repository.findByBarcode("12345")).thenReturn(item);

        // Request & Assert
        mockMvc.perform(post("/api/inventory/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\":\"12345\"}")) // Note quotes for String value
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(9)); // Reduced from 10
    }

}
