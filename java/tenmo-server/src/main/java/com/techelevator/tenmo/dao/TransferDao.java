package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

  List<Transfer>transferList(int userId);

  Transfer getTransfer(int transferId);

  Transfer createTransfer(Transfer transfer);


}
