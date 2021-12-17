public class Transfer {

	private static final Lock TRANSFER_SHARED_LOCK = new ReentrantLock(true); // true - keeping order

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

}
