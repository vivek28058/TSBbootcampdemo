package coll.app.boiler.controller;


import coll.app.boiler.setup.BaseControllerMockedTest;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

public class ConsentControllerMockTest extends BaseControllerMockedTest {

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ consents")
    void getConsentControllerSuccessResponseMock(){

        String subject = "jacob";
        String actor = "jacob";
        String definition = "sample";
        String audience = "client1";
        String collaborator = "Alice";

        when(consentService.getConsents(subject,actor,definition,audience,collaborator))
                .thenReturn(Mono.just(READ_consents_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/consents?subject=jacob&actor=jacob&definition=sample&audience=client1&collaborator=Alice")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_consents_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - READ consents")
    void getConsentRecordByIdControllerSuccessResponseMock(){

        String consentRecordId = "f0c0ac1d-a493-426d-9211-224255145d28";
        String expand = "sample";

        when(consentService.getConsent(consentRecordId,expand))
                .thenReturn(Mono.just(READ_a_consent_response));

        webTestClient
                .get()
                .uri("/api/consent/v1/consents/f0c0ac1d-a493-426d-9211-224255145d28?expand=sample")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(READ_a_consent_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - CREATE a consent")
    void createConsentControllerSuccessResponseMock(){

        when(consentService.createConsent(consent_request))
                .thenReturn(Mono.just(CREATE_a_consent_response));

        webTestClient
                .post()
                .uri("/api/consent/v1/consents")
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(CREATE_a_consent_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - REPLACE a consent")
    void updateConsentRecordControllerSuccessResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.updateConsent(consentRecordId,consent_request))
                .thenReturn(Mono.just(REPLACE_a_consent_response));

        webTestClient
                .put()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(REPLACE_a_consent_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - UPDATE a consent")
    void patchConsentRecordControllerSuccessResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.pathConsent(consentRecordId,consent_request))
                .thenReturn(Mono.just(UPDATE_a_consent_response));

        webTestClient
                .patch()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(JsonUtil.toJson(UPDATE_a_consent_response));
    }

    @Test
    @DisplayName("Success Response - Internal Api - Consent - DELETE a consent")
    void deleteConsentRecordControllerSuccessResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.deleteConsent(consentRecordId))
                .thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ consents")
    void getConsentControllerErrorResponseMock(){

        String subject = "jacob";
        String actor = "jacob";
        String definition = "sample";
        String audience = "client1";
        String collaborator = "Alice";

        when(consentService.getConsents(subject,actor,definition,audience,collaborator))
                .thenThrow(customExceptionConsents);

        webTestClient
                .get()
                .uri("/api/consent/v1/consents?subject=jacob&actor=jacob&definition=sample&audience=client1&collaborator=Alice")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsents.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsents.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - READ consents")
    void getConsentRecordByIdControllerErrorResponseMock(){

        String consentRecordId = "f0c0ac1d-a493-426d-9211-224255145d28";
        String expand = "sample";

        when(consentService.getConsent(consentRecordId,expand))
                .thenThrow(customExceptionConsentByRecordId);

        webTestClient
                .get()
                .uri("/api/consent/v1/consents/f0c0ac1d-a493-426d-9211-224255145d28?expand=sample")
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsentByRecordId.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsentByRecordId.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - CREATE a consent")
    void createConsentControllerErrorResponseMock(){

        when(consentService.createConsent(consent_request))
                .thenThrow(customExceptionConsentCreate);

        webTestClient
                .post()
                .uri("/api/consent/v1/consents")
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsentCreate.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsentCreate.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - REPLACE a consent")
    void updateConsentRecordControllerErrorResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.updateConsent(consentRecordId,consent_request))
                .thenThrow(customExceptionConsentUpdate);

        webTestClient
                .put()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsentUpdate.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsentUpdate.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - UPDATE a consent")
    void patchConsentRecordControllerErrorResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.pathConsent(consentRecordId,consent_request))
                .thenThrow(customExceptionConsentPatch);

        webTestClient
                .patch()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .bodyValue(consent_request)
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsentPatch.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsentPatch.getDescription());
    }

    @Test
    @DisplayName("Error Response - Internal Api - Consent - DELETE a consent")
    void deleteConsentRecordControllerErrorResponseMock(){
        String consentRecordId = "7f577510-414e-4fea-bb2c-43d2b5119431";

        when(consentService.deleteConsent(consentRecordId))
                .thenThrow(customExceptionConsentDelete);

        webTestClient
                .delete()
                .uri("/api/consent/v1/consents/"+consentRecordId)
                .exchange()
                .expectStatus().isEqualTo(451)
                .expectBody()
                .jsonPath("status").isEqualTo(customExceptionConsentDelete.getStatus())
                .jsonPath("description").isEqualTo(customExceptionConsentDelete.getDescription());
    }


}
