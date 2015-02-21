package ru.sokolov.concurrency;

import java.util.concurrent.TimeUnit;

public class Operations {

	private static final long WAIT_SEC = 3;

	public static void main(String[] args) {
		
		final Account a = new Account(1000);
		final Account b = new Account(2000);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					transfer(a, b, 500);
				} catch (InsufficientFundsException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
		try {
			transfer(b, a, 300);
		} catch (InsufficientFundsException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	protected static void transfer(Account acc1, Account acc2, int amount) throws InsufficientFundsException, InterruptedException {
		
		System.out.println("Try to lock Account 1");
		if (acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
			try {
				System.out.println("Lock Account 1");
				
				
				Thread.sleep(1000);
				
				System.out.println("Try to lock Account 2");
				if (acc2.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
					try {
						System.out.println("Lock Account 2");
						
						if (acc1.getBalance() < amount)
							throw new InsufficientFundsException();
						
						acc1.withdraw(amount);
						acc2.deposit(amount);
						System.out.println("Transfer of "+amount+" completed successfully");
					} finally {
						acc2.getLock().unlock();
						System.out.println("Release Account 2");
					}
				}
				else {
					System.out.println("Transaction has not been executed");
				}
			} finally {
				acc1.getLock().unlock();
				System.out.println("Release Account 1");
			}
		}
		else {
			System.out.println("Transaction has not been executed");
		}
		
	}

}
