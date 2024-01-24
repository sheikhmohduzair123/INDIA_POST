package com.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vo.CreateSignatureRequestVo;
import com.vo.CreateSignatureResponseVo;

public class CreateSignatureApiUtils {

    protected static Logger log = LoggerFactory.getLogger(CreateSignatureApiUtils.class);

    private static String uri = PitneyBowesUrl.pbUrl + "/ptm/v1/signature/create";
    private static String accessToken = AuthTokenUtils.oauthToken;

    public static ResponseEntity<CreateSignatureResponseVo> createSignature(String transactionId, String ngpId,
            String data,
            double parcelAmount) {

        if (accessToken == null) {
            accessToken = AuthTokenUtils.getAccessToken();
        }
        String authorizatioKey = "Bearer " + accessToken.trim();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizatioKey);
        headers.set("X-PB-TransactionId", UUID.randomUUID().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateSignatureRequestVo signatureRequestVo = new CreateSignatureRequestVo();
        signatureRequestVo.setCustomerTransactionId(transactionId);
        signatureRequestVo.setNgpId(ngpId);
        signatureRequestVo.setData(data);
        signatureRequestVo.setAmount(Double.valueOf(new DecimalFormat("0.000").format(parcelAmount)));
        signatureRequestVo.setTransactionDate(LocalDate.now().toString());
        signatureRequestVo.setProductId("ITM_IN");
        signatureRequestVo.setDataVersion(1);
        signatureRequestVo.setCountryId("IN");
        signatureRequestVo.setProductGroupCode("110108");
        signatureRequestVo.setPartnerId("247188770");
        signatureRequestVo.setCurrency("INR");

        HttpEntity<CreateSignatureRequestVo> httpEntity = new HttpEntity<CreateSignatureRequestVo>(signatureRequestVo,
                headers);

        log.info(signatureRequestVo.toString());
        ResponseEntity<CreateSignatureResponseVo> responseEntity = restTemplate
                .exchange(uri, HttpMethod.POST, httpEntity, CreateSignatureResponseVo.class);

        log.info(responseEntity.toString());
        return responseEntity;
    }

}
