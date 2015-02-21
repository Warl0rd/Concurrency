package ru.sokolov.concurrency;

public class Operations {

	public static void main(String[] args) {
		
		final Account a = new Account(1000);
		final Account b = new Account(2000);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					transfer(a, b, 1500);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
		try {
			transfer(b, a, 300);
		} catch (InsufficientFundsException e) {
			e.printStackTrace();
		}

	}

	protected static void transfer(Account acc1, Account acc2, int amount) throws InsufficientFundsException {

		if (acc1.getBalance() < amount)
			throw new InsufficientFundsException();
		
		synchronized (acc1) {
			synchronized (acc2) {
				acc1.withdraw(amount);
				acc2.deposit(amount);
			}
		}
		System.out.println("Transfer of "+amount+" completed successfully");
		
	}

}
