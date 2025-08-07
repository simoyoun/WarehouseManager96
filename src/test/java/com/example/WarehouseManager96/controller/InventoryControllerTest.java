package com.example.WarehouseManager96.controller;

import com.example.WarehouseManager96.InventoryItem;
import com.example.WarehouseManager96.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void scanBarcode_shouldReturn200() throws Exception {
        // Mock Repository
        InventoryItem item = new InventoryItem("12395", "Laptop2", 10); // barcode as String
        when(repository.findByBarcode("12395")).thenReturn(item);

        // Request & Assert
        mockMvc.perform(post("/api/inventory/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\":\"12395\"}")) // Note quotes for String value
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(9)); // Reduced from 10
    }



    // Testfall: Erfolgreiche Artikelanlage (Jira-Ticket INV-123)
    @Test
    void addItem_shouldReturn200_whenValidRequest() throws Exception {
        // Arrange
        InventoryItem mockItem = new InventoryItem("19345", "Laptop4", 10);
        when(repository.save(any(InventoryItem.class))).thenReturn(mockItem);

        // Act & Assert
        mockMvc.perform(post("/api/inventory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("19345"))
                .andExpect(jsonPath("$.name").value("Laptop4"));
    }

//    @Test
//    public void deleteItem_ShouldReturnNoContent() throws Exception {
//        Long itemId = 1L;
//
//        // Perform DELETE request
//        mockMvc.perform(delete("/items/{id}", itemId))
//                .andExpect(status().isNoContent());
//
//        // Verify repository.deleteById was called with the correct ID
//        verify(repository,times(1)).deleteById(itemId);
//    }

}
