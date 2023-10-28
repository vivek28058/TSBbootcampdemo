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
public class Risk {
    @JsonProperty("PaymentContextCode")
    private String paymentContextCode;
    @JsonProperty("MerchantCategoryCode")
    private String merchantCategoryCode;
    @JsonProperty("MerchantCustomerIdentification")
    private String merchantCustomerIdentification;
    @JsonProperty("DeliveryAddress")
    private DeliveryAddress deliveryAddress;
}
