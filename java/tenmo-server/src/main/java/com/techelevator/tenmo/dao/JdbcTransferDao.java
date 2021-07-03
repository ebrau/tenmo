package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Record;
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

    //TODO: Not sure it's working with results.getInt
    public int getAccountByUserId(int userFrom){
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFrom);
        if(results.next()) {
            return results.getInt(1);
        } else {
            throw new RuntimeException("Unable to lookup account id by user: "+ userFrom);
        }

    }

    @Override
    public List<Record> getTransfersById(int userFrom) {
        int accountFrom = getAccountByUserId(userFrom);
        List<Record> allTransfersById = new ArrayList<>();
        String sql = "SELECT t.transfer_id, 'To: '||u.username AS username, t.amount "
                + "FROM transfers t "
                + "INNER JOIN accounts a ON t.account_to = a.account_id "
                + "INNER JOIN users u ON a.user_id = u.user_id "
                + "WHERE t.account_from = ? "
                + "UNION "
                + "SELECT t.transfer_id, 'From: '||u.username AS username, t.amount "
                + "FROM transfers t "
                + "INNER JOIN accounts a ON t.account_from = a.account_id "
                + "INNER JOIN users u ON a.user_id = u.user_id "
                + "WHERE t.account_to = ? "
                + "ORDER BY transfer_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFrom, accountFrom);
        while (results.next()) {
            Record records = mapRowToRecord(results);
            allTransfersById.add(records);
        }
        return allTransfersById;
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
        BigDecimal amount = newTransfer.getAmount();
        int newTransferId = 0;

        if (fromAccount == toAccount) {
            System.out.println("You can't send money to yourself!!!");
        }

        if(amount.compareTo(fromAccount.getBalance()) == -1 || amount.compareTo(fromAccount.getBalance()) == 0) {

            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId,
                    fromAccount.getAccountId(), toAccount.getAccountId(), newTransfer.getAmount());

            accountDao.addtoBalance(amount, newTransfer.getUserTo());
            accountDao.subtractFromBalance(amount, newTransfer.getUserFrom());

            System.out.println("Transfer completed!");
        } else{
            System.out.println("Transfer failed due to insufficient funds");
        }

        //log.debug("created new Transfer with ID: "+newTransferId);
        return newTransferId;
    }

    private Record mapRowToRecord(SqlRowSet rs) {
        Record record= new Record();
        record.setTransferId(rs.getInt("transfer_id"));
        record.setUser(rs.getString("username"));
        record.setAmount(rs.getBigDecimal("amount"));
        return record;
    }

}

