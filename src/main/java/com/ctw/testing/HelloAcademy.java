package com.ctw.testing;

public class HelloAcademy {

    public String sayHello(String name){
        if(name != null && !name.isBlank()){
            return "Hello " + name;
        } else {
            return "Hello";
        }
    }
}
