package com.webflux.boilerplate.service;

import com.webflux.boilerplate.entity.Person;
import io.reactivex.rxjava3.core.Single;

/**
 * package com.webflux.boilerplate.service; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonService.java, v 0.1 2025-05-09 9:58 PM John Brix Pomoy Exp $$
 */
public interface PersonService {

    Single<Person> getPerson(Long id);
    Single<Person> savePersonDetails(Person person);
}
