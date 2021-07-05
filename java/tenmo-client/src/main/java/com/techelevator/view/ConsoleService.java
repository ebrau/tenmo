package com.techelevator.view;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Record;
import com.techelevator.tenmo.model.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private User user;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt + ": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt + ": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while (result == null);
		return result;
	}

	public void printAllUsers(AuthenticatedUser currentUser, User[] rawListUsers) {
		System.out.println("-------------------------------------------\r\n" +
				"Users\r\n" +
				"ID\t\tName\r\n" +
				"-------------------------------------------");
		for (User user : rawListUsers) {
			if (!user.getId().equals(currentUser.getUser().getId())) {
				System.out.println(user.getId() + "    " + user.getUsername());
			}
		}
		System.out.println("---------");
	}

	public void printAllTransfers(Record[] rawListRecords) {
		System.out.println("-------------------------------------------\r\n" +
				"Transfers\r\n" +
				"ID\t\t\tFrom/To\t\t\t\tAmount\r\n" +
				"-------------------------------------------");
		for (Record r : rawListRecords) {
			System.out.println(r.getTransferId() + "\t\t" + r.getUser() + "\t\t\t" + r.getAmount());
		}
		System.out.println("---------");
	}

	public void printTransferDetails(Record record) {
		System.out.println("-------------------------------------------\r\n" +
				"Transfer Details\r\n" +
				"-------------------------------------------");
		System.out.println("Id: " + record.getTransferId());
		System.out.println("From: " + record.getUserNameFrom());
		System.out.println("To: " + record.getUserNameTo());
		System.out.println("Type: " + record.getTransferTypeDesc());
		System.out.println("Status: " + record.getTransferStatusDesc());
		System.out.println("Amount: $" + record.getAmount());
		System.out.println("---------");
	}

	public Integer promptForRecipientId() {
		Integer recipientId = -1;
		do {
			System.out.print("Enter ID of user you are sending to (0 to cancel): ");
			String userInput = in.nextLine();
			try {
				recipientId = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				System.out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		}
		while (recipientId < 0);
		return recipientId;
	}

	public BigDecimal getTransferAmount() {
		BigDecimal transferAmount = new BigDecimal("-1.00");
		do {
			System.out.print("Enter amount: ");
			String userInput = in.nextLine();
			try {
				transferAmount = new BigDecimal(userInput);
			} catch (NumberFormatException e) {
				System.out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***. Enter a numeric value." + System.lineSeparator());
			}
		}
		while (transferAmount.intValue() < 0) ;
		return transferAmount;
	}

	public Integer promptForTransferId() {
		Integer transferId = -1;
		do {
			System.out.print("Please enter transfer ID to view details (0 to cancel): ");
			String userInput = in.nextLine();
			try {
				transferId = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				System.out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***. Enter a numeric value." + System.lineSeparator());
			}
		}
		while (transferId < 0);
		return transferId;
	}

}





