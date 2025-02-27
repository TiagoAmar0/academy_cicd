package com.ctw.workstation.rack.simple;

import com.arjuna.ats.jta.exceptions.NotImplementedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class HelloExtAcademyTest {
    @Mock
    ExternalMessageService externalMessageService;

    @InjectMocks
    HelloExtAcademy helloExtAcademy;

    @Spy
    ExternalMessageService externalMessageServiceSpy;
//
//    @Test
//    @DisplayName("Hello from outer space with mock")
//    void hello_from_outer_space_with_mock(){
//        String name = "Tiago";

//        Mockito.when(externalMessageService.sayHelloFromOuterSpace())
//                .thenThrow(new NotImplementedException("Feature not available"));
//
//        // when
//        String result2 = externalMessageService.sayHelloFromOuterSpace(name);
//
//        assertThat(result2)
//                .isEqualTo("Hello Tiago from outer space");
//    }

    @Test
    @DisplayName("Hello from outer space with spy")
    void hello_from_outer_space_with_spy(){
        String name = "Tiago";

        /*helloExtAcademy.externalMessageService = externalMessageServiceSpy;

        //Mockito.doNothing().when(externalMessageServiceSpy).doSomething();
        Mockito.doReturn("Hello world")
                .when(externalMessageServiceSpy).sayHelloFromOuterSpace();

        String result = helloExtAcademy.sayHello(name);

        Mockito.verify(externalMessageServiceSpy,
                Mockito.times(1)).doSomething();


        assertThat(result)
                .isEqualTo("Hello world");*/
    }

}
