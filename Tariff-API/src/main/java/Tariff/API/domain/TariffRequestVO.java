package Tariff.API.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffRequestVO {
    @JsonProperty(required = false)
    private String service;
    @JsonProperty(required = false)
    private String sourcepin;
    @JsonProperty(required = false)
    private String destinationpin;
    @JsonProperty(required = false)
    private String weight;
    @JsonProperty(required = false)
    private String length;
    @JsonProperty(required = false)
    private String breadth;
    @JsonProperty(required = false)
    private String height;
    @JsonProperty(required = false)
    private double  INS_Value;
    @JsonProperty(required = false)
    private double COD_Value;



}

