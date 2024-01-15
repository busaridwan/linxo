package com.busaridwan.lixon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinxoTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_expires_in")
    private String refreshExpire;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not-before-policy")
    private String beforePolicy;
    private String scope;
}
