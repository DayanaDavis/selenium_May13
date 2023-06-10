package seleniumcommands;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocatorsAndCommands {

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
		driver.quit();
	}

	@Test
	public void TC_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actual_Title = driver.getTitle();
		String expected_Title = "Obsqura Testing11";
		Assert.assertEquals(actual_Title, expected_Title, "invalid title found");
		System.out.println();
	}

	@Test
	public void TC_002_verifySingleInputFieldMessage() {

		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement messagefield = driver.findElement(By.id("single-input-field"));
		String Message = "hi all--Good Morning";
		messagefield.sendKeys(Message);
		WebElement showbutton = driver.findElement(By.id("button-one"));
		showbutton.click();
		WebElement yourMessage = driver.findElement(By.id("message-one"));
		String expected_Message = "Your Message : ".concat(Message);
		String actual_message = yourMessage.getText();
		Assert.assertEquals(actual_message, expected_Message, "Invalid message");

	}

	@Test
	public void TC_003_verifyTwoInputFieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		String value_A = "10";
		String value_B = "13";
		String Value = Integer.toString(Integer.parseInt(value_A) + Integer.parseInt(value_B));
		String expected_Value = "Total A + B : ".concat(Value);
		WebElement firstValue = driver.findElement(By.xpath("//input[@id='value-a']"));
		firstValue.sendKeys(value_A);
		WebElement secondValue = driver.findElement(By.xpath("//input[@id='value-b']"));
		secondValue.sendKeys(value_B);
		WebElement total = driver.findElement(By.xpath("//button[@id='button-two']"));
		total.click();
		WebElement getTotal = driver.findElement(By.xpath("//div[@id='message-two']"));
		String actual_value = getTotal.getText();
		Assert.assertEquals(actual_value, expected_Value, "invalid answer");
	}

	@Test
	public void TC_004_verifyEmptyFieldValidation() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit form']"));
		submitButton.click();
		WebElement fnameValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustom01']/following-sibling::div[1]"));
		WebElement lnameValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustom02']/following-sibling::div[1]"));
		WebElement unameValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustomUsername']/following-sibling::div[1]"));
		WebElement cityValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustom03']/following-sibling::div[1]"));
		WebElement stateValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustom04']/following-sibling::div[1]"));
		WebElement zipValidationMessage = driver
				.findElement(By.xpath("//input[@id='validationCustom05']/following-sibling::div[1]"));
		WebElement agreeValidationMessage = driver
				.findElement(By.xpath("//input[@id='invalidCheck']/following-sibling::div[1]"));

		String expe_FnameMessage = "Please enter First name.";
		String exp_LnameMessage = "Please enter Last name.";
		String exp_UnameMessage = "Please choose a username.";
		String exp_CityMessage = "Please provide a valid city.";
		String exp_StateMessage = "Please provide a valid state.";
		String exp_ZipMessage = "Please provide a valid zip.";
		String exp_agreeMessage = "You must agree before submitting.";

		String actual_fnameValidationMessage = fnameValidationMessage.getText();
		String actual_lnameValidationMessage = lnameValidationMessage.getText();
		String actual_unameValidationMessage = unameValidationMessage.getText();
		String acttual_cityValidationMessage = cityValidationMessage.getText();
		String actual_stateValidationMessage = stateValidationMessage.getText();
		String actual_zipValidationMessage = zipValidationMessage.getText();
		String actual_agreeValidationMessage = agreeValidationMessage.getText();

		Assert.assertEquals(actual_fnameValidationMessage, expe_FnameMessage, "Invalid Fname");
		Assert.assertEquals(actual_lnameValidationMessage, exp_LnameMessage, "Invalid Lname");
		Assert.assertEquals(actual_unameValidationMessage, exp_UnameMessage, "invlaid uname");
		Assert.assertEquals(acttual_cityValidationMessage, exp_CityMessage, "invlaid ");
		Assert.assertEquals(actual_stateValidationMessage, exp_StateMessage, "invalid");
		Assert.assertEquals(actual_zipValidationMessage, exp_ZipMessage, "invalid");
		Assert.assertEquals(actual_agreeValidationMessage, exp_agreeMessage, "invalid");

	}

	@Test
	public void TC_005_verifySuccessfulFormSubmission() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement fname = driver.findElement(By.id("validationCustom01"));
		fname.sendKeys("josu");
		WebElement lname = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lname.sendKeys("poulose");
		WebElement uname = driver.findElement(By.id("validationCustomUsername"));
		uname.sendKeys("josu123");
		WebElement city = driver.findElement(By.id("validationCustom03"));
		city.sendKeys("chalakudy");
		WebElement state = driver.findElement(By.id("validationCustom04"));
		state.sendKeys("kerala");
		WebElement zip = driver.findElement(By.id("validationCustom05"));
		zip.sendKeys("1234");
		WebElement agree = driver.findElement(By.xpath("//input[@class='form-check-input']"));
		agree.click();
		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit form']"));
		submitButton.click();
		WebElement validMsg = driver.findElement(By.xpath("//div[@id='message-one']"));
		String actualMsg = validMsg.getText();
		String expValidMasg = "Form has been submitted successfully!";
		Assert.assertEquals(actualMsg, expValidMasg, "invlaid validation msg");

	}

	@Test
	public void TC_006_verifyEmptyStateAndZipcode() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement fname = driver.findElement(By.id("validationCustom01"));
		fname.sendKeys("josu");
		WebElement lname = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lname.sendKeys("poulose");
		WebElement uname = driver.findElement(By.id("validationCustomUsername"));
		uname.sendKeys("josu123");
		WebElement city = driver.findElement(By.id("validationCustom03"));
		city.sendKeys("chalakudy");
		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit form']"));
		submitButton.click();

		WebElement stateValidMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom04']/following-sibling::div[1]"));
		String actStateValidMsg = stateValidMsg.getText();
		WebElement zipValidMsg = driver
				.findElement(By.xpath("//input[@id='validationCustom05']/following-sibling::div[1]"));
		String actZipValidMsg = zipValidMsg.getText();

		String exp_StateMessage = "Please provide a valid state.";
		String exp_ZipMessage = "Please provide a valid zip.";

		Assert.assertEquals(actStateValidMsg, exp_StateMessage, "Invlaid message");
		Assert.assertEquals(actZipValidMsg, exp_ZipMessage, "Invalid mesaage");

	}

	@Test
	public void TC_007_verifyNewsLetterSubscription() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement signup_email = driver.findElement(By.cssSelector("input#newsletter-email"));
		signup_email.sendKeys("daya@gmail.com");
		WebElement suscribeButton = driver.findElement(By.cssSelector("input#newsletter-subscribe-button"));
		suscribeButton.click();
	}

	@Test
	public void TC_008_verifyInstantDempRequestForm() throws InterruptedException {
		driver.get("https://phptravels.com/demo/");
		WebElement fname = driver.findElement(By.cssSelector("input.first_name.input.mb1[placeholder='First Name']"));
		fname.sendKeys("dayaa");
		WebElement lname = driver.findElement(By.cssSelector("input.last_name.input.mb1[name='last_name']"));
		lname.sendKeys("david");
		WebElement bname = driver.findElement(By.cssSelector("input.business_name.input.mb1[name='business_name']"));
		bname.sendKeys("trading");
		WebElement email = driver.findElement(By.cssSelector("input.email.input.mb1[name='email']"));
		email.sendKeys("123@gmail.com");
		WebElement num1 = driver.findElement(By.xpath("//span[@id='numb1']"));
		WebElement num2 = driver.findElement(By.xpath("//span[@id='numb2']"));
		String num1_str = num1.getText();
		String num2_str = num2.getText();
		int num1_int = Integer.parseInt(num1_str);
		int num2_int = Integer.parseInt(num2_str);
		int sum = num1_int + num2_int;
		System.out.println("sum_int=" + sum);
		String expected_sum = Integer.toString(sum);
		WebElement sum_value = driver.findElement(By.xpath("//input[@id='number']"));
		sum_value.sendKeys(expected_sum);
		Thread.sleep(2000);
		WebElement submit_button = driver.findElement(By.cssSelector("button.btn.w100"));
		submit_button.click();
		WebElement completedBox = driver.findElement(By.cssSelector("div.completed"));
		Thread.sleep(4000);
		boolean status = completedBox.isDisplayed();
		System.out.println(status);

	}

	@Test
	public void TC_009_verifyQuitAndClose() {
		driver.get("https://demo.guru99.com/popup.php");
		WebElement click_button = driver.findElement(By.xpath("//a[text()='Click Here']"));
		click_button.click();
		// driver.close();
		//driver.quit();
	}

	@Test
	public void Tc_010_verifNavigateTo() {
		// driver.get("https://demowebshop.tricentis.com");
		driver.navigate().to("https://demowebshop.tricentis.com");
	}

	@Test
	public void TC_011_verifyReferesh() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement newsletter_email = driver.findElement(By.xpath("//input[@id='newsletter-email']"));
		newsletter_email.sendKeys("dayana@gmail.com");
		driver.navigate().refresh();
	}

	@Test
	public void TC_012_verifyForwardAndBackwardNavigation() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().back();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().forward();
	}

	@Test
	public void TC_013_verifyWebElementCommands() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subject = driver.findElement(By.xpath("//input[@id='subject']"));
		subject.sendKeys("selenium");
		WebElement description = driver.findElement(By.xpath("//textarea[@id='description']"));
		description.sendKeys("Automation Testing");
		subject.clear();// to clear an input field.
		WebElement submit = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		String class_value = subject.getAttribute("class");// element.getattribute();
		System.out.println(class_value);
		String tagvalue_subject = subject.getTagName();// element.tagname();
		System.out.println(tagvalue_subject);
		subject.sendKeys("selenium Automation");
		Thread.sleep(2000);
		submit.click();
		Thread.sleep(2000);
		WebElement message = driver.findElement(By.xpath("//div[@id='message-one']"));
		String act_message = message.getText();
		String expe_message = "Form has been submitted successfully!";
		Assert.assertEquals(act_message, expe_message, "invalid message");
	}

	@Test
	public void TC_014_verifyIsDisplayed() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subject = driver.findElement(By.xpath("//input[@id='subject']"));
		subject.sendKeys("selenium");
		boolean status = subject.isDisplayed();
		System.out.println(status);
		Assert.assertTrue(status, "subject field is not displayed ");
	}

	@Test
	public void TC_015_verifyIsSelected() {
		driver.get("https://selenium.obsqurazone.com/check-box-demo.php");
		WebElement single_checkbox = driver.findElement(By.xpath("//input[@id='gridCheck']"));
		boolean statusBeforClick = single_checkbox.isSelected();
		System.out.println(statusBeforClick);
		Assert.assertFalse(statusBeforClick, "checkbox is selected");
		single_checkbox.click();
		boolean statusAfterclick = single_checkbox.isSelected();
		System.out.println(statusAfterclick);
		Assert.assertTrue(statusAfterclick, "checkbox is not selected");
	}

	@Test
	public void TC_016_verifyIsEnabled() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement submitButton = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		boolean status = submitButton.isEnabled();
		System.out.println(status);
		Assert.assertTrue(status, "submit button is not enabled");
		Point point = submitButton.getLocation();
		System.out.println(point.x + "," + point.y);
		Dimension dim = submitButton.getSize();
		//JavascriptExecutor jse=(JavascriptExecutor) driver;//scroll up and down
		//jse.executeScript("window.scrollBy(0,250)");
		System.out.println(dim.height + "," + dim.width);
		String bgColor = submitButton.getCssValue("background-color");
		System.out.println(bgColor);
		System.out.println("font="+submitButton.getCssValue("font-size"));
		WebElement element = driver.findElement(By.tagName("input"));
		System.out.println(element);
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		System.out.println(elements);
	}

	@Test
	public void TC_17_verifyMessageDisplayedinNewTab() {
		driver.get("https://demoqa.com/browser-windows");
		WebElement newtabButton = driver.findElement(By.xpath("//button[@id='tabButton']"));
		boolean status = newtabButton.isEnabled();
		System.out.println(status);
		newtabButton.click();
		driver.navigate().to("https://demoqa.com/sample");
		WebElement samplepage = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
		String actmasg = samplepage.getText();
		String expmsg = "This is a sample page";
		Assert.assertEquals(actmasg, expmsg, "invalid msg");
		

	}

	@Test
	public void Tc_018_verifytheMessageDisplayedinNewWindow() {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println("parent=" + parentWindow);
		WebElement newWindow = driver.findElement(By.xpath("//button[@id='windowButton']"));
		newWindow.click();
		Set<String> handles = driver.getWindowHandles();
		System.out.println(handles);
		Iterator<String> handlesIds = handles.iterator();
		while (handlesIds.hasNext()) {
			String childwindow = handlesIds.next();
			if (!childwindow.equals(parentWindow)) {
				driver.switchTo().window(childwindow);
				WebElement samplepage = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
				String acttext = samplepage.getText();
				String exptext = "This is a sample page";
				Assert.assertEquals(acttext, exptext, "invalid text");
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	@Test
	public void TC_019_verifySimpleAlerts() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickme = driver.findElement(By.xpath("//button[@class='btn btn-success']"));
		clickme.click();
		Alert alert = driver.switchTo().alert();
		String alerttext = alert.getText();
		System.out.println(alerttext);
		alert.accept();
	}
	@Test
	public void TC_020_verifyconfirmAlert() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickme = driver.findElement(By.xpath("//button[@class='btn btn-warning']"));
		clickme.click();
		Alert alert = driver.switchTo().alert();
		String alerttext = alert.getText();
		System.out.println(alerttext);
		alert.dismiss();	
	}
	@Test
	public void TC_021_verifyPromptAlert() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clicForPrompt = driver.findElement(By.xpath("//button[@class='btn btn-danger']"));
		clicForPrompt.click();
		Alert alert=driver.switchTo().alert();
		alert.sendKeys("Dayana");
		System.out.println(alert.getText());
		alert.accept();
		WebElement actMsg=driver.findElement(By.xpath("//p[@id='prompt-demo']"));
		System.out.println(actMsg.getText());
		String actMsgee=actMsg.getText();
		String expMsg="You have entered 'Dayana' !";
		Assert.assertEquals(actMsgee, expMsg,"Invalid");
	}
	@Test
	public void TC_022_verifyTextInaFrame() {
		driver.get("https://demoqa.com/frames");
		List<WebElement> frames=driver.findElements(By.tagName("iframe"));
		int noOfFrames=frames.size();
		System.out.println(noOfFrames);
		//driver.switchTo().frame(3);//using index
		//driver.switchTo().frame("frame1");//using nameorId
		WebElement frame1 =driver.findElement(By.id("frame1"));
		driver.switchTo().frame(frame1);
		WebElement heading=driver.findElement(By.id("sampleHeading"));
		String headingText=heading.getText();
		System.out.println(headingText);
		driver.switchTo().parentFrame();
		//driver.switchTo().defaultContent();
	}
	@Test
	public void TC_023_verifyRightClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement rightClickMe=driver.findElement(By.xpath("//span[text()='right click me']"));
		Actions action=new Actions(driver);
		action.contextClick(rightClickMe).build().perform();	
	}
	@Test
	public void TC_024_verifyDoubleClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement doubleclickme=driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
		Actions actions=new Actions(driver);
		actions.doubleClick(doubleclickme).build().perform();
		Alert alert=driver.switchTo().alert();
		alert.accept();
	}
	@Test
	public void TC_025_verifyMouseHover() {
	driver.get("https://demoqa.com/menu/");
	WebElement mainItem2=driver.findElement(By.xpath("//a[text()='Main Item 2']"));
	Actions action=new Actions(driver);
	action.moveToElement(mainItem2).build().perform();
	WebElement subitem=driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
	action.moveToElement(subitem).build().perform();
	WebElement subsubitem=driver.findElement(By.xpath("//a[text()='Sub Sub Item 2']"));
	action.moveToElement(subsubitem).build().perform();
	subsubitem.click();
	} 
	@Test
	public void TC_026_verifyDragAndDrop() {
		driver.get("https://demoqa.com/droppable");
		WebElement dragme=driver.findElement(By.id("draggable"));
		WebElement drophere=driver.findElement(By.id("droppable"));
		Actions actions=new Actions(driver);
		actions.dragAndDrop(dragme, drophere).build().perform();
	}
	@Test
	public void TC_027_verifyClickAndHold() {
		driver.get("https://demoqa.com/resizable");
		//WebElement resizable=driver.findElement(By.id("resizableBoxWithRestriction"));
		WebElement resizable=driver.findElement(By.xpath("//div[@id='resizableBoxWithRestriction']/div[@class='text']"));
		Actions actions=new Actions(driver);
		actions.clickAndHold(resizable).build().perform();
		actions.dragAndDropBy(resizable,100,100).build().perform();	
	}
	@Test
	public void TC_028_verifysingleDropDown() {
		driver.get("https://demo.guru99.com/test/newtours/register.php");
		WebElement country=driver.findElement(By.xpath("//select[@name='country']"));
		Select select=new Select(country);
		//select.selectByVisibleText("ANGOLA");
		//select.selectByIndex(3);
		select.selectByValue("AMERICAN SAMOA");
	}
	@Test
	public void TC_029_verifyMultiDropDown() throws InterruptedException {
		driver.get("https://www.softwaretestingmaterial.com/sample-webpage-to-automate/");
		WebElement mutiselect=driver.findElement(By.xpath("//select[@class='spTextField']"));
		Select select=new Select(mutiselect);
		boolean status=select.isMultiple();
		if(status) {
			select.selectByIndex(1);
			select.selectByIndex(2);
		}
		List<WebElement> allOptions=select.getOptions();
		for(int i=0;i<=allOptions.size();i++){
			String value=allOptions.get(i).getText();
			System.out.println(value);
		}
		List<WebElement> allSelectedoptions=select.getAllSelectedOptions();
		for(int j=0;j<allSelectedoptions.size();j++) {
			String value2=allSelectedoptions.get(j).getText();
			System.out.println(value2);
		}
	}
	@Test
	public void TC_30_verifyFileUploadInSelenium() {
		driver.get("https://demo.guru99.com/test/upload/");
		WebElement choosefile=driver.findElement(By.id("uploadfile_0"));
		choosefile.sendKeys("C:\\Users\\davis\\Desktop\\Manual\\Assignment_bank.docx");
		WebElement terms=driver.findElement(By.id("terms"));
		terms.click();
		WebElement submit=driver.findElement(By.id("submitbutton"));
		submit.click();
	}
	@Test
	public void TC_31_verifyclickAndSendKeysUsingJavaScriptExecutor() {
		driver.get("https://demowebshop.tricentis.com/");
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("document.getElementById('newsletter-email').value='test@gmail.com'");
		js.executeScript("document.getElementById('newsletter-subscribe-button').click()");
	}
	@Test
	public void TC_32_verifyscrollDownOfaWebElement() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
	}
	@Test
	public void TC_33_verifyScrollIntoViewOfAnWebElment() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		WebElement linuxelement=driver.findElement(By.linkText("Linux"));
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",linuxelement);
	}
	@Test
	public void TC_34_verifyHorizontalScroll() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		WebElement vBScriptElement=driver.findElement(By.linkText("VBScript"));
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",vBScriptElement);
	}
	@Test
	public void TC_35_verifyTable() throws IOException {
		driver.get("https://www.w3schools.com/html/html_tables.asp");
		List<WebElement> rowElements=driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
		List<WebElement> cellElements=driver.findElements(By.xpath("//table[@id='customers']//tbody//tr//td"));
		List<ArrayList<String>> actGridData=TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements, cellElements);
		List<ArrayList<String>> expGridData=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx","Sheet1");
		System.out.println(actGridData);
		Assert.assertEquals(actGridData,expGridData,"Invaliid data found");
	}
	@Test
	public void TC_36_verifyFileUploadUsingRobotClass() throws AWTException, InterruptedException {
		driver.get("https://www.foundit.in/seeker/registration");
		StringSelection s = new StringSelection("C:\\Users\\davis\\Desktop\\Bank.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		WebElement fileupload=driver.findElement(By.xpath("//div[@class='uploadResume']"));
		fileupload.click();
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	@Test
	public void TC_37_verifyTable2() throws InterruptedException, IOException {
		driver.get("https://selenium.obsqurazone.com/table-sort-search.php");
		WebElement search=driver.findElement(By.xpath("//div[@id='dtBasicExample_filter']//input[@type='search']"));
		search.sendKeys("caesar");
		Thread.sleep(2000);
		List<WebElement> rowitems=driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr"));
		List<WebElement> cellitems=driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr//td"));
		List<ArrayList<String>> actGridData=TableUtility.get_Dynamic_TwoDimension_ObscuraTablElemnts(rowitems, cellitems);
		System.out.println(actGridData);	
		List<ArrayList<String>> expGridData=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx","Sheet2");
		System.out.println(expGridData);
		Assert.assertEquals(actGridData,expGridData,"Invalid data");
	}
	@Test
	public void TC_38_verifyWaitsInSelenium() {
		driver.get("https://demowebshop.tricentis.com/");
		/*page load wait*/
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		/*Implicit wait*/
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement email=driver.findElement(By.xpath("//input[@id='newsletter-email']"));
		email.sendKeys("test@gmail.com");
		/*Explicit wait*/
		WebElement subcrib_button = driver.findElement(By.xpath("//input[@id='newsletter-subscribe-button']"));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(subcrib_button));
		/*Fluent Wait*/
		FluentWait fwait=new FluentWait<WebDriver>(driver);
		fwait.withTimeout(Duration.ofSeconds(10));
		fwait.pollingEvery(Duration.ofSeconds(1));
		fwait.until(ExpectedConditions.visibilityOf(subcrib_button));
		subcrib_button.click();
	}
	
	
	
}
