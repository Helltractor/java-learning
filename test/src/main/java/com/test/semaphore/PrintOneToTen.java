package com.test.semaphore;

import java.util.concurrent.Semaphore;

public class PrintOneToTen {
    
    private static Semaphore semaphore1 = new Semaphore(1);
    private static Semaphore semaphore2 = new Semaphore(0);
    
    public static void main(String[] args) {
        
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                try {
                    semaphore1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
                semaphore2.release();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                try {
                    semaphore2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
                semaphore1.release();
            }
        });
        
        thread1.start();
        thread2.start();
    }
}
