package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private BigDecimal zero = new BigDecimal("0.00");
    private TransferDao transferDao;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate, TransferDao transferDao) {

        this.jdbcTemplate = jdbcTemplate;
        this.transferDao = transferDao;
    }

    //Methods
    @Override
    public BigDecimal seeBalance(int userId) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        if (balance != null) {
            return balance;
        } else {
            return zero;
        }
    }

    public Account getAccountByUserId(int userId){
        Account account = null;
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()){
            account = mapRowToAccount(results);
            return account;

        } else {
            throw new RuntimeException("Unable to lookup account by userId " + userId);
        }
    }
    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUserId(rs.getInt("user_id"));
        return account;
    }


}
