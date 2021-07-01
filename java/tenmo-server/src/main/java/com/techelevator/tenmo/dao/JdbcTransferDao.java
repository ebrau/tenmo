package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private BigDecimal zero = new BigDecimal("0.00");

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> transferList(int userId) {
        List<Transfer>transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, username " +
                "FROM users u " +
                "INNER JOIN accounts a ON u.user_id = a.user_id "+
                "INNER JOIN transfers t ON a.account_id = t.account_from "+
                "WHERE u.user_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()){
            //transfers.add(mapRowToTransfer(results));
        }
       return transfers;
    }
   /* @Override
    public List<City> getCitiesByState(String stateAbbreviation) {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT city_id, city_name, state_abbreviation, population, area " +
                "FROM city " +
                "WHERE state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
        while (results.next()) {
            cities.add(mapRowToCity(results));
        }
        return cities;
    }*/

    @Override
    public Transfer getTransfer(int transferId) {
        return null;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        return null;
    }
}

