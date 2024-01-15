package com.busaridwan.lixon.dto.response;

import com.busaridwan.lixon.dto.request.LinxoInstructions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinxoDepositResponse {
    private String id;
    @JsonProperty("instant_payment")
    private String instantPayment;
    @JsonProperty("selected_capability")
    private String selectedCapability;
    @JsonProperty("order_status")
    private String orderStatus;
    @JsonProperty("creation_date")
    private String creationDate;
    @JsonProperty("auth_url")
    private String authUrl;
    @JsonProperty("redirect_url")
    private String redirectUrl;
    private List<LinxoInstructions> instructions;
    @JsonProperty("payer_time_zone")
    private String payerTimeZone;
}
