package com.busaridwan.lixon.service;

import com.busaridwan.lixon.dto.request.AccountAuthorizationRequest;
import com.busaridwan.lixon.dto.request.LinxoPayRequest;
import com.busaridwan.lixon.dto.request.LinxoPaymentOrderRequest;
import com.busaridwan.lixon.dto.request.LinxoTransactionStatusRequest;
import com.busaridwan.lixon.dto.response.LinxoDepositResponse;
import com.busaridwan.lixon.dto.response.Response;
import com.busaridwan.lixon.util.RestCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class LinxoService {
    @Value("${linxo.redirect_url}")
    private String redirectUrl;
    @Value("${linxo.xforward}")
    private String xforward;
    @Value("${linxo.deposit}")
    private String payUrl;
    @Value("${linxo.transaction.status}")
    private String statusUrl;
    @Value("${linxo.authaccount.url}")
    private String authAccountUrl;

    private final RestCallService restCall;
    private final LinxoTokenService tokenService;
    public LinxoDepositResponse makePayment(LinxoPayRequest request) {
        LinxoDepositResponse response = new LinxoDepositResponse();
        try {
            LinxoPaymentOrderRequest orderRequest = new LinxoPaymentOrderRequest();
            orderRequest.setInstructions(request.getInstructions());
            orderRequest.setRedirectUrl(redirectUrl);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(tokenService.getValidToken().getAccessToken());
            requestHeaders.set(xforward, request.getRequestId());
            HttpEntity<LinxoPaymentOrderRequest> entity = new HttpEntity<>(orderRequest, requestHeaders);

            response = (LinxoDepositResponse) restCall.postForObject(payUrl, entity, LinxoDepositResponse.class);

        }catch (Exception e){
            e.printStackTrace();
            log.debug(String.format("Deposit exception %s >> %s", e.getMessage(), e.getLocalizedMessage()));
            log.debug(Arrays.toString(e.getStackTrace()));
        }
        return response;
    }

    public LinxoDepositResponse getStatus(LinxoTransactionStatusRequest request) {
        LinxoDepositResponse response = new LinxoDepositResponse();
        try {
            statusUrl = statusUrl.replace("{orderId}", request.getOrderId());
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(tokenService.getValidToken().getAccessToken());
            Response apiResponse = restCall.get(statusUrl, requestHeaders, LinxoDepositResponse.class);

            response = (LinxoDepositResponse) apiResponse.getData();
        }catch (Exception e){
            e.printStackTrace();
            log.debug(String.format("Status exception %s >> %s", e.getMessage(), e.getLocalizedMessage()));
            log.debug(Arrays.toString(e.getStackTrace()));
        }
        return response;
    }

    public Object authorizeAccount(AccountAuthorizationRequest request) {
        try {

        }catch (Exception e){
            e.printStackTrace();
            log.debug(String.format("Account Auth exception %s >> %s", e.getMessage(), e.getLocalizedMessage()));
            log.debug(Arrays.toString(e.getStackTrace()));
        }
    }
}
