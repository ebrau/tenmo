package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferType;
    private int transferStatus;

    @Min(value = 1, message = "A non zero id is required for accountFrom")
    private int accountFrom;

    @Min(value = 1, message = "A non zero id is required for accountTo")
    private int accountTo;

    @Positive(message = "Transfer amount can't be negative")
    private BigDecimal amount;

    //Constructor
    public Transfer(){

    }
    public Transfer(int transferId, int transferType,int transferStatus, int accountFrom, int accountTo,BigDecimal amount){
      this.transferId = transferId;
      this.transferType = transferType;
      this.transferStatus = transferStatus;
      this.accountFrom = accountFrom;
      this.accountTo = accountTo;
      this.amount = amount;
    }

    //Getters

    public int getTransferId() {
        return transferId;
    }

    public int getTransferType() {
        return transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    //Setters
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
