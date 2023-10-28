package coll.app.boiler.controller;


import coll.app.boiler.exception.CustomException;
import coll.app.boiler.setup.BaseControllerMockedTest;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;

public class UserInfoControllerMock extends BaseControllerMockedTest {


    @Test
    @DisplayName("Success - Internal Api - User Info")
    public void userInfoControllerSuccessResponseMock(){

                 when(userInfoService.getUserInfo(userInfoRequest))
                .thenReturn(Mono.just(userInfoResponse));

        webTestClient
                .post()
                .uri("/api/v1/user-info")
                .bodyValue(userInfoRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(userInfoResponse));
    }

    @Test
    @DisplayName("Failed - Internal Api - User Info")
    public void userInfoControllerFailedResponseMock(){

        when(userInfoService.getUserInfo(userInfoRequest))
                .thenThrow(customException);

        webTestClient
                .post()
                .uri("/api/v1/user-info")
                .bodyValue(userInfoRequest)
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo("451")
                .jsonPath("description").isEqualTo("Failed to fetch user information");

    }


}
