package coll.app.boiler.service;

import coll.app.boiler.setup.BaseWebClientMockedTest;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.response.userInfo.UserInfoResponse;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class UserInfoServiceMock extends BaseWebClientMockedTest {

    @Test
    @DisplayName("Success - External API - User Info API")
    public void externalApiUserInfoSuccessTest(){

        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(Mockito.eq(userInfoUri)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(Mockito.eq(userInfoRequest)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                        .thenReturn(Mono.just(JsonUtil.toJson(userInfoResponse)));

        Mono<UserInfoResponse> userInfoResponseMono = userInfoServiceImpl.getUserInfo(userInfoRequest);

        StepVerifier.create(userInfoResponseMono)
                .expectNext(userInfoResponse)
                .verifyComplete();
    }


    @Test
    @DisplayName("Api Error - External API - User Info API - (400 Bad Request)")
    public void externalApiUserInfoErrorTest(){


        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(Mockito.eq(userInfoUri)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(Mockito.eq(userInfoRequest)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.BAD_REQUEST);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(userInfoError)));

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(customExceptionUserInfo));

        Mono<UserInfoResponse> userInfoResponseMono = userInfoServiceImpl.getUserInfo(userInfoRequest);

        StepVerifier.create(userInfoResponseMono)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo("451");
                    assertThat(customEx.getDescription()).isEqualTo("Failed to fetch user information");
                })
                .verify();

    }

}