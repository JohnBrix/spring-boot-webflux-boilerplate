package com.webflux.boilerplate.model;

import lombok.Builder;
import lombok.Data;

/**
 * package com.webflux.boilerplate.model; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: HttpPersonResponse.java, v 0.1 2025-05-09 9:35 PM John Brix Pomoy Exp $$
 */
@Data
@Builder
public class HttpPersonResponse {
    private String message;
    private Integer status;
    private String description;
    private Boolean isSuccess;
    private Integer resultCode;
    private String xml;
}
