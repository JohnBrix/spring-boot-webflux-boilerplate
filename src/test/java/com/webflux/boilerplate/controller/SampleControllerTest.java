package com.webflux.boilerplate.controller;

import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.service.SampleService;
import io.reactivex.rxjava3.core.Single;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

/**
 * package com.webflux.boilerplate.controller; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleControllerTest.java, v 0.1 2025-05-09 11:24 PM John Brix Pomoy Exp $$
 */
@RunWith(SpringRunner.class)
@WebFluxTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private SampleService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void paymentInstructionSuccessTest() throws Exception {
        when(service.getPerson(123L)).thenReturn(Single.just(buildSuccessResponse()));

        webTestClient
                .get()
                .uri("/123")
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
