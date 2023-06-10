package seleniumcommands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class automationDemoWebSite {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new ChromeDriver();
		driver.get("https://demo.automationtesting.in/Register.html");
		WebElement fname=driver.findElement(By.xpath("//input[@placeholder='First Name']"));
		WebElement lname=driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
		WebElement email=driver.findElement(By.xpath("//input[@type='email']"));

	}

}
