package com.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vo.RefillAmountRequestVo;
import com.vo.RefillAmountResponseVo;

public class RefillAmountUtils {

    private static String ngpURL = PitneyBowesUrl.pbUrl;
    protected static Logger log = LoggerFactory.getLogger(RefillAmountUtils.class);

    private static String uri = ngpURL + "/ptm/v1/transaction/refill";
    private static String accessToken = AuthTokenUtils.oauthToken;

    public static ResponseEntity<RefillAmountResponseVo> refillMeter(double refillAmount, String ngpId, String customerTransactionId,
            String productId) {

        if (accessToken == null) {
            accessToken = AuthTokenUtils.getAccessToken();
        }
        String authorizatioKey = "Bearer " + accessToken.trim();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", authorizatioKey);
        headers.set("X-PB-TransactionId", UUID.randomUUID().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        RefillAmountRequestVo requestObj = new RefillAmountRequestVo();
        requestObj.setAmount(refillAmount);
        requestObj.setNgpId(ngpId);
        requestObj.setCustomerTransactionId(customerTransactionId);
        requestObj.setProductId(productId);

        HttpEntity<RefillAmountRequestVo> httpEntity = new HttpEntity<RefillAmountRequestVo>(requestObj, headers);

        ResponseEntity<RefillAmountResponseVo> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
                RefillAmountResponseVo.class);
        
        log.info(responseEntity.toString());
        return responseEntity;
    }

}
