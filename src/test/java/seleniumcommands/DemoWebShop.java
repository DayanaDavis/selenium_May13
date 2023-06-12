package seleniumcommands;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoWebShop {
	WebDriver driver;

	public void testInitialize(String browser) {
		if (browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			try {
				throw new Exception("invalid broswer");
			} catch (Exception e) {
				throw new RuntimeException(e);
				// TODO: handle exception
			}
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@BeforeMethod
	public void setUp() {
		testInitialize("chrome");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName() + ".png"));
        }
		// driver.close();
		//driver.quit();
	}
	@Test
	public void TC_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actual_Title = driver.getTitle();
		String expected_Title = "Obsqura Testing";
		Assert.assertEquals(actual_Title, expected_Title, "invalid title found");
		System.out.println();
	}
	@Test
	public void TC_002_verifyLogin() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login=driver.findElement(By.xpath("//a[@class='ico-login']"));
		String email="davis12345@gmail.com";
		String password="123456";
		login.click();
		WebElement emailInput=driver.findElement(By.xpath("//input[@id='Email']"));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		emailInput.sendKeys(email);
		WebElement passinput=driver.findElement(By.id("Password"));
		passinput.sendKeys(password);
		WebElement loginButton=driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		loginButton.click();
		WebElement account=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actAccountHolder=account.getText();
		System.out.println(actAccountHolder);
		Assert.assertEquals(actAccountHolder, email,"Invalid login");	
	}
	@Test
	public void TC_003_verifyRegistration() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement register=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
		register.click();
		String gender="female";
		String fname="albin";
		String lname="davis";
		String email="aalbin123@gmail.com";
		String password="123456";
		List<WebElement> genders=driver.findElements(By.xpath("//div[@class='gender']//label"));
		selectGender(genders,"female");
		WebElement inputfname=driver.findElement(By.id("FirstName"));
		WebElement inputlname=driver.findElement(By.id("LastName"));
		WebElement inputemail=driver.findElement(By.id("Email"));
		WebElement inputpass=driver.findElement(By.id("Password"));
		WebElement inputConfimPass=driver.findElement(By.id("ConfirmPassword"));
		inputfname.sendKeys(fname);
		inputlname.sendKeys(lname);
		inputemail.sendKeys(email);
		inputpass.sendKeys(password);
		inputConfimPass.sendKeys(password);
		WebElement RegisterButton=driver.findElement(By.id("register-button"));
		RegisterButton.click();
		WebElement account=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actAccountHolder=account.getText();
		Assert.assertEquals(actAccountHolder, email,"Invalid");	
	}
	public void selectGender(List<WebElement> genders,String gen) {
		
		for(int i=0;i<genders.size();i++) {
			String status=genders.get(i).getText();
			if(status.equalsIgnoreCase(gen)) {
				genders.get(i).click();
			}
		}	
	}
	@Test
	public void verifyTitleFromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
		String acttitle=driver.getTitle();
		String excelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheet="HomePage";
		String expTitle=ExcelUtility.readStringData(excelPath, sheet, 0, 1);
		Assert.assertEquals(acttitle, expTitle,"Invaid Title");
	}
	@Test
	public void verifyRegistrationPagefromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
		String ecxelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheet="Registration";
		WebElement register=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
		register.click();
		String actTitle=driver.getTitle();
		System.out.println(actTitle);
		String expTitle=ExcelUtility.readStringData(ecxelPath, sheet, 0, 1);
		Assert.assertEquals(actTitle, expTitle,"Invalid Title");
		String gender=ExcelUtility.readStringData(ecxelPath, sheet, 1, 1);
		String fname=ExcelUtility.readStringData(ecxelPath, sheet, 2, 1);
		String lname=ExcelUtility.readStringData(ecxelPath, sheet, 3, 1);;
		String email=ExcelUtility.readStringData(ecxelPath, sheet, 4, 1);;
		String password=ExcelUtility.readStringData(ecxelPath, sheet, 5, 1);;
		List<WebElement> genders=driver.findElements(By.xpath("//div[@class='gender']//label"));
		selectGender(genders,gender);
		WebElement inputfname=driver.findElement(By.id("FirstName"));
		WebElement inputlname=driver.findElement(By.id("LastName"));
		WebElement inputemail=driver.findElement(By.id("Email"));
		WebElement inputpass=driver.findElement(By.id("Password"));
		WebElement inputConfimPass=driver.findElement(By.id("ConfirmPassword"));
		inputfname.sendKeys(fname);
		inputlname.sendKeys(lname);
		inputemail.sendKeys(email);
		inputpass.sendKeys(password);
		inputConfimPass.sendKeys(password);
		WebElement RegisterButton=driver.findElement(By.id("register-button"));
		RegisterButton.click();
		WebElement account=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actAccountHolder=account.getText();
		Assert.assertEquals(actAccountHolder, email,"Invalid");		
	}
	@Test(dataProvider = "InvalidCredentials")
	public void verifyLoginWithInvalidData(String mailId,String pswrd) {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login=driver.findElement(By.xpath("//a[@class='ico-login']"));
		login.click();
		WebElement emailInput=driver.findElement(By.xpath("//input[@id='Email']"));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		emailInput.sendKeys(mailId);
		WebElement passinput=driver.findElement(By.id("Password"));
		passinput.sendKeys(pswrd);
		WebElement loginButton=driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		loginButton.click();
		WebElement acterror=driver.findElement(By.xpath("//div[@class='message-error']//div[@class='validation-summary-errors']//span"));
		String actErrorMsg=acterror.getText();
		String expMsg="Login was unsuccessful. Please correct the errors and try again.";
		Assert.assertEquals(actErrorMsg, expMsg,"Invalid");	
	}
	@DataProvider(name="InvalidCredentials")
	public Object[][] userCredentials() {
		Object[][] data= {{"davis12345@gmai.com","23456"},{"davi12345@gmail.com","123456"},{"davis12345@gmail.com","12345"}};
		return data;
	}
	@Test
	public void verifyRegistrationUsingRandomGenerator() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement register=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='ico-register']"));
		register.click();
		String gender="female";
		String fname=RandomDataUtility.getfName();
		String lname=RandomDataUtility.getlName();
		String email=RandomDataUtility.getRandomEmail();
		String password=RandomDataUtility.getPassWord();
		List<WebElement> genders=driver.findElements(By.xpath("//div[@class='gender']//label"));
		selectGender(genders,"female");
		WebElement inputfname=driver.findElement(By.id("FirstName"));
		WebElement inputlname=driver.findElement(By.id("LastName"));
		WebElement inputemail=driver.findElement(By.id("Email"));
		WebElement inputpass=driver.findElement(By.id("Password"));
		WebElement inputConfimPass=driver.findElement(By.id("ConfirmPassword"));
		inputfname.sendKeys(fname);
		inputlname.sendKeys(lname);
		inputemail.sendKeys(email);
		inputpass.sendKeys(password);
		inputConfimPass.sendKeys(password);
		WebElement RegisterButton=driver.findElement(By.id("register-button"));
		RegisterButton.click();
		WebElement account=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actAccountHolder=account.getText();
		Assert.assertEquals(actAccountHolder, email,"Invalid");		
	}
	@Test(dataProvider = "ValidLoginCredential")
	public void verifyLoginWithValidDataFromDataProvider(String email,String password) {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login=driver.findElement(By.xpath("//a[@class='ico-login']"));
		login.click();
		WebElement emailInput=driver.findElement(By.xpath("//input[@id='Email']"));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		emailInput.sendKeys(email);
		WebElement passinput=driver.findElement(By.id("Password"));
		passinput.sendKeys(password);
		WebElement loginButton=driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		loginButton.click();
		WebElement account=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actAccountHolder=account.getText();
		System.out.println(actAccountHolder);
		Assert.assertEquals(actAccountHolder, email,"Invalid login");		
	}
	@DataProvider(name="ValidLoginCredential")
	public Object[][] validLoginCredentials(){
		Object[][] data= {{"davis12345@gmail.com","123456"},{"aalbin123@gmail.com","123456"}};
		return data;
	}
	@Test
	public void verifyLoginWithParameters() {
		
		//"C:\Automation\seleniumTest"
	}
}
