package com.webflux.boilerplate.model;

import lombok.Data;

/**
 * package com.webflux.boilerplate.model; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: HttpCreateBookRequest.java, v 0.1 2025-05-22 12:41 PM John Brix Pomoy Exp $$
 */
@Data
public class HttpCreateBookRequest {
    private String authorName;
    private String title;
    private String description;
    private String type;
}
