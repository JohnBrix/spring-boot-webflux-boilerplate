package com.webflux.boilerplate.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;
/**
 * package com.webflux.boilerplate.filter; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: RequestIdFilter.java, v 0.1 2025-05-18 10:37 PM John Brix Pomoy Exp $$
 */
@Slf4j
@Component
public class RequestIdFilter implements WebFilter {

    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Generate a request ID if not present
        String requestId = exchange.getRequest().getHeaders().getFirst(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        // Add request ID to MDC (for logging)
        MDC.put("requestId", requestId);

        // Modify the response to include the request ID
        exchange.getResponse().getHeaders().add(REQUEST_ID_HEADER, requestId);

        return chain.filter(exchange).doFinally(signalType -> MDC.clear()); // Clear MDC after request completes
    }
}