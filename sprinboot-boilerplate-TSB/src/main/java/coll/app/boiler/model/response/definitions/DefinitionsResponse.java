package coll.app.boiler.model.response.definitions;

import coll.app.boiler.model.response.consent.Definition;
import coll.app.boiler.util.RemoveValueZero;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(
        value = JsonInclude.Include.NON_EMPTY,
        content = JsonInclude.Include.NON_NULL,
        contentFilter = RemoveValueZero.class)
public record DefinitionsResponse(
         Links _links,
         Embedded _embedded,
         Integer size,
         Integer count,
         String id,
         String locale,
         String version,
         String dataText,
         String purposeText
) {
}
