package coll.app.boiler.model.request.userInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructedAmount {

    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("Currency")
    private String currency;

}
