package com.archelo.coupons.db.data;

public class MWG_GSA_S {
    private String AuthorizationToken;
    private String Secret;
    private String CheckoutDeliveryAddressSetFor;
    private String PseudoStoreId;
    private boolean isChangeOrder;
    private boolean isRecognizedShopper;

    public MWG_GSA_S(String authorizationToken, String secret, String checkoutDeliveryAddressSetFor, String pseudoStoreId, boolean isChangeOrder, boolean isRecognizedShopper) {
        this.AuthorizationToken = authorizationToken;
        this.Secret = secret;
        this.CheckoutDeliveryAddressSetFor = checkoutDeliveryAddressSetFor;
        this.PseudoStoreId = pseudoStoreId;
        this.isChangeOrder = isChangeOrder;
        this.isRecognizedShopper = isRecognizedShopper;
    }

    public String getAuthorizationToken() {
        return AuthorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.AuthorizationToken = authorizationToken;
    }

    public String getSecret() {
        return Secret;
    }

    public void setSecret(String secret) {
        this.Secret = secret;
    }

    public String getCheckoutDeliveryAddressSetFor() {
        return CheckoutDeliveryAddressSetFor;
    }

    public void setCheckoutDeliveryAddressSetFor(String checkoutDeliveryAddressSetFor) {
        this.CheckoutDeliveryAddressSetFor = checkoutDeliveryAddressSetFor;
    }

    public String getPseudoStoreId() {
        return PseudoStoreId;
    }

    public void setPseudoStoreId(String pseudoStoreId) {
        this.PseudoStoreId = pseudoStoreId;
    }

    public boolean isChangeOrder() {
        return isChangeOrder;
    }

    public void setChangeOrder(boolean changeOrder) {
        isChangeOrder = changeOrder;
    }

    public boolean isRecognizedShopper() {
        return isRecognizedShopper;
    }

    public void setRecognizedShopper(boolean recognizedShopper) {
        isRecognizedShopper = recognizedShopper;
    }

    @Override
    public String toString() {
        return "MWG_GSA_S{" +
                "AuthorizationToken='" + AuthorizationToken + '\'' +
                ", Secret='" + Secret + '\'' +
                ", CheckoutDeliveryAddressSetFor='" + CheckoutDeliveryAddressSetFor + '\'' +
                ", PseudoStoreId='" + PseudoStoreId + '\'' +
                ", isChangeOrder=" + isChangeOrder +
                ", isRecognizedShopper=" + isRecognizedShopper +
                '}';
    }
}
