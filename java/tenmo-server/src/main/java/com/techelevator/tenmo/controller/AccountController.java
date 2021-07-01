package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    //Constructor
    public AccountController(AccountDao accountDao, UserDao userDao) {

        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //Request Methods
    @RequestMapping(path = "/{id}/balance", method = RequestMethod.GET)
    public BigDecimal seeBalance(@PathVariable("id") int userId) throws AccountNotFoundException {
        return accountDao.seeBalance(userId);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAll() throws UsernameNotFoundException {
        return userDao.findAll();
    }
}