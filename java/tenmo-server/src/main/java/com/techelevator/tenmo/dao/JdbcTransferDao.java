package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Record;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
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
    public Record getRecordByTransferId(int transferId) {
        Record record = new Record();
        String sql = "SELECT t.transfer_id, t.amount, tt.transfer_type_desc, ts.transfer_status_desc, ua.username AS user_from, ub.username AS user_to\n" +
                "FROM transfers t\n" +
                "INNER JOIN transfer_statuses ts\n" +
                "ON ts.transfer_status_id = t.transfer_status_id\n" +
                "INNER JOIN transfer_types tt\n" +
                "ON t.transfer_type_id = tt.transfer_type_id\n" +
                "INNER JOIN accounts a ON t.account_from = a.account_id\n" +
                "INNER JOIN accounts b ON t.account_to = b.account_id\n" +
                "INNER JOIN users ua ON ua.user_id = a.user_id\n" +
                "INNER JOIN users ub ON ub.user_id = b.user_id\n" +
                "WHERE t.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while (results.next()) {
            record = mapRowToCompleteRecord(results);
        }
        return record;
    }

    @Override
    public TransferMoneyResponse createTransfer(Transfer newTransfer){
        TransferMoneyResponse response = new TransferMoneyResponse();
        response.isSuccessful = false;

        int transferTypeId = getTransferTypeId(newTransfer.getTransferType());
        int transferStatusId = getTransferStatusId(newTransfer.getTransferStatus());

        Account fromAccount = accountDao.getAccountByUserId(newTransfer.getUserFrom());
        Account toAccount = accountDao.getAccountByUserId(newTransfer.getUserTo());
        BigDecimal amount = newTransfer.getAmount();

        if (toAccount == null) {
            response.message = "That user does not exist. Try again!";

        } else if (fromAccount.getAccountId() == toAccount.getAccountId()) {
            response.message = "You can't send money to yourself!!!";

        } else if (!(amount.compareTo(fromAccount.getBalance()) == -1 || amount.compareTo(fromAccount.getBalance()) == 0)) {
            response.message = "Transfer failed due to insufficient funds.";

        } else {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
            jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId,
                    fromAccount.getAccountId(), toAccount.getAccountId(), newTransfer.getAmount());

            accountDao.addtoBalance(amount, newTransfer.getUserTo());
            accountDao.subtractFromBalance(amount, newTransfer.getUserFrom());

            response.isSuccessful = true;
            response.message = "Transfer completed!";
        }

        return response;

    }

    private Record mapRowToRecord(SqlRowSet rs) {
        Record record= new Record();
        record.setTransferId(rs.getInt("transfer_id"));
        record.setUser(rs.getString("username"));
        record.setAmount(rs.getBigDecimal("amount"));
        return record;
    }

    private Record mapRowToCompleteRecord(SqlRowSet rs) {
        Record record= new Record();
        record.setTransferId(rs.getInt("transfer_id"));
        record.setAmount(rs.getBigDecimal("amount"));
        record.setTransferTypeDesc(rs.getString("transfer_type_desc"));
        record.setTransferStatusDesc(rs.getString("transfer_status_desc"));
        record.setUserNameFrom(rs.getString("user_from"));
        record.setUserNameTo(rs.getString("user_to"));
        return record;
    }

}

