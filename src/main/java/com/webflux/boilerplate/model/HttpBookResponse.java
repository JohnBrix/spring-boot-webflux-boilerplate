package com.webflux.boilerplate.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * package com.webflux.boilerplate.model; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: HttpBookResponse.java, v 0.1 2025-05-22 12:43 PM John Brix Pomoy Exp $$
 */
@Data
@Builder
public class HttpBookResponse {
    private String resultMessage;
    private String resultDescription;
    private Integer resultCode;
    private List<HttpCreateBookRequest> httpCreateBookRequestList;
}
