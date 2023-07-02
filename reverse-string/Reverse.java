// import java.util.Scanner;

public class Reverse{

	public static void main(String[] args) {
		String input = "123456";
		int length = input.length();
		char[] res = input.toCharArray();
		for (int i = 0; i <= length / 2; i++) {
			if (i == length / 2 ) {
				if (length % 2 == 1) {
					res[i] = input.charAt(i);
				}
				System.out.println(res);
				return;
			}
			char head = input.charAt(i);
			char tail = input.charAt(length - i - 1);

			res[length - i - 1] = head;
			res[i] = tail;
		}
	}

}
