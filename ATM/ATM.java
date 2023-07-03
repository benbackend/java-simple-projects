package ATM;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Bank theBank = new Bank("Bank of Drausin");

		User aUser = theBank.addUser("John", "Doe", "1234");

		// add a Checking Account
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {

			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in the main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}

	public static User mainMenuPrompt(Bank theBank, Scanner sc) {

		// inits
		String userID;
		String pin;
		User authUser;

		boolean invalid = false;

		// prompt the user for user ID / Pin combo until a correct one is reached
		do {
			invalid = false;
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.println("Enter user ID: ");

			userID = sc.nextLine();

			System.out.println("Enter pin: ");

			pin = sc.nextLine();

			// try to get the user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				invalid = true;
				System.out.println("In correct user ID / pin combination. \n Please try again.");
			}
		} while (invalid);

		return authUser;
	}

	public static void printUserMenu(User theUser, Scanner sc) {

		// print a summary of the user's accounts
		theUser.printAccountsSummary();

		// init
		int choice;

		String[] actions = {
				"	1) Show account transaction history",
				"	2) Withdrawal",
				"	3) Deposit",
				"	4) Transfer",
				"	5) Quit",
		};

		// user menu
		boolean invalid = false;
		do {
			invalid = false;
			System.out.printf("Welcome %s, what would you like to do? \n", theUser.getFirstName());
			for (String a : actions) {
				System.out.println(a);
			}
			System.out.printf("\nEnter Choice \n");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				invalid = true;
				System.out.println("Invalid choice. Please choose 1-5");
			}
		} while (invalid);

		switch (choice) {
			case 1:
				ATM.showTransactionHistory(theUser, sc);
				break;
			case 2:
				ATM.withdrawFunds(theUser, sc);
				break;
			case 3:
				ATM.depositFunds(theUser, sc);
				break;
			case 4:
				ATM.transferFunds(theUser, sc);
				break;
			case 5:
				// gabble up rest of previous input
				sc.nextLine();
				return;
		}

		// redisplay this menu unless the user wants to quit
		ATM.printUserMenu(theUser, sc);
	}

	public static void showTransactionHistory(User theUser, Scanner sc) {

		int theAcct;
		int numAccount = theUser.numAccount();

		boolean invalid = false;
		do {
			invalid = false;
			System.out.printf("Enter Account Number (1-%d) of the account\n", numAccount);

			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct > numAccount) {
				invalid = true;
				System.out.println("Invalid Account. Please try again.");
			}

		} while (invalid);

		// print transaction history
		theUser.printAccountsHistory(theAcct);
	}

	public static void transferFunds(User theUser, Scanner sc) {
		// init
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		boolean invalid = false;

		// get the account to transfer from
		do {
			invalid = false;
			int numAccount = theUser.numAccount();
			System.out.printf("Enter the number (1-%d) of the account to transfer from\n", numAccount);
			fromAcct = sc.nextInt() - 1;

			if (fromAcct < 0 || fromAcct >= numAccount) {
				invalid = true;
				System.out.println("Invalid account. Please try again");
			}
		} while (invalid);

		acctBal = theUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {
			invalid = false;
			int numAccount = theUser.numAccount();
			System.out.printf("Enter the number (1-%d) of the account to transfer to\n", numAccount);
			toAcct = sc.nextInt() - 1;

			if (toAcct < 0 || toAcct >= numAccount) {
				invalid = true;
				System.out.println("Invalid account. Please try again");
			}
		} while (invalid);

		// get the amount to transfer
		do {
			invalid = false;
			System.out.printf("Enter the amount to transfer (max $%.02f)\n", acctBal);
			amount = sc.nextDouble();

			if (amount < 0.0 || amount > acctBal) {
				invalid = true;
				System.out.println("Invalid amount. Please try again");
			}
		} while (invalid);

		// finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1 * amount,
				String.format("Transfer to acct %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount,
				String.format("Transfer from acct %s", theUser.getAcctUUID(fromAcct)));
	}

	public static void withdrawFunds(User theUser, Scanner sc) {
		// init
		int fromAcct;
		double amount;
		double acctBal;
		String memo;

		boolean invalid = false;

		// get the account to withdrawing from
		do {
			invalid = false;
			int numAccount = theUser.numAccount();
			System.out.printf("Enter the number (1-%d) of the account to withdraw from\n", numAccount);
			fromAcct = sc.nextInt() - 1;

			if (fromAcct < 0 || fromAcct >= numAccount) {
				invalid = true;
				System.out.println("Invalid account. Please try again");
			}
		} while (invalid);

		acctBal = theUser.getAcctBalance(fromAcct);

		// get the amount to withdraw
		do {
			invalid = false;
			System.out.printf("Enter the amount to withdraw (max: $%.02f)\n", acctBal);
			amount = sc.nextDouble();

			if (amount < 0.0 || amount > acctBal) {
				invalid = true;
				System.out.println("Invalid amount. Please try again");
			}
		} while (invalid);

		// gabble up rest of previous input
		sc.nextLine();

		System.out.println("Enter a memo: ");
		memo = sc.nextLine();

		// finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
	}

	public static void depositFunds(User theUser, Scanner sc) {
		// init
		int toAcct;
		double amount;
		String memo;

		boolean invalid = false;

		// get the account to deposit to
		do {
			invalid = false;
			int numAccount = theUser.numAccount();
			System.out.printf("Enter the number (1-%d) of the account to deposit to\n", numAccount);
			toAcct = sc.nextInt() - 1;

			if (toAcct < 0 || toAcct >= numAccount) {
				invalid = true;
				System.out.println("Invalid account. Please try again");
			}
		} while (invalid);

		// get the amount to deposit
		do {
			invalid = false;
			System.out.println("Enter the amount to deposit");
			amount = sc.nextDouble();

			if (amount < 0.0) {
				invalid = true;
				System.out.println("Invalid amount. Please try again");
			}
		} while (invalid);

		// gabble up rest of previous input
		sc.nextLine();

		System.out.println("Enter a memo: ");
		memo = sc.nextLine();

		// finally do the transfer
		theUser.addAcctTransaction(toAcct, amount, memo);
	}

}
