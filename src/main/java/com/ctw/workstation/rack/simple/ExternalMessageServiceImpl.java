package com.ctw.workstation.rack.simple;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExternalMessageServiceImpl implements ExternalMessageService{

    @Override
    public String sayHelloFromOuterSpace(String name) {
        return "Hello from outer space " + name;
    }

    @Override
    public String sayHelloFromOuterSpace() {
        Log.info("Hello from outer space");
        return "Hello from outer space";
    }

    @Override
    public void doSomething() {
        Log.info("do something...");
    }
}
