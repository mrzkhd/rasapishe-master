package com.rasapishe.customer.objectmodel;

/**
 * Created by Sara
 * on 2/16/2017.
 */

public class Factor {
    public enum PayStatus{
        PAID, UNPAID
    }

    private String id;
    private String businessTitle;
    private String reason;
    private int amount;
    private int count;
    private PayStatus payStatus;
    private String dueDate;

    public Factor(String id,String businessTitle, String reason, int amount, int count, PayStatus payStatus, String dueDate) {
        this.id = id;
        this.businessTitle = businessTitle;
        this.reason = reason;
        this.amount = amount;
        this.count = count;
        this.payStatus = payStatus;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public String getAmount() {
        return amount+" ریال";
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCount() {
        return  " - "  + count +  " بار ";
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTotalAmount() {
        return (amount * count )  +" ریال";
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPeriodic(){
        return count > 1;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
