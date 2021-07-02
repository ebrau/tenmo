package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

  List<Transfer>transferList(int userId);

  Transfer getTransfer(int transferId);

  int createTransfer(Transfer transfer);


}
