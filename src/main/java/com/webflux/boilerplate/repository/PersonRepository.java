package com.webflux.boilerplate.repository;

import com.webflux.boilerplate.entity.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * package com.webflux.boilerplate.repository; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonRepository.java, v 0.1 2025-05-09 9:51 PM John Brix Pomoy Exp $$
 */
@Repository
public interface PersonRepository extends R2dbcRepository<Person, Long> {

    /*You can create your custom query here, hence e.g*/

    //Example
    @Query("SELECT * FROM person WHERE id =:id")
    Mono<Person> findByPersonId(@Param("id")Long id);
}
