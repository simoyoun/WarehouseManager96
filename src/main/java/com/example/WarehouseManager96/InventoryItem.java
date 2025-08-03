package com.example.WarehouseManager96;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_item")  // Stelle sicher, dass der Tabellenname hier steht!
public class InventoryItem {


    @Id
    @GeneratedValue
    private Long id;
    private String barcode;
    private String name;
    private int quantity;



    public InventoryItem() {

    }

    public InventoryItem(String barcode, String name, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Getter & Setter
}
