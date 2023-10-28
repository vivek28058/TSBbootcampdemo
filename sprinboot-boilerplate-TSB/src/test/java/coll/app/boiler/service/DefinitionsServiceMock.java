package coll.app.boiler.service;

import coll.app.boiler.dto.response.definition.DefinitionsResponseDto;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.response.definitions.DefinitionsResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class DefinitionsServiceMock extends BaseWebClientMockedTest {

    //token for this definitions api
    @BeforeEach
    void setUpTokenDefinitionsExternalApiTest(){

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
    @DisplayName("Success Response - Consent - READ definitions - External API")
    void getDefinitionsMockTest(){

        String filter = "sample";


        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionConsentUrl)
                .queryParam("filter",filter);

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
                .thenReturn(Mono.just(JsonUtil.toJson(READ_definitions_response)));

      Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getDefinitions(filter);

        StepVerifier.create(definitionsResponse)
                .expectNext(READ_definitions_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - READ a definition - External API")
    void getDefinitionByIdMockTest(){

        String expand = "sample";
        String id = "share-my-email";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
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
                .thenReturn(Mono.just(JsonUtil.toJson(READ_a_definition_response)));

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getDefinitionById(id,expand);

        StepVerifier.create(definitionsResponse)
                .expectNext(READ_a_definition_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - READ localizations - External API")
    void getLocalizationsMockTest(){

        String id = "en-US";
        String filter = "sample";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
                .path("localizations")
                .queryParam("filter",filter);

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
                .thenReturn(Mono.just(JsonUtil.toJson(READ_localizations_response)));

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getLocalizations(id,filter);

        StepVerifier.create(definitionsResponse)
                .expectNext(READ_localizations_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Success Response -Consent - READ a localization - External API")
    void getLocalizationByIdMockTest(){

        String id = "en-US";
        String locale = "en-US";
        String expand = "sample";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
                .path("localizations")
                .path(locale)
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
                .thenReturn(Mono.just(JsonUtil.toJson(READ_a_localization_response)));

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getLocalizationById(id,locale,expand);

        StepVerifier.create(definitionsResponse)
                .expectNext(READ_a_localization_response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Error Response - Consent - READ definitions - External API")
    void getDefinitionsErrorMockTest(){

        String filter = "sample";


        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionConsentUrl)
                .queryParam("filter",filter);

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getDefinitions(filter);

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


        StepVerifier.create(definitionsResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionDefinitions.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionDefinitions.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - READ a definition - External API")
    void getDefinitionByIdErrorMockTest(){

        String expand = "sample";
        String id = "share-my-email";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
                .queryParam("expand",expand);

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getDefinitionById(id,expand);

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


        StepVerifier.create(definitionsResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionDefinitionById.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionDefinitionById.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - READ localizations - External API")
    void getLocalizationsErrorMockTest(){

        String id = "en-US";
        String filter = "sample";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
                .path("localizations")
                .queryParam("filter",filter);

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getLocalizations(id,filter);

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


        StepVerifier.create(definitionsResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionLocalizations.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionLocalizations.getDescription());
                })
                .verify();
    }

    @Test
    @DisplayName("Error Response -Consent - READ a localization - External API")
    void getLocalizationByIdErrorMockTest(){

        String id = "en-US";
        String locale = "en-US";
        String expand = "sample";

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(definitionUrl)
                .path(id)
                .path("localizations")
                .path(locale)
                .queryParam("expand",expand);

        Mono<DefinitionsResponseDto> definitionsResponse =  definitionsService.getLocalizationById(id,locale,expand);

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


        StepVerifier.create(definitionsResponse)
                .expectErrorSatisfies(t -> {
                    assertThat(t).isInstanceOf(CustomException.class);
                    CustomException customEx = (CustomException) t;
                    assertThat(customEx.getStatus()).isEqualTo(customExceptionLocalizationById.getStatus());
                    assertThat(customEx.getDescription()).isEqualTo(customExceptionLocalizationById.getDescription());
                })
                .verify();
    }
}

