package com.webflux.boilerplate.controller;

import com.webflux.boilerplate.mapper.PersonRequestMapper;
import com.webflux.boilerplate.mapper.PersonResponseMapper;
import com.webflux.boilerplate.model.HttpPersonRequest;
import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.service.PersonService;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

/**
 * package com.webflux.boilerplate.controller; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleControllerTest.java, v 0.1 2025-05-09 11:24 PM John Brix Pomoy Exp $$
 */
@WebFluxTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private PersonService service;

    @MockitoBean
    private PersonResponseMapper personResponseMapper;

    @MockitoBean
    private PersonRequestMapper personRequestMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public HttpPersonRequest buildHttpRequest(){
        return HttpPersonRequest.builder()
                .userId(1L)
                .firstName("foo")
                .lastName("bar")
                .middleName("f")
                .build();
    }

    public static final String URI = "/api/person";
    public static final String GET_PERSON = "/123";
    public static final String CREATE_PERSON = "/createPerson";

    @Test
    public void getPersonTest(){
        when(service.getPersonById(123L)).thenReturn(Single.just(buildSuccessResponse()));

        webTestClient
                .get()
                .uri(URI+GET_PERSON)
                .accept(MediaType.valueOf(MediaType.ALL_VALUE))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void createPersonTest(){
        HttpPersonRequest request = buildHttpRequest();

        when(service.createPersonDetails(request))
                .thenReturn(Single.just(buildSuccessResponse()));
        
        webTestClient
                .post()
                .uri(URI+CREATE_PERSON)
                .body(Single.just(request), HttpPersonRequest.class)
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    private static HttpPersonResponse buildSuccessResponse() {
        return HttpPersonResponse.builder()
                .status(0)
                .isSuccess(true)
                .message("success")
                .description("success")
                .build();
    }

}
