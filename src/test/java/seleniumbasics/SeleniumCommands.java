package seleniumbasics;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class SeleniumCommands {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver=new ChromeDriver();
		//WebDriver driver=new FirefoxDriver();
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		String title=driver.getTitle();
		String currentUrl=driver.getCurrentUrl();
		System.out.println(currentUrl);
		String pageSource=driver.getPageSource();
		System.out.println(pageSource);
		//driver.close();	
	}

}
