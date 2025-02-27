package com.ctw.workstation.rack.simple;

import com.ctw.workstation.external.ExternalApi;
import com.ctw.workstation.external.ExternalRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HelloExtAcademy {

    @Inject
    @RestClient
    ExternalApi externalApi;

    public String sayHello(String name){
        if(name != null){
            return externalApi.hello(new ExternalRequest(name)).message();
        }
        return externalApi.hello().message();
    }
}
