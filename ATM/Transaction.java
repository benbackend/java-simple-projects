package ATM;

import java.util.Date;

public class Transaction {
	private double amount;

	private Date timestamp;

	private String memo;

	private Account inAccount;
	// Because this is an ATM, there won't be a outAccount I guess

	/**
	 *
	 * @param amount
	 * @param inAccount
	 */
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}

	/**
	 *
	 * @param amount
	 * @param memo
	 * @param inAccount
	 */
	public Transaction(double amount, String memo, Account inAccount) {
		this(amount, inAccount);
		this.memo = memo;
	}

	public double getAmount() {
		return this.amount;
	}

	public String getSummaryLine() {
		if (this.amount >= 0) {
			return String.format("%s : $%.02f : %s\n", this.timestamp, this.amount, this.memo);
		}
		return String.format("%s : $(%.02f) : %s\n", this.timestamp, -1 * this.amount, this.memo);
	}
}
