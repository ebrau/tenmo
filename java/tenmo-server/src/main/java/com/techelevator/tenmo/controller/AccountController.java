package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping(path = "account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;

    //Constructor
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    //Request Methods
    @RequestMapping(path = "/{id}/balance", method = RequestMethod.GET)
    public BigDecimal seeBalance(@PathVariable("id") int userId) throws AccountNotFoundException {
        return accountDao.seeBalance(userId);
    }

}