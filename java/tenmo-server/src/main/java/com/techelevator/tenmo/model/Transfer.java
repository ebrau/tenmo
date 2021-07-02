package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String transferType;
    private String transferStatus;

    @Min(value = 1, message = "A non zero id is required for accountFrom")
    private int userFrom;

    @Min(value = 1, message = "A non zero id is required for accountTo")
    private int userTo;

    @Positive(message = "Transfer amount can't be negative")
    private BigDecimal amount;

    //Constructor
    public Transfer(){

    }

    public Transfer(int transferId, String transferType, String transferStatus, int userFrom, int userTo, BigDecimal amount){
      this.transferId = transferId;
      this.transferType = transferType;
      this.transferStatus = transferStatus;
      this.userFrom = userFrom;
      this.userTo = userTo;
      this.amount = amount;
    }

    //Getters

    public int getTransferId() {
        return transferId;
    }

    public String getTransferType() { return transferType; }

    public String getTransferStatus() {
        return transferStatus;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    //Setters
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setUserFrom(int accountFrom) {
        this.userFrom = accountFrom;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
