package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vo.AccessTokenResponseVo;

public class AuthTokenUtils {

    protected static Logger log = LoggerFactory.getLogger(AuthTokenUtils.class);

    public static String oauthToken = null;

    private static String uri = PitneyBowesUrl.pbUrl + "/oauth/token";
    private static final String authrizationToken = PitneyBowesUrl.pbauthrizationToken;

    public static String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Basic " + authrizationToken.trim());
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> httpEntity = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<AccessTokenResponseVo> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
                AccessTokenResponseVo.class);
        AccessTokenResponseVo responseBody = responseEntity.getBody();

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            oauthToken = responseBody.getAccess_token();

        log.info("auth token: " + oauthToken);

        return oauthToken;
    }

}
