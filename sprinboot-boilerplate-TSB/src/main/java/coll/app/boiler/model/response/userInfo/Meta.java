package coll.app.boiler.model.response.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    @JsonProperty("TotalPages")
    private Integer totalPages;
    @JsonProperty("FirstAvailableDateTime")
    private String firstAvailableDateTime;
    @JsonProperty("LastAvailableDateTime")
    private String lastAvailableDateTime;
}
