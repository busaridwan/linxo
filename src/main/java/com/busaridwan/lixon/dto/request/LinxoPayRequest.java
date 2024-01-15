package com.busaridwan.lixon.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinxoPayRequest {
    private String requestId;
    private List<LinxoInstructions> instructions;
}
