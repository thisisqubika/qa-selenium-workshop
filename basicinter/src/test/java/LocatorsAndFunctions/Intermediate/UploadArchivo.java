package LocatorsAndFunctions.Intermediate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;


public class UploadArchivo {
	WebDriver driver;
	String url = "https://seleniumjavalocators.neocities.org/pages/upload";

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get(url);

	}

	@Test
	public void uploadTest() {
		String fileName = "inicioSesionInvalido.xlsx";
		WebElement uploadInput = driver.findElement(By.id("fileInput"));
		String filePath = System.getProperty("user.dir") + "/src/test/resources/data/inicioSesionInvalido.xlsx";
		uploadInput.sendKeys(filePath);
		System.out.println(filePath);
//		uploadInput.sendKeys("/Users/gravielsosa/Desktop/graviel/decemberlabs/QA/QubikaSeleniumJavaWorkshop/qa-selenium-workshop/basicinter/src/test/resources/data/inicioSesionInvalido.xlsx");

		Wait<WebDriver> waitSubmit = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement result = waitSubmit.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message"))));

		Assert.assertEquals(result.getText(), "File \"" + fileName + "\" selected. Click \"Upload\" to proceed.");
		System.out.println(result.getText());

		WebElement uploadBtn = driver.findElement(By.xpath("//input[@value='Upload']"));
		uploadBtn.click();

		WebElement resultAfterSubmit = waitSubmit.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("message"))));

		Assert.assertEquals(resultAfterSubmit.getText(), "File uploaded successfully!");
		System.out.println(resultAfterSubmit.getText());
	}

	@AfterMethod
	public void finTest(ITestContext context) {
		driver.close();

	}
}
