package com.ctw.testing;

import io.quarkus.logging.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MathOperationsTest {

    private static MathOperations mathOperations;

    @BeforeAll
    static void setup(){
        mathOperations = new MathOperations();
    }

    @AfterAll
    static void tearDown(){
        Log.info("Ending");
    }

    @Test
    @Order(1)
    void add(){
        Assertions.assertEquals(2, mathOperations.add(1,1));
    }

    @Order(3)
    @Test
    void divide(){
        Assertions.assertEquals(2, mathOperations.divide(4,2));
    }

    @Order(2)
    @Test
    void divide_by_zero(){
        Assertions.assertThrows(ArithmeticException.class, () -> mathOperations.divide(2, 0));
    }

    @Order(4)
    @Test
    void dividend_smaller_than_divisor(){
        Assertions.assertAll(
                () -> assertInstanceOf(Integer.class, mathOperations.divide(3,2)),
                () -> assertNotEquals(1.5, mathOperations.divide(3,2))
        );
    }


}
