package coll.app.boiler.model.request.userInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequest {
    @JsonProperty("Data")
    private Data data;
    @JsonProperty("Risk")
    private Risk risk;
}
