package com.webflux.boilerplate.service.impl;

import com.webflux.boilerplate.entity.Person;
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
 * @version $Id: PersonImplService.java, v 0.1 2025-05-09 9:58 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Service
public class PersonImplService implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Single<Person> getPerson(Long id) {
        log.info(PERSON_ID, id);

        //Get Person Identifications
        Mono<Person> resultPersonId = findByPersonId(id);

        return RxJava3Adapter.monoToSingle(resultPersonId);
    }

    private Mono<Person> findByPersonId(Long id) {
        return personRepository.findByPersonId(id)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ServiceException(NOT_FOUND_EXCEPTION)))
                .onErrorMap(error -> {
                    log.error(ERROR, error.getCause());
                    return error;
                });
    }

    @Override
    public Single<Person> savePersonDetails(Person person){

        //Save Person Details
        Mono<Person> updateStatus = saveOrUpdatePerson(person);

        return RxJava3Adapter.monoToSingle(updateStatus);
    }

    private Mono<Person> saveOrUpdatePerson(Person person) {
        return personRepository.save(person)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ServiceException(DATABASE_EXCEPTION)))
                .onErrorMap(error -> {
                    log.error(ERROR, error.getCause());
                    return error;
                });
    }
}
