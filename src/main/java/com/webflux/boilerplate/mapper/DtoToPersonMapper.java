package com.webflux.boilerplate.mapper;

import com.webflux.boilerplate.entity.Person;
import com.webflux.boilerplate.model.HttpPersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * package com.webflux.boilerplate.mapper; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonMapper.java, v 0.1 2025-05-18 8:30 PM John Brix Pomoy Exp $$
 */
@Mapper(componentModel = "spring")
public interface DtoToPersonMapper {

    @Mapping(source = "userId", target = "id")
    Person dtoToPerson(HttpPersonRequest httpPersonRequest);

}
