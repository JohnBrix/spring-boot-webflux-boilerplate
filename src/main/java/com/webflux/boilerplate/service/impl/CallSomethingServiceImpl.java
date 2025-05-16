package com.webflux.boilerplate.service.impl;

import com.webflux.boilerplate.adapter.AdapterService;
import com.webflux.boilerplate.model.HttpPersonResponse;
import com.webflux.boilerplate.service.PersonService;
import com.webflux.boilerplate.service.SampleService;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.webflux.boilerplate.constant.PersonConstants.HTTP_PERSON_RESPONSE;
import static com.webflux.boilerplate.constant.PersonConstants.PERSON;

/**
 * package com.webflux.boilerplate.service.impl; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleImplService.java, v 0.1 2025-05-09 9:40 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Service
public class CallSomethingServiceImpl implements SampleService {

    @Autowired
    private PersonService personService;

    @Autowired
    private AdapterService adapterService;

    @Override
    public Single<HttpPersonResponse> getPerson(Long id) {

        //findByPersonId
        return personService.getPerson(id)
                .flatMap(personResponse -> {

                    log.debug(PERSON, personResponse);

                    //Calling another API
                    return callSomethingAPI(id);
                });
    }

    private Single<HttpPersonResponse> callSomethingAPI(Long id) {
        return adapterService.callSomethingAPI(id)
                .flatMap(httpPersonResponse -> {
                    log.debug(HTTP_PERSON_RESPONSE, httpPersonResponse);
                    return Single.just(httpPersonResponse);
                });
    }
}
