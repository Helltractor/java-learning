package com.thread;

import java.util.Random;

public class ReentrantReadWriteLockDemo {
    
    public static UnboundedCache<Integer, Integer> cache = new UnboundedCache<>();
    
    public class MyThread implements Runnable {
        
        @Override
        public void run() {
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
               cache.put(i, random.nextInt(100));
            }
        }
    }
}
