package com.webflux.boilerplate.service.impl;

import com.webflux.boilerplate.adapter.AdapterService;
import com.webflux.boilerplate.entity.Person;
import com.webflux.boilerplate.mapper.DtoToPersonMapper;
import com.webflux.boilerplate.mapper.PersonRequestMapper;
import com.webflux.boilerplate.mapper.PersonResponseMapper;
import com.webflux.boilerplate.model.HttpPersonRequest;
import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.repository.PersonRepository;
import com.webflux.boilerplate.service.PersonService;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import static com.webflux.boilerplate.constant.PersonConstants.*;

/**
 * package com.webflux.boilerplate.service.impl; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleImplService.java, v 0.1 2025-05-09 9:40 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdapterService adapterService;

    @Autowired
    private DtoToPersonMapper dtoToPersonMapper;

    @Autowired
    private PersonResponseMapper personResponseMapper;
    @Autowired
    private PersonRequestMapper personRequestMapper;

    @Override
    public Single<HttpPersonResponse> getPersonById(Long id) {
        //Building http request to external API
        HttpPersonRequest mappedPersonRequest = personRequestMapper.buildHttpRequest(id);
        log.info(PERSON,mappedPersonRequest);

        //findByPersonId
        return findByPersonId(id)
                .flatMap(personResponse -> {
                    log.debug(PERSON, personResponse);

                    //Calling another API
                    return callSomethingAPI(mappedPersonRequest);
                });
    }

    @Override
    public Single<HttpPersonResponse> createPersonDetails(HttpPersonRequest personRequest){
        //Dto to Person mapping for Insert query only.
        Person mappedPerson = dtoToPersonMapper.saveDtoToPerson(personRequest);
        log.info(MAPPED_PERSON,mappedPerson.toString());

        //CreatePerson Details
        return saveOrUpdatePerson(mappedPerson)
                .flatMap(personResponse ->{
                    log.info(PERSON,personResponse);

                    //Return 201
                    return Single.just(personResponseMapper.build201Response());
                });
    }

    @Override
    public Single<HttpPersonResponse> updatePersonDetails(HttpPersonRequest personRequest){
        //Dto to Person mapping for Update query only.
        Person mappedPerson = dtoToPersonMapper.updateDtoToPerson(personRequest);
        log.info(MAPPED_PERSON,mappedPerson.toString());

        //Find Person By Id and Before Update Person details.
        return findPersonByIdBeforeUpdate(mappedPerson);
    }

    private Single<HttpPersonResponse> findPersonByIdBeforeUpdate(Person mappedPerson) {
        return findByPersonId(mappedPerson.getId())
                .flatMap(personResponse -> {
                    log.info(PERSON, personResponse);

                    return saveOrUpdatePerson(mappedPerson)
                            .flatMap(person -> {
                                log.info(PERSON, person);

                                return Single.just(personResponseMapper.build2xxResponse());
                            });
                });
    }

    private Single<HttpPersonResponse> callSomethingAPI(HttpPersonRequest httpPersonRequest) {
        return adapterService.getKYCApi(httpPersonRequest)
                .flatMap(httpPersonResponse -> {
                    log.debug(HTTP_PERSON_RESPONSE, httpPersonResponse);

                    return Single.just(httpPersonResponse);
                });
    }

    private Single<Person> findByPersonId(Long id) {
        return RxJava3Adapter.monoToSingle(
                personRepository.findByPersonId(id)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ServiceException(NOT_FOUND_EXCEPTION)))
                .onErrorMap(error -> {
                    log.error(ERROR, error.getCause());
                    return error;
                })
        );
    }

    private Single<Person> saveOrUpdatePerson(Person mappedPerson) {

        return RxJava3Adapter.monoToSingle(
                personRepository.save(mappedPerson)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ServiceException(DATABASE_EXCEPTION)))
                .onErrorMap(error -> {
                    log.error(ERROR, error.getCause());
                    return error;
                })
        );
    }
}
