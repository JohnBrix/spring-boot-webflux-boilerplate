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
public class PersonController {

    @Autowired
    private SampleService service;


    @GetMapping("/{id}")
    public Single<ResponseEntity<HttpPersonResponse>> getPerson(@PathVariable Long id) {
        return service.getPerson(id)
                .flatMap(httpPersonResponse -> {
                    log.info(HTTP_PERSON_RESPONSE,httpPersonResponse);
                    return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.OK));
                })
                .onErrorResumeNext(PersonController::validateError);  //Error Catch Handling, hence equal to finally or catch

    }

    private static Single<ResponseEntity<HttpPersonResponse>> validateError(Throwable throwable) {
        String result = throwable.getMessage();

        log.error(ERROR,result);

        //Validate the results and return NOT_FOUND_EXCEPTION,DOWNSTREAM_EXCEPTION and JSON_PROCESS_EXCEPTION. or else INTERNAL_SERVER_ERROR
        return switch (result) {
            case String error when error.equals("NOT_FOUND_EXCEPTION") -> Single.just(new ResponseEntity<>(build404Response(), HttpStatus.NOT_FOUND));
            case String error when error.equals("DOWNSTREAM_EXCEPTION") -> Single.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
            case String error when error.equals("JSON_PROCESS_EXCEPTION") -> Single.just(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
            default ->{
                    HttpPersonResponse httpPersonResponse = build500Response();
                    log.error(ERROR,httpPersonResponse);

            yield Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.INTERNAL_SERVER_ERROR));
            }
        };
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
