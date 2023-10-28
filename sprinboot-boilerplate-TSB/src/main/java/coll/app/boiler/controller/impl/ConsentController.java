package coll.app.boiler.controller.impl;

import coll.app.boiler.controller.ConsentApiVersion;
import coll.app.boiler.dto.response.consent.ConsentResponseDto;
import coll.app.boiler.dto.response.consent.ConsentResponsePostPutPatchDto;
import coll.app.boiler.model.request.consent.ConsentRequest;
import coll.app.boiler.model.response.consent.ConsentResponse;
import coll.app.boiler.model.response.consent.ConsentResponsePostPutPatch;
import coll.app.boiler.service.ConsentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ConsentController implements ConsentApiVersion {

    private final ConsentService consentService;

    ConsentController(ConsentService consentService){
     this.consentService = consentService;
    }


    @GetMapping("/consents")
    public Mono<ConsentResponseDto> getConsentController(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "actor") String actor,
            @RequestParam(name = "definition") String definition,
            @RequestParam(name = "audience") String audience,
            @RequestParam(name = "collaborator") String collaborator)
    {

        return consentService.getConsents(
                subject,
                actor,
                definition,
                audience,
                collaborator
        );
    }

    @GetMapping("/consents/{consentRecordId}")
    public Mono<ConsentResponseDto> getConsentRecordByIdController(
            @PathVariable(name = "consentRecordId") String consentRecordId,
            @RequestParam(name = "expand", required = false) String expand)
    {
        return consentService.getConsent(
                consentRecordId,
                expand
        );
    }

    @PostMapping("/consents")
    public Mono<ConsentResponsePostPutPatchDto> createConsentController(
            @RequestBody ConsentRequest requestBody)
    {
        return consentService.createConsent(requestBody);
    }

    @PutMapping("/consents/{consentRecordId}")
    public Mono<ConsentResponsePostPutPatchDto> updateConsentRecordController(
            @PathVariable(name = "consentRecordId") String consentRecordId,
            @RequestBody ConsentRequest requestBody)
    {
        return consentService.updateConsent(
                consentRecordId,
                requestBody
        );
    }

    @PatchMapping("/consents/{consentRecordId}")
    public Mono<ConsentResponsePostPutPatchDto> patchConsentRecordController(
            @PathVariable(name = "consentRecordId") String consentRecordId,
            @RequestBody ConsentRequest requestBody)
    {
        return consentService.pathConsent(
                consentRecordId,
                requestBody
        );
    }

    @DeleteMapping("/consents/{consentRecordId}")
    public Mono<Void> deleteConsentRecordController(
            @PathVariable(name = "consentRecordId") String consentRecordId)
    {
        return consentService.deleteConsent(consentRecordId);
    }

}
