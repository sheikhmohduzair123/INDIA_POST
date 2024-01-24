package com.util;

import com.domain.Parcel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256SingnatureUtils {

    protected static Logger log = LoggerFactory.getLogger(CreateSignatureApiUtils.class);

    public static String getSHA256(String str1, String str2) throws NoSuchAlgorithmException {
        String key = str1 + str2;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
       
        return bytesToBase64(encodedhash);
    }

    private static String bytesToBase64(byte[] encodedhash) {
        
        String finalReult = Base64.getEncoder().encodeToString(encodedhash);
        log.info("base 64 encoded sha256: {}",finalReult);
        return finalReult;
    }
    
    public static String createParcelHex(Parcel parcel) throws NoSuchAlgorithmException {
        return getSHA256(parcel.getLabelCode(), parcel.getTrackId());
    }
}
