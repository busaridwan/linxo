package com.busaridwan.lixon.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountEntity {
    private String type;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("national_identification")
    private String nationalIdentification;
    private String country;
}
