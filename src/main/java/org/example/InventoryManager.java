package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class InventoryManager {
    private AtomicInteger stock;

    public InventoryManager(int stock) {
        this.stock = stock;
    }

    public synchronized boolean sellItem(int amount) {
        if (stock >= amount) {
            stock -= amount;
            return true;
        }
        return false;
    }

    public synchronized int getStock() {
        return stock.get();
    }

}
