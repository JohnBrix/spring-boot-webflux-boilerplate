package com.webflux.boilerplate.controller;

import com.webflux.boilerplate.mapper.PersonRequestMapper;
import com.webflux.boilerplate.mapper.PersonResponseMapper;
import com.webflux.boilerplate.model.HttpPersonRequest;
import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.service.PersonService;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.webflux.boilerplate.constant.PersonConstants.*;

/**
 * package com.webflux.boilerplate.controller; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleController.java, v 0.1 2025-05-09 9:33 PM John Brix Pomoy Exp $$
 */
@Slf4j
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonResponseMapper personResponseMapper;

    @Autowired
    private PersonRequestMapper personRequestMapper;


    @GetMapping("/{id}")
    public Single<ResponseEntity<HttpPersonResponse>> getPerson(@PathVariable Long id) {
        log.info(HTTP_ID, id);

        return personService.getPersonById(id)
                .flatMap(httpPersonResponse -> {
                    log.info(HTTP_PERSON_RESPONSE, httpPersonResponse);

                    return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.OK));
                })
                .onErrorResumeNext(this::validateError);  //Error Catch Handling, hence equal to finally or catch
    }

    @PostMapping("/createPerson")
    public Single<ResponseEntity<HttpPersonResponse>> createPerson(@RequestBody HttpPersonRequest request) {
        log.info(HTTP_REQUEST, request);

        return personService.createPersonDetails(request)
                .flatMap(httpPersonResponse -> {
                    log.info(HTTP_PERSON_RESPONSE, httpPersonResponse);

                    return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.CREATED));
                })
                .onErrorResumeNext(this::validateError);
    }

    @PutMapping("/updatePerson/{id}")
    public Single<ResponseEntity<HttpPersonResponse>> updatePerson(@PathVariable Long id, @RequestBody HttpPersonRequest request) {

        return validateRequest(request)
                .flatMap(httpPersonRequest -> {
                    log.info(HTTP_REQUEST, httpPersonRequest);

                    return personService.updatePersonDetails(personRequestMapper.buildHttpRequest(request, id))
                            .flatMap(httpPersonResponse -> {
                                log.info(HTTP_PERSON_RESPONSE, httpPersonResponse);
                                log.info("FLATMAP_SUCCESS");
                                return Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.OK));
                            });
                }).onErrorResumeNext(this::validateError);
    }

    public Single<HttpPersonRequest> validateRequest(HttpPersonRequest httpPersonRequest) {

        log.error("HttpPersonRequest: {}", httpPersonRequest);
        return switch (httpPersonRequest) {
            case HttpPersonRequest request when request.getFirstName().equals("brix") -> Single.error(new IllegalArgumentException("BRIX_ILLEGAL"));
            default -> Single.just(httpPersonRequest);
        };
    }

    public Single<ResponseEntity<HttpPersonResponse>> validateError(Throwable throwable) {
        String result = throwable.getMessage();

        log.error(ERROR, result);

        //Validate the results and return NOT_FOUND_EXCEPTION,DOWNSTREAM_EXCEPTION and JSON_PROCESS_EXCEPTION. or else INTERNAL_SERVER_ERROR
        return switch (result) {
            case ("BRIX_ILLEGAL") -> Single.just(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
            case ("FTUBE_10") -> Single.just(new ResponseEntity<>(HttpStatus.ALREADY_REPORTED));
            case ("NOT_FOUND_EXCEPTION") ->
                    Single.just(new ResponseEntity<>(personResponseMapper.build404Response(), HttpStatus.NOT_FOUND));
            case ("DOWNSTREAM_EXCEPTION") -> Single.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
            case ("JSON_PROCESS_EXCEPTION") -> Single.just(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
            default -> {
                HttpPersonResponse httpPersonResponse = personResponseMapper.build500Response();
                log.error(ERROR, httpPersonResponse);

                yield Single.just(new ResponseEntity<>(httpPersonResponse, HttpStatus.INTERNAL_SERVER_ERROR));
            }
        };
    }
}
