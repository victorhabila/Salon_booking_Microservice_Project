package com.projsalony.payload.response;

public class PaymentLinkResponse {
    private String payment_link_url;
    private String getPayment_link_id;

    public String getPayment_link_url() {
        return payment_link_url;
    }

    public void setPayment_link_url(String payment_link_url) {
        this.payment_link_url = payment_link_url;
    }

    public String getGetPayment_link_id() {
        return getPayment_link_id;
    }

    public void setGetPayment_link_id(String getPayment_link_id) {
        this.getPayment_link_id = getPayment_link_id;
    }
}
