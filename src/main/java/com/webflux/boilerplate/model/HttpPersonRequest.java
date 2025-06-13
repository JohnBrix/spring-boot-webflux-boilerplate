package com.webflux.boilerplate.model;

import lombok.Builder;
import lombok.Data;

/**
 * package com.webflux.boilerplate.model; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: HttpPersonRequest.java, v 0.1 2025-05-18 6:57 PM John Brix Pomoy Exp $$
 */
@Data
@Builder
public class HttpPersonRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
}
