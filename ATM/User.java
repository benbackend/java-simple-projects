package ATM;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	/**
	 * The first name of the user
	 */
	private String firstName;
	/**
	 * The last name of the user
	 */
	private String lastName;
	/**
	 * Unique identifier for the user
	 */
	private String uuid;
	/**
	 * MD5 hash of the user pin
	 */
	private byte pinHash[];
	/**
	 * List of accounts of the user
	 */
	private ArrayList<Account> accounts;

	/**
	 *
	 * @param firstName the user's first name
	 * @param lastName  the user's last name
	 * @param pin       the user's account pin number
	 * @param theBank   the bank object the customer belongs
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {

		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;

		// store the pin's MD5 hash, rather than the original value for security
		MessageDigest md = getMD5MD();
		this.pinHash = md.digest(pin.getBytes());

		// get a new unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		this.accounts = new ArrayList<Account>();

		System.out.printf("New user %s, %s with ID %s is created", lastName, firstName, this.uuid);
	}

	/**
	 * Add an account for the user
	 *
	 * @param anAcct the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	/**
	 *
	 * @return User UUID
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 *
	 * @param aPin
	 * @return
	 */
	public Boolean validatePin(String aPin) {

		MessageDigest md = getMD5MD();
		return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

	}

	// Make sure it catches the error
	private MessageDigest getMD5MD() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);

		int counter = 1;
		for (Account a : this.accounts) {
			System.out.printf("%d) %s\n", counter, a.getSummaryLine());
			counter += 1;
		}
	}

	public int numAccount() {
		return this.accounts.size();
	}

	public void printAccountsHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransactionHistory();
	}

	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}
