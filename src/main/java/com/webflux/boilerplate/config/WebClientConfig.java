package com.webflux.boilerplate.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * package com.webflux.boilerplate.config; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: WebClientConfig.java, v 0.1 2025-05-09 9:23 PM John Brix Pomoy Exp $$
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient getWebclient() {
        //Timeout Configuration
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofMillis(10000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }

}
