package com.test.accessModifier.two;

import com.test.accessModifier.one.FatherPublic;

public class GrandFather {
    
    static void print() {
        System.out.println("a grand father!");
    }
    
    public static void main(String[] args) {
        GrandFather grandFather = new GrandFather();
        grandFather.print();
        FatherPublic fatherPublic = new FatherPublic();
//        System.out.println(fatherPublic.asset);
//        System.out.println(fatherPublic.wifeNum);
    }
}
