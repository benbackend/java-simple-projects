package ATM;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;

	private ArrayList<User> users;

	private ArrayList<Account> accounts;

	public Bank(String name) {

		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Generate a new uni unique ID for a user.
	 *
	 * @return uuid String
	 */
	public String getNewUserUUID() {
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique = false;

		// continue looping until we get a unique ID
		do {
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}
			nonUnique = false;
			// Check to make sure it is unique
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		return uuid;
	}

	/**
	 * Generate a new uni unique ID for an account.
	 *
	 * @return uuid String
	 */
	public String getNewAccountUUID() {
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;

		// continue looping until we get a unique ID
		do {
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}
			nonUnique = false;
			// Check to make sure it is unique
			for (Account acct : this.accounts) {
				if (uuid.compareTo(acct.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		return uuid;
	}

	/**
	 * Add an account for the user
	 *
	 * @param anAcct the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	public User addUser(String firstName, String lastName, String pin) {

		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);

		// create a savings account for the user
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);

		return newUser;
	}

	public User userLogin(String userID, String pin) {

		for (User u : this.users) {

			// If we find the user
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}

		return null;
	}

	public String getName() {
		return this.name;
	}

}
