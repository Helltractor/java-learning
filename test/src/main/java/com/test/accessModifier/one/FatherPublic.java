package com.test.accessModifier.one;

import java.math.BigDecimal;

public class FatherPublic {
    
    protected BigDecimal asset = new BigDecimal(1000);
    
    int wifNum = 1;
    
    static void print() {
        System.out.println("a public father!");
    }
}
