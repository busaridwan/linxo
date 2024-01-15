package com.busaridwan.lixon.util;

import com.busaridwan.lixon.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestCallService {

    private final RestTemplateBuilder builder;
    private <T> Response executeRequest(HttpMethod method, Object request, String requestUrl, HttpHeaders requestHeaders, Class<T> responseClass) {
        RestTemplate restTemplate = builder.build();
        Response response = new Response();

        try {

            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Arrays.asList(
                    MediaType.TEXT_PLAIN,
                    MediaType.APPLICATION_JSON
            ));

            HttpEntity<?> requestEntity = new HttpEntity<>(request, requestHeaders);

            log.info("Request Body: {}", JsonConverter.toJson(request, true));
            log.info("Url: {}", requestUrl);
            log.info("Request Entity: {}", JsonConverter.toJson(requestEntity, true));

            ResponseEntity<?> resp = restTemplate.exchange(requestUrl, method,
                    requestEntity, responseClass);

            log.info("REQUEST HANDLER RESPONSE ENTITY {}", JsonConverter.toJson(resp, true));

            if (resp.getBody() == null) {
                throw new IOException("No response from the host");
            }

            response
                    .setResponseCode(String.valueOf(resp.getStatusCode().value()))
                    .setResponseMessage(resp.getStatusCode().toString())
                    .setData(resp.getBody());

        } catch(IOException ex){
            log.info("LIXON Call error {}", ex);
            response.setResponseCode("99")
                    .setResponseMessage("FAILED");

        } catch (Exception ex) {
            log.info("Error from LIXON Call is  {}", ex);
            response.setResponseCode("99")
                    .setResponseMessage("FAILED");
        }

        return response;
    }

    public <T> Response post(Object request, String requestUrl, HttpHeaders requestHeaders, Class<T> responseClass) {
        return executeRequest(HttpMethod.POST, request, requestUrl, requestHeaders, responseClass);
    }

    public <T> Response get(String requestUrl, HttpHeaders requestHeaders, Class<T> responseClass) {
        return executeRequest(HttpMethod.GET, null, requestUrl, requestHeaders, responseClass);
    }


    public <T> Object postForObject(String requestUrl, HttpEntity<?> entity, Class<T> responseClass) throws RestClientException {
        RestTemplate restTemplate = builder.build();
        return restTemplate.postForObject(requestUrl, entity, responseClass);
    }

}
