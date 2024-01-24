package Tariff.API.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TariffResponseVO {
    private String ValidationStatus;
    private String ChargeableWeight;
    private String BaseTariff;
    private String POD_ACKCharges;
    private String DDLCharges;
    private String VPCharges;
    private String INCharges;
    private String PickupCharges;
    private String ServiceTax;
    private String TotalTariff;
    private String CODCharges;

    @JsonProperty("Validation Status")
    public String getValidationStatus() {
        return ValidationStatus;
    }

    @JsonProperty("Chargeable Weight")
    public String getChargeableWeight() {
        return ChargeableWeight;
    }

    @JsonProperty("Base Tariff")
    public String getBaseTariff() {
        return BaseTariff;
    }

    @JsonProperty("POD/ACK Charges")
    public String getPOD_ACKCharges() {
        return POD_ACKCharges;
    }

    @JsonProperty("DDL Charges")
    public String getDDLCharges() {
        return DDLCharges;
    }

    @JsonProperty("VP Charges")
    public String getVPCharges() {
        return VPCharges;
    }

    @JsonProperty("INS Charges")
    public String getINCharges() {
        return INCharges;
    }

    @JsonProperty("Pickup Charges")
    public String getPickupCharges() {
        return PickupCharges;
    }

    @JsonProperty("Service Tax")
    public String getServiceTax() {
        return ServiceTax;
    }

    @JsonProperty("Total Tariff")
    public String getTotalTariff() {
        return TotalTariff;
    }

    @JsonProperty("COD Charges")
    public String getCODCharges() {
        return CODCharges;
    }

    // public Output(String validationStatus, String chargeableWeight,
    // String baseTariff, String POD_ACKCharges, String DDLCharges,
    // String VPCharges, String INCharges, String pickupCharges, String serviceTax,
    // String totalTariff, String CODCharges) {
    // this.ValidationStatus = validationStatus;
    // this.ChargeableWeight = chargeableWeight;
    // this.BaseTariff = baseTariff;
    // this.POD_ACKCharges = POD_ACKCharges;
    // this.DDLCharges = DDLCharges;
    // this.VPCharges = VPCharges;
    // this.INCharges = INCharges;
    // this.PickupCharges = pickupCharges;
    // this.ServiceTax = serviceTax;
    // this.TotalTariff = totalTariff;
    // this.CODCharges = CODCharges;
    // }
}
