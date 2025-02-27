package com.ctw.workstation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WiremockResource implements QuarkusTestResourceLifecycleManager {

    WireMockServer wireMockServer;
    WireMock wireMock;

    @Override
    public void init(Map<String, String> initArgs) {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        wireMock = new WireMock(wireMockServer.port());
    }

    @Override
    public Map<String, String> start() {
        return Map.of("external-api.url", wireMockServer.baseUrl());
    }


    @Override
    public void stop() {
        wireMockServer.stop();
    }

}

