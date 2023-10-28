package coll.app.boiler.controller;


import coll.app.boiler.setup.BaseControllerMockedTest;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

public class DefinitionsControllerMock extends BaseControllerMockedTest {

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ definitions")
    void getDefinitionsControllerSuccessResponseMock(){

        String filter = "sample";

        when(definitionsService.getDefinitions(filter))
                .thenReturn(Mono.just(READ_definitions_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions?filter=sample")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_definitions_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ a definition")
    void getDefinitionByIdControllerSuccessResponseMock(){

        String id = "share-my-email";
        String expand = "sample";

        when(definitionsService.getDefinitionById(id,expand))
                .thenReturn(Mono.just(READ_a_definition_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/share-my-email?expand=sample")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_a_definition_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ localizations")
    void getLocalizationsControllerSuccessResponseMock(){

        String id = "share-my-email";
        String expand = "sample";

        when(definitionsService.getLocalizations(id,expand))
                .thenReturn(Mono.just(READ_localizations_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/share-my-email/localizations?filter=sample")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_localizations_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ a localization")
    void getLocalizationByIdControllerSuccessResponseMock(){

        String id = "en-US";
        String locale = "en-US";
        String expand = "sample";

        when(definitionsService.getLocalizationById(id,locale,expand))
                .thenReturn(Mono.just(READ_a_localization_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/en-US/localizations/en-US?expand=sample")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_a_localization_response));
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ definitions")
    void getDefinitionsControllerErrorResponseMock(){

        String filter = "sample";

        when(definitionsService.getDefinitions(filter))
                .thenThrow(customExceptionDefinitions);

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions?filter=sample")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionDefinitions.getStatus())
                .jsonPath("description").isEqualTo(customExceptionDefinitions.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ a definition")
    void getDefinitionByIdControllerErrorResponseMock(){

        String id = "share-my-email";
        String expand = "sample";

        when(definitionsService.getDefinitionById(id,expand))
                .thenThrow(customExceptionDefinitionById);

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/share-my-email?expand=sample")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionDefinitionById.getStatus())
                .jsonPath("description").isEqualTo(customExceptionDefinitionById.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ localizations")
    void getLocalizationsControllerErrorResponseMock(){

        String id = "share-my-email";
        String expand = "sample";

        when(definitionsService.getLocalizations(id,expand))
                .thenThrow(customExceptionLocalizations);

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/share-my-email/localizations?filter=sample")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionLocalizations.getStatus())
                .jsonPath("description").isEqualTo(customExceptionLocalizations.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ a localization")
    void getLocalizationByIdControllerErrorResponseMock(){

        String id = "en-US";
        String locale = "en-US";
        String expand = "sample";

        when(definitionsService.getLocalizationById(id,locale,expand))
                .thenThrow(customExceptionLocalizationById);

        webTestClient
                .get()
                .uri("/api/consent/v1/definitions/en-US/localizations/en-US?expand=sample")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionLocalizationById.getStatus())
                .jsonPath("description").isEqualTo(customExceptionLocalizationById.getDescription());
    }
}
