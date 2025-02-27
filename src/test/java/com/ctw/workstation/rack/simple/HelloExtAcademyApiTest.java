package com.ctw.workstation.rack.simple;

import com.ctw.workstation.config.CtwAcademyTestProfile;
import com.ctw.workstation.config.WiremockResource;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@QuarkusTest
@TestProfile(CtwAcademyTestProfile.class)
class HelloExtAcademyApiTest {
    @Inject
    HelloExtAcademy helloExtAcademy;

    @Test
    @DisplayName("Saying hello to external API")
    void saying_hello_to_external_api() {
        // given
        String name = "Tiago";

        stubFor(post(urlEqualTo("/external/hello"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello " + name + "!")));
        // when

        String result = helloExtAcademy.sayHello(name);
        // then
        Assertions.assertThat(result)
                .isEqualTo("Hello " + name + "!");
    }
}