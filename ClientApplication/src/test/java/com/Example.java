package com;

import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.util.AuthTokenUtils;
import com.util.CreateSignatureApiUtils;
import com.util.RefillAmountUtils;
import com.util.SHA256SingnatureUtils;
import com.vo.Evidence;
import com.util.MeterBalanceUtils;

public class Example {

    public static void main(String[] args) throws JsonProcessingException, NoSuchAlgorithmException {

        // try {
        // System.out.println(SHA256SingnatureUtils.getSHA256("201309202302100001",
        // "AA123461789ZZ"));
        // } catch (NoSuchAlgorithmException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // AuthTokenUtils.getAccessToken();
        // System.out.println(AuthTokenUtils.oauthToken);

        // boolean result = RefillAmountUtils.refillMeter(50.0,
        // "eaaf0f04-7818-4eeb-8476-e670000dcac0", "ITM_IN", "1");
        // System.out.println(result);

        // System.out.println(MeterBalanceUtils.getMeterBalance("eaaf0f04-7818-4eeb-8476-e670000dcac0"));

        // MeterBalanceSyncServiceImpl mtr = new MeterBalanceSyncServiceImpl();
        // System.out.println(mtr.syncBalance("eaaf0f04-7818-4eeb-8476-e670000dcac0",
        // "2013091675943253"));

        // System.out.println(CreateSignatureApiUtils.createSignature("1",
        // "eaaf0f04-7818-4eeb-8476-e670000dcac0",
        // "MTAxMTExMTAxMTEwMTEwMTExMTAwMTAxMDEwMTAxMDAxMTEwMTAxMDAwMTEwMDExMDExMTExMTAxMDAxMDAxMTAwMTAxMDAwMDAxMTAwMDAxMTAxMTAwMDExMDAxMDExMTAwMDExMTAxMTAxMDExMDAxMTAwMTEwMDAwMTAxMDAwMDAxMDAwMDAxMDEwMTEwMDExMDAwMTExMTAxMDAwMTEwMTExMTExMTExMTAxMDAxMDEwMDEwMDAxMTAxMDAxMDExMTAwMDAxMDAxMDEwMTExMDEwMDExMDExMTExMDExMTExMDAxMTAxMTExMTAxMDAxMDEwMDAwMDExMDAxMTExMDEwMDAwMQ==",
        // 150.0, "2023-01-25"));
        // Evidence evidence = new Evidence("hello", "local", 1, "hellosign",
        // "hellounsign", 1.0);
        // ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        // String json = ow.writeValueAsString(evidence);
        // System.out.println(json);
        // float a = 74.1f;
        // double b = 74.1;
        // System.out.println(a);
        // System.out.println(b);
        // System.out.println(a==b);
        System.out.println(SHA256SingnatureUtils.getSHA256("hello", "JSON"));
            // System.out.println(CreateSignatureApiUtils.createSignature("201307202302160001","eaaf0f04-7818-4eeb-8476-e670000dcac0","MDAxMDAwMDAxMDEwMTAxMDExMDExMTAxMTAxMTExMDAwMDAwMTAxMDAwMDEwMDExMDEwMTAwMTExMTEwMTEwMTEwMDEwMTAwMDEwMDAwMDAwMDExMDEwMTEwMTAxMDEwMTAxMTAxMTAxMDAxMTAxMTAxMTAwMDAxMDAwMDAxMDEwMTEwMDAxMDEwMTAwMDEwMTExMDAxMDAwMDAwMDAxMDAxMTAwMTEwMTExMDExMTAwMTExMDEwMTAwMTEwMDAwMDExMTAwMDAwMTEwMDExMTEwMTExMTExMTExMTExMTExMTAxMTAxMTEwMTAxMDExMDAwMDEwMDAwMDAwMDExMTAwMDExMDEwMDEwMTExMDEwMDExMDExMTEwMDExMDEwMDA=",161.82));
    }
}