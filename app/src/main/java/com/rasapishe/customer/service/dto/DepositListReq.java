package com.rasapishe.customer.service.dto;

/**
 * Created by Sara
 * on 2/17/2017.
 */
public class DepositListReq {
    private String customer_number;
    private String deposit_status;
    private String offset = "0";
    private String length = "100";

    public DepositListReq() {
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getDeposit_status() {
        return deposit_status;
    }

    public void setDeposit_status(String deposit_status) {
        this.deposit_status = deposit_status;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
