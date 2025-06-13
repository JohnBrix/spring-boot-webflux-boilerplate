package com.webflux.boilerplate.mapper;

import com.webflux.boilerplate.model.HttpPersonRequest;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.webflux.boilerplate.constant.PersonConstants.HTTP_REQUEST;

/**
 * package com.webflux.boilerplate.mapper; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonRequestMapper.java, v 0.1 2025-05-20 9:49 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Component
public class PersonRequestMapper {

    public HttpPersonRequest buildHttpRequest(HttpPersonRequest httpPersonRequest, Long id){
        httpPersonRequest.setUserId(id);

        log.info(HTTP_REQUEST, httpPersonRequest);
        return httpPersonRequest;
    }

    public HttpPersonRequest buildHttpRequest(Long id){
        return HttpPersonRequest.builder()
                .userId(id)
                .build();
    }
}
