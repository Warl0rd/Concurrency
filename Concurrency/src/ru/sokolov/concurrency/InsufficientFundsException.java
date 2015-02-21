package ru.sokolov.concurrency;

public class InsufficientFundsException extends Exception {

	@Override
	public void printStackTrace() {
		System.out.println("Not enougth funds on account.");
		super.printStackTrace();
	}
	
}
