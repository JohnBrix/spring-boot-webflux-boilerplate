package com.webflux.boilerplate.adapter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.boilerplate.adapter.AdapterService;
import com.webflux.boilerplate.model.HttpPersonRequest;
import com.webflux.boilerplate.model.HttpPersonResponse;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.webflux.boilerplate.constant.ApiEndpointConstant.*;
import static com.webflux.boilerplate.constant.PersonConstants.*;


/**
 * package com.webflux.boilerplate.adapter.impl; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: AdapterImplService.java, v 0.1 2025-05-09 9:43 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Component
public class AdapterServiceImpl implements AdapterService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public Single<HttpPersonResponse> getKYCApi(HttpPersonRequest request) {
        log.info(HTTP_REQUEST, request);

        return RxJava3Adapter.monoToSingle(getWebClient(request));
    }

    private Mono<HttpPersonResponse> getWebClient(HttpPersonRequest request) {

        //Calling External API /kyc
        return webClient.post()
                .uri(WIRE_MOCK_BASE_ENDPOINT + KYC)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .body(Mono.just(request), HttpPersonRequest.class)
                .retrieve()
                .onStatus(status -> status.value() == 503,
                        response -> Mono.error(new Exception(DOWNSTREAM_EXCEPTION)))
                .toEntity(String.class)
                .map(this::validateResponseAndParsingJsonToDTO);
    }

    private HttpPersonResponse validateResponseAndParsingJsonToDTO(ResponseEntity<String> result) {
        log.info(RESULT_BODY,result.getBody());

        return Optional.ofNullable(result.getBody())
                .map(body -> parsingJsonToDto(result))
                .orElseThrow(() -> new IllegalArgumentException(DOWNSTREAM_EXCEPTION));

    }

    private HttpPersonResponse parsingJsonToDto(ResponseEntity<String> result) {
        try {
            //Object Mapping and ideal for reparsing the JSON to DTO.
            HttpPersonResponse httpPersonResponse = objectMapper.readValue(result.getBody(), HttpPersonResponse.class);

            log.info(HTTP_PERSON_RESPONSE, httpPersonResponse);
            return httpPersonResponse;
        } catch (JsonProcessingException e) {
            log.info(ADAPTER, e);

            throw new IllegalArgumentException(JSON_PROCESS_EXCEPTION); // Directly throw an exception
        }
    }




}
