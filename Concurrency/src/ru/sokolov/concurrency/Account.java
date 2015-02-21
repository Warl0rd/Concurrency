package ru.sokolov.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	
	private Lock lock;
	private int balance;

	public Account(int initialBalance) {
		this.balance = initialBalance;
		this.lock = new ReentrantLock();
	}
	
	public void withdraw(int amount) {
		this.balance -= amount;
	}
	
	public void deposit(int amount) {
		this.balance += amount;
	}

	public int getBalance() {
		return this.balance;
	}

	public Lock getLock() {
		return lock;
	}
	

}
