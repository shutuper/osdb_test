public class Transfer {

	private static final Lock TRANSFER_SHARED_LOCK = new ReentrantLock(true); // true - keeping order
	// First solution: (the disadvantage is that all transfers may be waiting for the one if it's locked)
	private void transfer(BankAccount fromAccount, BankAccount toAccount, int transferAmount) {
		BankAccount fromAccountTemp = fromAccount;
		BankAccount toAccountTemp = toAccount;

		TRANSFER_SHARED_LOCK.lock();
		synchronized (fromAccountTemp) {
			synchronized (toAccountTemp) {
				TRANSFER_SHARED_LOCK.unlock();
				fromAccount.withdraw(transferAmount);
				toAccount.deposit(transferAmount);
			}
		}
	}
	
	//Second solution: (the disadvantage is that all transfers are waiting for the end of one)
	private void transferSync(BankAccount fromAccount, BankAccount toAccount, int transferAmount) {
		TRANSFER_SHARED_LOCK.lock();
		fromAccount.withdraw(transferAmount);
		toAccount.deposit(transferAmount);
		TRANSFER_SHARED_LOCK.unlock();
	}
}
