package coll.app.boiler.model.request.consent;

import lombok.Builder;

@Builder
public record ConsentRequest(
         String status,
         String subject,
         String actor,
         String audience,
         Definition definition,
         String dataText,
         String purposeText,
         String data,
         String consentContext) {
}
