package com.busaridwan.lixon.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinxoInstructions {
    private String amount;
    private String currency;
    private String label;
    private LinxoBeneficiaryReq beneficiary;
}
