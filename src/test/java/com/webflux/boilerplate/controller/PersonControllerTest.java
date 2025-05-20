package com.webflux.boilerplate.controller;

import com.webflux.boilerplate.mapper.PersonRequestMapper;
import com.webflux.boilerplate.mapper.PersonResponseMapper;
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

    public static final String GET_PERSON = "/api/person/123";

    @Test
    public void successPersonTest() throws Exception {
        when(service.getPersonById(123L)).thenReturn(Single.just(buildSuccessResponse()));

        webTestClient
                .get()
                .uri(GET_PERSON)
                .accept(MediaType.valueOf(MediaType.ALL_VALUE))
                .exchange()
                .expectStatus()
                .isOk();
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
