package com.webflux.boilerplate.mapper;

import com.webflux.boilerplate.entity.Person;
import com.webflux.boilerplate.model.HttpPersonRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T16:04:27+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class DtoToPersonMapperImpl implements DtoToPersonMapper {

    @Override
    public Person saveDtoToPerson(HttpPersonRequest httpPersonRequest) {
        if ( httpPersonRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setFirstName( httpPersonRequest.getFirstName() );
        person.setLastName( httpPersonRequest.getLastName() );
        person.setMiddleName( httpPersonRequest.getMiddleName() );

        return person;
    }

    @Override
    public Person updateDtoToPerson(HttpPersonRequest httpPersonRequest) {
        if ( httpPersonRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( httpPersonRequest.getUserId() );
        person.setFirstName( httpPersonRequest.getFirstName() );
        person.setLastName( httpPersonRequest.getLastName() );
        person.setMiddleName( httpPersonRequest.getMiddleName() );

        return person;
    }
}
