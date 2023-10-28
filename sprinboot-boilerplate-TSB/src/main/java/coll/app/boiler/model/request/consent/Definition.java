package coll.app.boiler.model.request.consent;

import lombok.Builder;

@Builder
public record Definition(

         String id,
         String version,
         String locale) {
}
