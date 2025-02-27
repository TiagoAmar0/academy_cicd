package com.ctw.testing;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

class HelloAcademyTest {

    public static Stream<Arguments> hello_academy_with_valid_name() {
        return Stream.of(
                Arguments.of("Tiago"),
                Arguments.of("João"),
                Arguments.of("Maria")
        );
    }

    @ParameterizedTest
    @MethodSource
    void hello_academy_with_valid_name(String name){
        // Given
        HelloAcademy helloAcademy = new HelloAcademy();

        // When
        String result = helloAcademy.sayHello(name);

        org.assertj.core.api.Assertions.assertThat(result).isEqualTo("Hello " + name);
    }


    @ParameterizedTest
    @NullSource
    @EmptySource
    void hello_academy_with_null_name(String name){
        HelloAcademy helloAcademy = new HelloAcademy();


        // When
        String result = helloAcademy.sayHello(name);

        // Then
        Assertions.assertThat(result).isEqualTo("Hello");
    }

    @Test
    void hello_academy_with_empty_name(){
        // Given
        String name = "";
        HelloAcademy helloAcademy = new HelloAcademy();

        // When
        String result = helloAcademy.sayHello(name);

        // Then
        Assertions.assertThat(result).isEqualTo("Hello");
    }
}
