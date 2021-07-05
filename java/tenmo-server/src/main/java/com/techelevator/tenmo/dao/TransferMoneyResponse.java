package com.techelevator.tenmo.dao;

public class TransferMoneyResponse {
    boolean isSuccessful;
    String message;

    //Constructor
    public TransferMoneyResponse(boolean isSuccessful, String failureMessage) {
        this.isSuccessful = isSuccessful;
        this.message = failureMessage;
    }

    public TransferMoneyResponse() {
        this.isSuccessful = false;
        this.message = "";
    }

    //Getters
    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    //Setters
    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}