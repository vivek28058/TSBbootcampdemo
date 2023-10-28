package coll.app.boiler.service;

import coll.app.boiler.dto.response.consent.ConsentResponseDto;
import coll.app.boiler.dto.response.consent.ConsentResponsePostPutPatchDto;
import coll.app.boiler.model.request.consent.ConsentRequest;
import coll.app.boiler.model.response.consent.ConsentResponse;
import coll.app.boiler.model.response.consent.ConsentResponsePostPutPatch;
import reactor.core.publisher.Mono;

public interface ConsentService {
    Mono<ConsentResponseDto> getConsents(
            String subject,
            String actor,
            String definition,
            String audience,
            String collaborator
    );
    Mono<ConsentResponseDto> getConsent(
            String consentRecordId,
            String expand
    );
    Mono<ConsentResponsePostPutPatchDto> createConsent(
            ConsentRequest consentRequest
    );

    Mono<ConsentResponsePostPutPatchDto> updateConsent(
            String consentRecordId,
            ConsentRequest consentRequest
    );

    Mono<ConsentResponsePostPutPatchDto> pathConsent(
            String consentRecordId,
            ConsentRequest consentRequest
    );


    Mono<Void> deleteConsent(
            String consentRecordId
    );

}
