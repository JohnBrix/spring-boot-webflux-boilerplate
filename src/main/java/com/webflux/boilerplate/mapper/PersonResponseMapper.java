package com.webflux.boilerplate.mapper;

import com.webflux.boilerplate.model.HttpPersonResponse;
import org.springframework.stereotype.Component;

import static com.webflux.boilerplate.constant.PersonConstants.*;
import static com.webflux.boilerplate.constant.PersonConstants.INTERNAL_SERVER_ERROR;

/**
 * package com.webflux.boilerplate.mapper; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonMapperResponse.java, v 0.1 2025-05-18 9:13 PM John Brix Pomoy Exp $$
 */
@Component
public class PersonResponseMapper {

    public HttpPersonResponse build201Response() {
        return HttpPersonResponse.builder()
                .message(CREATED)
                .status(0)
                .description(CREATED)
                .isSuccess(true)
                .build();
    }

    public HttpPersonResponse build404Response() {
        return HttpPersonResponse.builder()
                .message(NOT_FOUND)
                .status(1)
                .description(USER_NOT_EXIST)
                .isSuccess(false)
                .build();
    }

    public HttpPersonResponse build500Response() {
        return HttpPersonResponse.builder()
                .message(INTERNAL_SERVER_ERROR)
                .status(2)
                .description(INTERNAL_SERVER_ERROR)
                .isSuccess(false)
                .build();
    }

    public HttpPersonResponse build2xxResponse() {
        return HttpPersonResponse.builder()
                .message(SUCCESS)
                .status(0)
                .description(SUCCESS)
                .isSuccess(true)
                .build();
    }



}
