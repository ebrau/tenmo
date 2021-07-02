package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping(path = "transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;

    //Constructor
    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    //Request Methods
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void createTransfer(@Valid @RequestBody Transfer incomingTransfer) {
        Transfer thisTransfer = incomingTransfer;
        //return transferDao.createTransfer();
        transferDao.createTransfer(incomingTransfer.getAccountFrom(), incomingTransfer.getAccountTo(), incomingTransfer.getAmount());
       // results = transferDao.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
       // return results;
    }

//	@RequestMapping(path = "transfer", method = RequestMethod.POST)
//	public String sendTransferRequest(@RequestBody Transfers transfer) {
//		String results = transfersDAO.sendTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
//		return results;
//	}

    // public void createTransfer(int senderId, int recipientId, BigDecimal amount)


}


///**
//     * Create a new reservation for a given hotel
//     *
//     * @param reservation
//     * @param hotelID
//     */
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping( path = "/hotels/{id}/reservations", method = RequestMethod.POST)
//    public Reservation addReservation(@Valid @RequestBody Reservation reservation, @PathVariable("id") int hotelID) throws HotelNotFoundException {
//        return reservationDao.create(reservation, hotelID);
//    }
