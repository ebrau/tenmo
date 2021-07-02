package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;

public class TransferService {
    private String url;
    private RestTemplate restTemplate = new RestTemplate();
    private User user;
    private final String INVALID_TRANSFER_MSG = "Invalid Transfer. You did not supply the correct information.";
    private AuthenticatedUser currentUser;
    public static String AUTH_TOKEN = "";

    //Constructor
    public TransferService(String baseUrl) {
        this.url = baseUrl + "transfers";
    }

    //http://localhost:8080/transfers

    public int createTransfer(Transfer transfer, AuthenticatedUser currentUser){
        try {
            int result = restTemplate.exchange(url, HttpMethod.POST, makeTransferEntity(transfer, currentUser), Integer.class).getBody();
            //System.out.println(result);
            return result;
        } catch(Exception e){
           // System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
            return -1;
        }
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity makeTransferEntity(Transfer transfer, AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }
}

