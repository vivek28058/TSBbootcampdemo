package coll.app.boiler.dto.response.consent;

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
public record ConsentResponseDto(
         Embedded _embedded,
         Links _links,
         Integer count,
         Integer size
) {
}
