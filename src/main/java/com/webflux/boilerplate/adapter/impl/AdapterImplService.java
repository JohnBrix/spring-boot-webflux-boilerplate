package com.webflux.boilerplate.adapter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.boilerplate.adapter.AdapterService;
import com.webflux.boilerplate.model.HttpPersonResponse;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import static com.webflux.boilerplate.constant.ApiEndpointConstant.LOCAL_HOST;
import static com.webflux.boilerplate.constant.PersonConstants.HTTP_PERSON_RESPONSE;
import static com.webflux.boilerplate.constant.PersonConstants.HTTP_REQUEST;


/**
 * package com.webflux.boilerplate.adapter.impl; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: AdapterImplService.java, v 0.1 2025-05-09 9:43 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Component
public class AdapterImplService implements AdapterService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public Single<HttpPersonResponse> callSomethingAPI(String request) {
        log.info(HTTP_REQUEST, request);

        //Calling Another API
        Mono<HttpPersonResponse> response = callAnotherAPI(request);

        return RxJava3Adapter.monoToSingle(response);
    }

    private Mono<HttpPersonResponse> callAnotherAPI(String request) {
        return webClient.post()
                .uri(LOCAL_HOST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .body(Mono.just(request), String.class)
                .retrieve()
                .toEntity(String.class)
                .map(result -> {
                    try {
                        //Object Mapping
                        HttpPersonResponse httpPersonResponse = objectMapper.readValue(result.getBody(), HttpPersonResponse.class);
                        log.info(HTTP_PERSON_RESPONSE, httpPersonResponse);
                        return httpPersonResponse;
                    } catch (Exception err) {
                        log.info("Error: {}", err.getCause());

                        return null;
                    }
                });
    }

}
