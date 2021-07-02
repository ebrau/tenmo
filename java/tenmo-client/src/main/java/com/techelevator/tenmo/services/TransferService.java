package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

    //Constructor
    public TransferService(String baseUrl) {
        this.url = baseUrl + "transfers";
    }

    //Methods
    public Transfer createTransfer(int recipientId, BigDecimal amount, int senderId)  {
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
    }

    //TODO: I had to had a 2nd parameter in order to use with current user's token
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer, AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

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
    }*/
}
