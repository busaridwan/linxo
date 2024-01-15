package com.busaridwan.lixon.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.busaridwan.lixon.dto.response.LinxoTokenResponse;
import com.busaridwan.lixon.dto.response.Response;
import com.busaridwan.lixon.util.RestCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinxoTokenService {
    private final RestCallService restCall;
    private static LinxoTokenResponse tokenObj;

    @Value("${linxo.authUrl}")
    private String authUrl;
    @Value("${linxo.grant_type}")
    private String grantType;
    @Value("${linxo.clientId}")
    private String clientId;
    @Value("${linxo.clientSecret}")
    private String clientSecret;
    @Value("${linxo.basic.auth}")
    private String basicAuth;

    private void getToken(){
        try{
        Response response = new Response();

        Map<String, Object> formData = new HashMap<>();
        formData.put("grant_type", grantType);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBasicAuth(getAuthorizationValue());

        log.info("MAKING REST CALL TO GET LINXO TOKEN");

        response = restCall.post(formData, authUrl, requestHeaders, LinxoTokenResponse.class);
        log.info("LINXO GET TOKEN RESPONSE CODE ::: {}", response.getResponseCode());

        tokenObj = (LinxoTokenResponse) response.getData();
        }catch (Throwable t){
            t.printStackTrace();
            log.debug(String.format("Token exception %s >> %s", t.getMessage(), t.getLocalizedMessage()));
            log.debug(Arrays.toString(t.getStackTrace()));
        }
    }

    private String getAuthorizationValue() {
        return basicAuth;
//        String value = clientId + ":" + clientSecret;
//        return Base64.getEncoder().encodeToString(value.getBytes());
    }
    public LinxoTokenResponse getValidToken(){
        log.info("Checking token validity");
        if(
                tokenObj == null ||
                        tokenObj.getAccessToken() == null ||
                        tokenObj.getAccessToken().equals("null") ||
                        tokenObj.getAccessToken().isEmpty()
        ){
            log.info("TOKEN IS NULL");
            getToken();
        }else {
            if(hasTokenExpired(tokenObj.getAccessToken())){
                log.info("TOKEN HAS EXPIRED");
                getToken();
            }
        }
        return tokenObj;
    }
    private boolean hasTokenExpired(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiresAt = decodedJWT.getExpiresAt();
        log.info("Session expired: " + expiresAt.before(new Date()));
        return expiresAt.before(new Date());
    }

}
