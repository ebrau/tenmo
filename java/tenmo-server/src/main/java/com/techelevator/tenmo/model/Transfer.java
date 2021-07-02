package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;

    //@Min(value = 1, message = "A non zero id is required for accountFrom")
    private int accountFrom;

   // @Min(value = 1, message = "A non zero id is required for accountTo")
    private int accountTo;

    @Positive(message = "Transfer amount can't be negative")
    private BigDecimal amount;

    //Constructor
    public Transfer(){

    }
    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount){
      this.transferId = transferId;
      this.transferTypeId = transferTypeId;
      this.transferStatusId = transferStatusId;
      this.accountFrom = accountFrom;
      this.accountTo = accountTo;
      this.amount = amount;
    }

    //Getters

    public int getTransferId() {
        return transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
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

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
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
