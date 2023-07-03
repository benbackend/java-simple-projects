// import java.util.Scanner;

public class Reverse{

	String reverse(String str) {
		int length = str.length();
		if (length == 0) {
			return "";
		}
		return str.charAt(length - 1) + reverse(str.substring(0, length - 1));
	}

	public static void main(String[] args) {
		String input = "qwerty";
		Reverse rev = new Reverse();
		System.out.println(rev.reverse(input));
	}

}
