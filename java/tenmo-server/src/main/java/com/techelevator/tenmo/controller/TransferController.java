package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferMoneyResponse;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Record;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;

    //Constructor
    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public TransferMoneyResponse createTransfer(@Valid @RequestBody Transfer incomingTransfer) {
        return transferDao.createTransfer(incomingTransfer);
    }

    @RequestMapping(value = "/transfers/{id}", method = RequestMethod.GET )
    public Record[] listTransfersById(@PathVariable("id") int userId) {
        List<Record> allTransfersById = transferDao.getTransfersById(userId);
        Record[] transfersArray = new Record[allTransfersById.size()];
        transfersArray = allTransfersById.toArray(transfersArray);
        return transfersArray;
    }

    @RequestMapping(value = "/transfers/{transferId}/details", method = RequestMethod.GET)
    public Record getRecordByTransferId(@PathVariable int transferId) {
        return transferDao.getRecordByTransferId(transferId);
    }

}



