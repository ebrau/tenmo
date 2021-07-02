package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

    public String addTransfer(Transfer transfer){
        try {
            String message =  restTemplate.exchange(url, HttpMethod.POST, makeTransferEntity(transfer), String.class).getBody();
            System.out.println(message);
            return message;
        } catch(Exception e){
            System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
            return "Transfer unsuccessful";
        }
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }



    /*public Transfer addTransfer(Transfer transferRequest)  {
        //if (transferRequest == null) {
        //    throw new TransferServiceException(INVALID_TRANSFER_MSG);
       // }
        try {
            transferRequest = restTemplate.postForObject(url, makeTransferEntity(transferRequest), Transfer.class);
        } catch (RestClientResponseException ex) {
        }
        return transferRequest;
    }*/

    //FAILING
    /*public void addTransfer(Transfer transferRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transferRequest, headers);

        try {
            restTemplate.postForObject(url, entity, Transfer.class);
        } catch (RestClientResponseException ex) {
        }
    }*/


        //TODO: I had to had a 2nd parameter in order to use with current user's token

    /*//FAIL
    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }


    //FAIL
    private HttpEntity<Transfer> makeTransferEntity (Transfer transferRequest){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(currentUser.getToken());
            HttpEntity<Transfer> entity = new HttpEntity<>(transferRequest, headers);
            return entity;
        }*/

   /* private HttpEntity<Reservation> makeReservationEntity(Reservation reservation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Reservation> entity = new HttpEntity<>(reservation, headers);
        return entity;
    }*/

    /*public Reservation addReservation(String newReservation) throws HotelServiceException {
        Reservation reservation = makeReservation(newReservation);
        if(reservation == null) {
            throw new HotelServiceException(INVALID_RESERVATION_MSG);
        }

        try {
            reservation = restTemplate.postForObject(BASE_URL + "hotels/" + reservation.getHotelID() + "/reservations", makeReservationEntity(reservation), Reservation.class);
        } catch (RestClientResponseException ex) {
            throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }

        return reservation;
    }*

    ///Methods
     */
    /*public Transfer createTransfer(int recipientId, BigDecimal amount, int senderId)  {
        int transferId = new Random().nextInt(1000);
        int transferType = 2;
        int transferStatus = 2;
        Transfer transfer = new Transfer (transferId, transferType, transferStatus, senderId, recipientId, amount);
        return transfer;
    }

    public Transfer addTransfer(int recipientId, BigDecimal amount, int senderId) throws TransferServiceException {
        Transfer transfer = createTransfer(recipientId, amount, senderId);
        if (transfer == null) {
            throw new TransferServiceException(INVALID_TRANSFER_MSG);
        }
        try {
            transfer = restTemplate.postForObject(url, makeTransferEntity(transfer, currentUser), Transfer.class);
        } catch (RestClientResponseException ex) {
            throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return transfer;
    }*/
    }

