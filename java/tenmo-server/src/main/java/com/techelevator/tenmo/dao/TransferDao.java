package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Record;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

  List<Record> getTransfersById(int userFrom) ;

  //Transfer getTransfer(int transferId);

  int createTransfer(Transfer transfer);

  Record getRecordByTransferId(int transferId);


}
