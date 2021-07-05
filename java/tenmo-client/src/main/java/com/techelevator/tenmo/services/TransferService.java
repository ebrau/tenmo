package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Record;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.SQLOutput;
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
            return result;
        } catch(Exception e){
            return -1;
        }
    }

    public Record[] listTransfersById(AuthenticatedUser currentUser) {
        Record[] records = null;
        try {
            records = restTemplate.exchange(url + "/" + currentUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(currentUser), Record[].class).getBody();
        } catch (RestClientException ex) {
            System.out.println("We could not retrieve the list of transfers");
        } catch (NullPointerException ex) {
            System.out.println("Null pointer...");
        }
        return records;
    }

    //http://localhost:8080/transfers/3030/details

    public Record getRecordByTransferId(AuthenticatedUser currentUser, int transferId) {
        Record record = null;
        try {
            record = restTemplate.exchange(url + "/" + transferId + "/details", HttpMethod.GET, makeAuthEntity(currentUser), Record.class).getBody();
        } catch (RestClientException ex) {
            System.out.println("We could not retrieve the transfer details");
        } catch (NullPointerException ex) {
            System.out.println("Null pointer...");
        }
        return record;
    }

//http://localhost:8080/transfers/1001

    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

//Commented out on 7/3 to make one that accepts current User
/*    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }*/

    private HttpEntity makeTransferEntity(Transfer transfer, AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }
}

