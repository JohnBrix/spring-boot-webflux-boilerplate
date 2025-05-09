package com.webflux.boilerplate.controller;

import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.service.SampleService;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.webflux.boilerplate.constant.PersonConstants.*;

/**
 * package com.webflux.boilerplate.controller; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleController.java, v 0.1 2025-05-09 9:33 PM John Brix Pomoy Exp $$
 */
@Slf4j
@RestController
public class SampleController {

    @Autowired
    private SampleService service;


    @GetMapping("/{id}")
    public Single<ResponseEntity<HttpPersonResponse>> getPerson(@PathVariable Long id) {

        return service.getPerson(id)
                .flatMap(httpPersonResponse -> {
                    log.info(HTTP_PERSON_RESPONSE,httpPersonResponse);
                    return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.OK));
                })
                //Error Catch Handling, hence equal to finally or catch
                .onErrorResumeNext(errorResult ->{
                    log.error(ERROR, errorResult);
                    return validateError(errorResult);
                });

    }

    private static Single<ResponseEntity<HttpPersonResponse>> validateError(Throwable errorResult) {
        String result = errorResult.getMessage();

        if (result.equals(NOT_FOUND_EXCEPTION)){

            HttpPersonResponse httpPersonResponse = build404Response();
            log.error(ERROR,httpPersonResponse);

            return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.NOT_FOUND));
        }

        HttpPersonResponse httpPersonResponse = build500Response();
        log.error(ERROR,httpPersonResponse);

        return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private static HttpPersonResponse build404Response() {
        return HttpPersonResponse.builder()
                .message(NOT_FOUND)
                .status(1)
                .description(USER_NOT_EXIST)
                .isSuccess(false)
                .build();
    }

    private static HttpPersonResponse build500Response() {
        return HttpPersonResponse.builder()
                .message(INTERNAL_SERVER_ERROR)
                .status(2)
                .description(INTERNAL_SERVER_ERROR)
                .isSuccess(false)
                .build();
    }

}
