package seleniumcommands;

import java.util.Random;

import com.github.javafaker.Faker;

public class RandomDataUtility {
	static Faker faker;
	static String fName;
	static String lName;
	static String password;

	public static String getfName() {
		faker = new Faker();
		fName = faker.name().firstName();
		return fName;
	}

	public static String getlName() {
		faker = new Faker();
		lName = faker.name().lastName();
		return lName;
	}

	public static String getRandomEmail() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 5;
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		String randomStringEmail = sb.toString() + "@gmail.com";
		return randomStringEmail;
	}
	public static String getPassWord() {
		faker =new Faker();
		password =faker.internet().password();
		return password;
	}

}
