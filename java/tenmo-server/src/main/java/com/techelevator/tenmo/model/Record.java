package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Record {
    private int transferId;
    private String userNameFrom;
    private String userNameTo;
    private BigDecimal amount;
    private String transferStatus;
    private String transferType;
    private String user;


    //getters
    public String getUser() {
        return user;
    }

    public int getTransferId() {
        return transferId;
    }

    public String getUserNameFrom() {
        return userNameFrom;
    }

    public String getUserNameTo() {
        return userNameTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTransferStatus() {
        return transferStatus;
    }
    public String getTransferType() {
        return transferType;
    }

    //setters

    public void setUser(String user) {
        this.user = user;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setUserNameFrom(String userNameFrom) {
        this.userNameFrom = userNameFrom;
    }

    public void setUserNameTo(String userNameTo) {
        this.userNameTo = userNameTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
