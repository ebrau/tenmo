package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private BigDecimal zero = new BigDecimal("0.00");

   public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    
    @Override
    public Account getAccountByUserId(int userId){
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()){
            account = mapRowToAccount(results);
            return account;
        }
        return account;
        }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUserId(rs.getInt("user_id"));
        return account;
    }

    @Override
    public BigDecimal addtoBalance(BigDecimal amount, int userId) {
        String sql = "UPDATE accounts SET balance = (balance + ?)  WHERE user_id = ? RETURNING balance;";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, userId);
        } catch(ResourceAccessException re){
            System.out.println("Cannot connect to database");
        }
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, userId);
    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amount, int userFrom){
        String sql = "UPDATE accounts SET balance = (balance - ?)  WHERE user_id = ? RETURNING balance;";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, userFrom);
        } catch(ResourceAccessException re){
            System.out.println("Cannot connect to database");
        }
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, userFrom);
    }

}
