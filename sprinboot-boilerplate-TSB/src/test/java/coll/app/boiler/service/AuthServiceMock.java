package coll.app.boiler.service;

import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.response.token.TokenResponse;
import coll.app.boiler.setup.BaseWebClientMockedTest;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AuthServiceMock extends BaseWebClientMockedTest {


    @Test
    @DisplayName("Success - Token External Api")
    void tokenSuccessResponseMock(){

        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(tokenUrl)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(tokenRequest)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(tokenResponse)));

      Mono<TokenResponse> tokenResponseMono =  authServiceImpl.getToken();

        StepVerifier.create(tokenResponseMono)
                .expectNext(tokenResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("Error - Token External Api")
     void tokenErrorResponseMock(){
        System.out.println(tokenRequest);

        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(tokenUrl)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(tokenRequest)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.BAD_REQUEST);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));

        Mono<TokenResponse> tokenResponseMono =  authServiceImpl.getToken();

        StepVerifier.create(tokenResponseMono)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionToken.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionToken.getDescription());
                })
                .verify();
    }


}
