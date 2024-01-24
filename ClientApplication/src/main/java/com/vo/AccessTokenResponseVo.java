package com.vo;

public class AccessTokenResponseVo {
    private String access_token;
    private String tokenType;
    private String issuedAt;
    private String expiresIn;
    private String clientID;
    private String org;

    public AccessTokenResponseVo(){
        
    }

    public AccessTokenResponseVo(String access_token, String tokenType, String issuedAt, String expiresIn,
            String clientID, String org) {
        this.access_token = access_token;
        this.tokenType = tokenType;
        this.issuedAt = issuedAt;
        this.expiresIn = expiresIn;
        this.clientID = clientID;
        this.org = org;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

}
