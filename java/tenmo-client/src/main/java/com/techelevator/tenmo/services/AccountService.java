package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private String url;
    private RestTemplate restTemplate = new RestTemplate();
    private User user;

    //Constructor
    public AccountService(String baseUrl) {
        this.url = baseUrl + "account/";
    }

    //http://localhost:8080/account/{id}/balance
    //http://localhost:8080/account/users

    //Make Auth Entity Method
    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

    //Methods
    public BigDecimal seeBalance(AuthenticatedUser currentUser) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(url + currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(currentUser), BigDecimal.class).getBody();
        } catch (RestClientException ex) {
            System.out.println("We could not retrieve your balance.");
        } catch (NullPointerException ex) {
            System.out.println("Null pointer...");
        }
        return balance;
    }

    public User[] findAll(AuthenticatedUser currentUser)  {
        User[] users = null;
        try {
            users = restTemplate.exchange(url + "users", HttpMethod.GET, makeAuthEntity(currentUser), User[].class).getBody();
        } catch (RestClientException ex) {
            System.out.println("We could not retrieve the list of users.");
        }
        return users;
    }
}
