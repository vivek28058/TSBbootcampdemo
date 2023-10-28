package coll.app.boiler.model.error.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoError {
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Errors")
    private List<Error> errors;
}
