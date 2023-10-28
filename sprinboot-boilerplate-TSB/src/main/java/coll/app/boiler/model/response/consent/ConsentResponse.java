package coll.app.boiler.model.response.consent;

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
public record ConsentResponse(
         Embedded _embedded,
         Links _links,
         Integer count,
         Integer size
) {
}
