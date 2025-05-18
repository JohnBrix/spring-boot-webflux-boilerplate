package com.webflux.boilerplate.service;

import com.webflux.boilerplate.model.HttpPersonRequest;
import com.webflux.boilerplate.model.HttpPersonResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * package com.webflux.boilerplate.service; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: SampleService.java, v 0.1 2025-05-09 9:40 PM John Brix Pomoy Exp $$
 */
public interface PersonService {

    Single<HttpPersonResponse> getPersonById(Long id);
    Single<HttpPersonResponse> createPersonDetails(HttpPersonRequest personRequest);
}
