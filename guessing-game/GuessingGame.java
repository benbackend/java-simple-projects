import java.util.Scanner;

class GuessingGame {
	static void print(String str) {
		System.out.println(str);
	}

	static int verify(int num, int ans) {
		if (ans > num) {
			print("The answer is larger.");
		} else if (ans < num) {
			print("The answer is smaller.");
		} else {
			print("Bingo!");
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {

		print("Please input the maximum range of the guessing game to start");
		Scanner sc = new Scanner(System.in);
		int max;
		do {
			max = sc.nextInt();
		} while (max < 1);

		int ans = (int) (Math.random() * max) + 1;
		// print("Answer is : " + ans);
		print("Game start ! Guess a number in 5 trials");

		int guess;
		for (int i = 0; i < 5; i++) {
			print("Guess " + i + " :");
			do {
				guess = sc.nextInt();
			} while (guess > max);
			guess = verify(guess, ans);
			if (guess == 1) {
				sc.close();
				return;
			}
		}
		print("You have exhausted the chances");

		sc.close();

	}
}
