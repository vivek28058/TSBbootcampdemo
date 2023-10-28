package coll.app.boiler.service;

import coll.app.boiler.dto.response.consent.ConsentResponseDto;
import coll.app.boiler.dto.response.consent.ConsentResponsePostPutPatchDto;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.response.consent.ConsentResponse;
import coll.app.boiler.model.response.consent.ConsentResponsePostPutPatch;
import coll.app.boiler.model.response.token.TokenResponse;
import coll.app.boiler.setup.BaseWebClientMockedTest;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


public class ConsentServiceMock extends BaseWebClientMockedTest {


    //token for this consent api
    @BeforeEach
    void setUpTokenConsentExternalApiTest(){

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
    @DisplayName("Success Response -Consent - READ consents - External API")
    void getConsentsMockTest(){

        String subject = "jacob";
        String actor = "jacob";
        String definition = "sample";
        String audience = "client1";
        String collaborator = "Alice";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .queryParam("subject",subject)
                .queryParam("actor",actor)
                .queryParam("definition",definition)
                .queryParam("audience",audience)
                .queryParam("collaborator",collaborator);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(READ_consents_response)));

        Mono<ConsentResponseDto> getConsentsResponse =  consentService.getConsents(
                subject,
                actor,
                definition,
                audience,
                collaborator
        );

        StepVerifier.create(getConsentsResponse)
                .expectNext(READ_consents_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - READ a consent - External API")
    void getConsentMockTest(){

        String consentRecordId = "f0c0ac1d-a493-426d-9211-224255145d28";
        String expand = "expand";


        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId)
                .queryParam("expand",expand);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(READ_a_consent_response)));

        Mono<ConsentResponseDto> getConsentResponse =  consentService.getConsent(
                consentRecordId,
                expand

        );

        StepVerifier.create(getConsentResponse)
                .expectNext(READ_a_consent_response)
                .verifyComplete();


    }


    @Test
    @DisplayName("Success Response -Consent - CREATE a consent - External API")
    void postConsentMockTest(){
        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(consentsUrl)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(CREATE_a_consent_response)));

        Mono<ConsentResponsePostPutPatchDto> createConsentResponse = consentService.createConsent(consent_request);

        StepVerifier.create(createConsentResponse)
                .expectNext(CREATE_a_consent_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - REPLACE a consent - External API")
    void putConsentsMockTest(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        when(webClientMock.put())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(componentsBuilder.toUriString())))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(REPLACE_a_consent_response)));

        Mono<ConsentResponsePostPutPatchDto> updateConsentResponse = consentService.updateConsent(consentRecordId,consent_request);

        StepVerifier.create(updateConsentResponse)
                .expectNext(REPLACE_a_consent_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - UPDATE a consent - External API")
    void pathConsentsMockTest(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        when(webClientMock.patch())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(componentsBuilder.toUriString())))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.just(JsonUtil.toJson(UPDATE_a_consent_response)));

        Mono<ConsentResponsePostPutPatchDto> pathConsentResponse = consentService.pathConsent(consentRecordId,consent_request);

        StepVerifier.create(pathConsentResponse)
                .expectNext(UPDATE_a_consent_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - DELETE a consent - External API")
    void deleteConsentsMockTest(){

        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        when(webClientMock.delete())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.NO_CONTENT);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(Void.class))
                .thenReturn(Mono.empty());

        Mono<Void> deleteConsentResponse = consentService.deleteConsent(consentRecordId);

        StepVerifier.create(deleteConsentResponse)
                .verifyComplete();

    }

    @Test
    @DisplayName("Error Response -Consent - READ consents - External API")
    void getConsentsErrorMockTest(){

        String subject = "jacob";
        String actor = "jacob";
        String definition = "sample";
        String audience = "client1";
        String collaborator = "Alice";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .queryParam("subject",subject)
                .queryParam("actor",actor)
                .queryParam("definition",definition)
                .queryParam("audience",audience)
                .queryParam("collaborator",collaborator);

        Mono<ConsentResponseDto> getConsentsResponse =  consentService.getConsents(
                subject,
                actor,
                definition,
                audience,
                collaborator
        );


        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));



        StepVerifier.create(getConsentsResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsents.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsents.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - READ a consent - External API")
    void getConsentErrorMockTest(){

        String consentRecordId = "f0c0ac1d-a493-426d-9211-224255145d28";
        String expand = "expand";


        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId)
                .queryParam("expand",expand);

        Mono<ConsentResponseDto> getConsentByRecordIdResponse =  consentService.getConsent(
                consentRecordId,
                expand

        );

        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));


        StepVerifier.create(getConsentByRecordIdResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsentByRecordId.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsentByRecordId.getDescription());
                })
                .verify();


    }


    @Test
    @DisplayName("Error Response -Consent - CREATE a consent - External API")
    void postConsentErrorMockTest(){

        Mono<ConsentResponsePostPutPatchDto> postConsentResponse = consentService.createConsent(consent_request);

        when(webClientMock.post())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(consentsUrl)))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));


        StepVerifier.create(postConsentResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsentCreate.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsentCreate.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - REPLACE a consent - External API")
    void putConsentsErrorMockTest(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        Mono<ConsentResponsePostPutPatchDto> putConsentResponse = consentService.updateConsent(consentRecordId,consent_request);


        when(webClientMock.put())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(componentsBuilder.toUriString())))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));


        StepVerifier.create(putConsentResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsentUpdate.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsentUpdate.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - UPDATE a consent - External API")
    void pathConsentsErrorMockTest(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        Mono<ConsentResponsePostPutPatchDto> pathConsentResponse = consentService.pathConsent(consentRecordId,consent_request);

        when(webClientMock.patch())
                .thenReturn(requestBodyUriMock);

        when(requestBodyUriMock.uri(eq(componentsBuilder.toUriString())))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.headers(any()))
                .thenReturn(requestBodyMock);

        when(requestBodyMock.bodyValue(eq(consent_request)))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.OK);

        when(responseSpecMock
                .onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();

        when(responseSpecMock.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));


        StepVerifier.create(pathConsentResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsentPatch.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsentPatch.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - DELETE a consent - External API")
    void deleteConsentsErrorMockTest(){

        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(consentsUrl)
                .path(consentRecordId);

        Mono<Void> deleteConsentResponse = consentService.deleteConsent(consentRecordId);

        when(webClientMock.delete())
                .thenReturn(requestHeadersUriMock);

        when(requestHeadersUriMock.uri(componentsBuilder.toUriString()))
                .thenReturn(requestHeadersMock);

        ArgumentCaptor<Consumer<HttpHeaders>> headersCaptor = ArgumentCaptor.forClass(Consumer.class);

        when(requestHeadersMock.headers(headersCaptor.capture()))
                .thenReturn(requestHeadersMock);

        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);

        when(responseSpecMock.getStatus())
                .thenReturn(HttpStatus.NO_CONTENT);

        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class)))
                .thenCallRealMethod();


        when(responseSpecMock.bodyToMono(Void.class))
                .thenReturn(Mono.error(new RuntimeException("Error")));


        StepVerifier.create(deleteConsentResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionConsentDelete.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionConsentDelete.getDescription());
                })
                .verify();

    }

}
