package coll.app.boiler.model.request.userInfo;

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
public class DeliveryAddress {
    @JsonProperty("AddressLine")
    private List<String> addressLine;
    @JsonProperty("StreetName")
    private String streetName;
    @JsonProperty("BuildingNumber")
    private String buildingNumber;
    @JsonProperty("PostCode")
    private String postCode;
    @JsonProperty("TownName")
    private String townName;
    @JsonProperty("CountySubDivision")
    private List<String> countySubDivision;
    @JsonProperty("Country")
    private String country;
}
