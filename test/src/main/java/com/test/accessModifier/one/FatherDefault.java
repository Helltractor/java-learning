package com.test.accessModifier.one;

import java.math.BigDecimal;

class FatherDefault {
    
    protected BigDecimal asset = new BigDecimal(999);
    
    int wifeNum = 1;
    
    static void print() {
        System.out.println("a default father!");
    }
}
