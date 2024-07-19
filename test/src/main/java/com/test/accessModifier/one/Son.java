package com.test.accessModifier.one;

public class Son extends FatherDefault{
    
    static void print() {
        System.out.println("a son!");
    }
    
    public static void main(String[] args) {
        Son son = new Son();
        son.print();
        System.out.println(son.asset);
    }
}
