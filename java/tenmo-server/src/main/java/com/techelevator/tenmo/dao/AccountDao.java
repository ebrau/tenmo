package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal seeBalance(int userId);

    Account getAccountByUserId(int userId);

}
