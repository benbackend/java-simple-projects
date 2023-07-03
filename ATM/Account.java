package ATM;

import java.util.ArrayList;

public class Account {
	private String name;

	private String uuid;

	private User holder;

	private ArrayList<Transaction> transactions;

	/**
	 *
	 * @param name    the name of the user
	 * @param holder  the holder object
	 * @param theBank the bank object
	 */
	public Account(String name, User holder, Bank theBank) {

		this.name = name;
		this.holder = holder;

		this.uuid = theBank.getNewAccountUUID();
		this.transactions = new ArrayList<Transaction>();

	}

	/**
	 *
	 * @return Account UUID
	 */
	public String getUUID() {
		return this.uuid;
	}

	public double getBalance() {
		double balance = 0.0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	public String getSummaryLine() {
		double balance = this.getBalance();

		// format the summary line, depending on whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s\n", this.uuid, balance, this.name);
		}
		return String.format("%s : $(%.02f) : %s\n", this.uuid, -1 * balance, this.name);

	}

	public void printTransactionHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	public void addTransaction(double amount, String memo) {
		Transaction tr = new Transaction(amount, memo, this);
		this.transactions.add(tr);
	}
}
