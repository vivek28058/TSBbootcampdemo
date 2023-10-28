package coll.app.boiler.service.impl;

import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.request.userInfo.UserInfoRequest;
import coll.app.boiler.model.response.userInfo.UserInfoResponse;
import coll.app.boiler.service.UserInfoService;
import coll.app.boiler.util.HeadersUtil;
import coll.app.boiler.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Value("${api.user-info.uri}")
    private String userInfoUri;
    private final WebClient webClient;
    @Value("${api.max-attempt}")
    private Integer maxAttempt;
    @Value("${api.delay-millis}")
    private Integer delayMillis;

    public UserInfoServiceImpl(WebClient webClient){
        this.webClient = webClient;
    }

    @Override
    public Mono<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        log.info("UserInfo Request: {}",request);
        HttpHeaders httpHeaders = HeadersUtil.defaultHeader();

        return webClient.post()
                        .uri(userInfoUri)
                        .headers(head -> head.addAll(httpHeaders))
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError,
                                response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                    log.error("Error status code {}, response body: {}",response.statusCode(), errorBody);
                                    CustomException customException = new CustomException("451","Failed to fetch user information");
                                    return Mono.error(customException);
                                }))
                        .bodyToMono(String.class)
                        .map(response -> {
                            log.info("UserInfo Response: {}",response);
                            return JsonUtil.toObject(response, UserInfoResponse.class);
                        })
                        .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                        .onErrorResume(error -> {
                            log.error("Error while fetching user information max attempt done: {}", error.getMessage());
                            throw new CustomException("451","Failed to fetch user information");
                        });


    }
}
