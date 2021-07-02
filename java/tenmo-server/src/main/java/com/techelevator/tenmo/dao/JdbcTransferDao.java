package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private BigDecimal zero = new BigDecimal("0.00");
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
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

    public int getTransferTypeId(String transferType){
        String sql = "SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferType);
        if(results.next()) {
            return results.getInt(1);
        } else {
            throw new RuntimeException("Unable to lookup transferType "+transferType);
        }
    }

    public int getTransferStatusId(String transferStatus){
        String sql = "SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatus);
        if(results.next()) {
            return results.getInt(1);
        } else {
            throw new RuntimeException("Unable to lookup transferType "+transferStatus);
        }
    }

    @Override
    public Transfer getTransfer(int transferId) {
        return null;
    }

    @Override
    public int createTransfer(Transfer newTransfer){

        int transferTypeId = getTransferTypeId(newTransfer.getTransferType());
        int transferStatusId = getTransferStatusId(newTransfer.getTransferStatus());

        //Todo: We had to remove .getId() from line 70 & 71, I don't know why
        Account fromAccount = accountDao.getAccountByUserId(newTransfer.getUserFrom());
        Account toAccount = accountDao.getAccountByUserId(newTransfer.getUserTo());

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        int newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId, fromAccount.getAccountId(), toAccount.getAccountId(), newTransfer.getAmount());

        //log.debug("created new Transfer with ID: "+newTransferId);
        return newTransferId;
    }

    //TODO: Delete first attempt below
    /*//TODO: I haven't seen an example of a JDBC create method taking more than one parameter
    //We have only seen it doing "save(CatCard card)" where it takes the full object as a parameter
    //Do we need to the alter the parameters in the TransferController?
    @Override
    public void createTransfer(int senderId, int recipientId, BigDecimal amount) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES (2, 2, " +
                "(SELECT account_id FROM accounts WHERE user_id = ?), " +
                "(SELECT account_id FROM accounts WHERE user_id = ?), ?);";
        int newId = jdbcTemplate.update(sql, senderId, recipientId, amount);
    }*/
}
/* @Override
public City createCity(City city) {
        String sql = "INSERT INTO city (city_name, state_abbreviation, population, area) " +
        "VALUES (?, ?, ?, ?) RETURNING city_id;";
        Long newId = jdbcTemplate.queryForObject(sql, Long.class,
        city.getCityName(), city.getStateAbbreviation(), city.getPopulation(), city.getArea());

        return getCity(newId);
        }*/

