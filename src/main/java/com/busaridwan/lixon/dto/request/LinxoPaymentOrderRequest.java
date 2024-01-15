package com.busaridwan.lixon.dto.request;

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
public class LinxoPaymentOrderRequest {
    @JsonProperty("redirect_url")
    private String redirectUrl;
    private List<LinxoInstructions> instructions;
}
