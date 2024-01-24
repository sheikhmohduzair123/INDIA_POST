package com.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vo.MeterBalanceResponseVo;

public class MeterBalanceUtils {

    private static String ngpURL = PitneyBowesUrl.pbUrl;
    private static String accessToken = AuthTokenUtils.oauthToken;

    protected Logger log = LoggerFactory.getLogger(MeterBalanceUtils.class);

    public static MeterBalanceResponseVo getMeterBalance(String ngp) {
        String uri = ngpURL + "/ptm/v2/ngpId/" + ngp + "/balance";

        if (accessToken == null) {
            accessToken = AuthTokenUtils.getAccessToken();
        }
        String authorizatioKey = "Bearer " + accessToken.trim();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", authorizatioKey);
        headers.set("X-PB-TransactionId", UUID.randomUUID().toString());

        HttpEntity<MeterBalanceResponseVo> httpEntity = new HttpEntity<MeterBalanceResponseVo>(headers);
        MeterBalanceResponseVo responseBody=null;

        ResponseEntity<MeterBalanceResponseVo> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
                MeterBalanceResponseVo.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            responseBody = responseEntity.getBody();
        }
        else if (responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            AuthTokenUtils.oauthToken = null;
        }

        return responseBody;
    }

}
