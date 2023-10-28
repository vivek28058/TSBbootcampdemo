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
public class Initiation {

    @JsonProperty("EndToEndIdentification")
    private String endToEndIdentification;
    @JsonProperty("InstructedAmount")
    private InstructedAmount instructedAmount;
    @JsonProperty("CreditorAccount")
    private CreditorAccount creditorAccount;
    @JsonProperty("RemittanceInformation")
    private RemittanceInformation remittanceInformation;
}
