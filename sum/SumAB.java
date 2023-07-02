import java.util.Scanner;

public class SumAB {
	public static void main(String[] args) {
		System.out.println("Please input 2 numbers for addition : ");
		Scanner readInput = new Scanner(System.in);
		int a = readInput.nextInt();
		int b = readInput.nextInt();
		readInput.close();

		System.out.println("The sum of the 2 numbers is : " + Integer.sum(a, b));
	}
}
