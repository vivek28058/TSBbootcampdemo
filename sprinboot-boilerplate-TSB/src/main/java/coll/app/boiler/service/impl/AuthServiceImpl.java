package coll.app.boiler.service.impl;

import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.request.token.TokenRequest;
import coll.app.boiler.model.response.token.TokenResponse;
import coll.app.boiler.service.AuthService;
import coll.app.boiler.util.HeadersUtil;
import coll.app.boiler.util.JsonUtil;
import coll.app.boiler.util.PayloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final WebClient webClient;

    @Value("${api.token.uri}")
    private String tokenUrl;

    @Value("${api.max-attempt}")
    private Integer maxAttempt;
    @Value("${api.delay-millis}")
    private Integer delayMillis;


    public AuthServiceImpl(WebClient webClient){
        this.webClient = webClient;
    }

    @Override
    public Mono<TokenResponse> getToken() {

        HttpHeaders httpHeaders = HeadersUtil.defaultHeader();
        TokenRequest tokenRequest = PayloadUtil.tokenRequest();
        log.info("Token Request: {}",tokenRequest);
        return webClient.post()
                .uri(tokenUrl)
                .headers(head -> head.addAll(httpHeaders))
                .bodyValue(tokenRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            log.error("Error token status code {}, response body: {}",response.statusCode(), errorBody);
                            CustomException customException = new CustomException("451","Failed to fetch Token");
                            return Mono.error(customException);
                        }))
                .bodyToMono(String.class)
                .map(response -> {
                    log.info("Token Response: {}",response);
                    return JsonUtil.toObject(response, TokenResponse.class);
                })
                .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                .onErrorResume(error -> {
                    log.error("Error while fetching user information max attempt done: {}", error.getMessage());
                    throw new CustomException("451","Failed to fetch Token");
                });
    }
}
