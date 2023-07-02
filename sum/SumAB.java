import java.util.Scanner;

public class SumAB {
	public static void main(String[] args) {
		System.out.println("Please input 2 floating numbers for addition : ");
		Scanner readInput = new Scanner(System.in);
		float a = readInput.nextFloat();
		float b = readInput.nextFloat();
		readInput.close();

		System.out.println("The sum of the 2 floating numbers is : " + Float.sum(a, b));
	}
}
