package com.busaridwan.lixon.controller;

import com.busaridwan.lixon.dto.request.AccountAuthorizationRequest;
import com.busaridwan.lixon.dto.request.LinxoPayRequest;
import com.busaridwan.lixon.dto.request.LinxoTransactionStatusRequest;
import com.busaridwan.lixon.dto.response.LinxoDepositResponse;
import com.busaridwan.lixon.dto.response.LinxoTokenResponse;
import com.busaridwan.lixon.service.LinxoService;
import com.busaridwan.lixon.service.LinxoTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linxo")
@RequiredArgsConstructor
public class LinxoController {

    private final LinxoTokenService tokenService;
    private final LinxoService service;

    @GetMapping("/token")
    public LinxoTokenResponse getToken(){
        return tokenService.getValidToken();
    }

    @PostMapping("/pay")
    public LinxoDepositResponse makePayment(@RequestBody LinxoPayRequest request){
        return service.makePayment(request);
    }

    @PostMapping("/status")
    public LinxoDepositResponse getStatus(@RequestBody LinxoTransactionStatusRequest request){
        return service.getStatus(request);
    }

    @PostMapping("/account")
    public Object authorizeAccount(@RequestBody AccountAuthorizationRequest request){
        return service.authorizeAccount(request);
    }
}
