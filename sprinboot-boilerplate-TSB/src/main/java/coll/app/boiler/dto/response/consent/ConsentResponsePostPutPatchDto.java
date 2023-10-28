package coll.app.boiler.dto.response.consent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.ArrayList;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,content = JsonInclude.Include.NON_NULL)
public record ConsentResponsePostPutPatchDto(

         Links _links,
         String actor,
         String audience,
         ArrayList<String>collaborators,
         String createdDate,
         String dataText,
         Definition definition,
         String id,
         String purposeText,
         String status,
         String subject,
         String updatedDate
) {
}
