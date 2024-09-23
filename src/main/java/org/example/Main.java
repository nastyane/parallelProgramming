package org.example;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final int COUNT_WRITERS = 15;
    public static final int COUNT_READERS = 15;
    public static final int COUNT_READS = 5_000_000;
    public static final int COUNT_SELLS = 5_000_000;
    public static final int STOCK = COUNT_WRITERS * COUNT_SELLS;

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager(STOCK);

        long start = System.currentTimeMillis();

        ExecutorService writers = Executors.newFixedThreadPool(COUNT_WRITERS);
        for (int i = 0; i < COUNT_WRITERS; i++) {
            writers.execute(() -> {
                for (int j = 0; j < COUNT_SELLS; j++) {
                    inventoryManager.sellItem(1);
                }
            });
        }

        ExecutorService readers = Executors.newFixedThreadPool(COUNT_READERS);
        for (int i = 0; i < COUNT_READERS; i++) {
            readers.execute(() -> {
                for (int j = 0; j < COUNT_READS; j++) {
                    int stock = inventoryManager.getStock();
                }
            });
        }

        writers.close();
        readers.close();

        long end = System.currentTimeMillis();
        System.out.println("Total time execution: " + Duration.ofMillis(end - start));

        int expectedSold = STOCK - COUNT_WRITERS * COUNT_SELLS;
        if (inventoryManager.getStock() != expectedSold) {
            System.err.println("Fail!");
            System.err.println("Actual: " + inventoryManager.getStock());
            System.err.println("Expected: " + expectedSold);
        } else {
            System.out.println("OK");
            System.out.println("stock: " + inventoryManager.getStock());
        }
    }
}


