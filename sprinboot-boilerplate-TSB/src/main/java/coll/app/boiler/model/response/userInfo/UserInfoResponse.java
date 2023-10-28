package coll.app.boiler.model.response.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse {

//    @JsonProperty("Data")
//    private Data data;
//    @JsonProperty("Risk")
//    private Risk risk;
    @JsonProperty("Links")
    private Links links;
    @JsonProperty("Meta")
    private Meta meta;

}
