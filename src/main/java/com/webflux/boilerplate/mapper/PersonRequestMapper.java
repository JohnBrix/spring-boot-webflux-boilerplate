package com.webflux.boilerplate.mapper;

import com.webflux.boilerplate.model.HttpPersonRequest;
import org.springframework.stereotype.Component;

/**
 * package com.webflux.boilerplate.mapper; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonRequestMapper.java, v 0.1 2025-05-20 9:49 PM John Brix Pomoy Exp $$
 */
@Component
public class PersonRequestMapper {

    public HttpPersonRequest buildHttpRequest(HttpPersonRequest httpPersonRequest, Long id){
        httpPersonRequest.setUserId(id);
        return httpPersonRequest;
    }

    public HttpPersonRequest buildHttpRequest(Long id){
        return HttpPersonRequest.builder()
                .userId(id)
                .build();
    }
}
