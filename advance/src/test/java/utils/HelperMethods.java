package util;

import advancesWorkShop.utils.constants.Constant;
import advancesWorkShop.utils.constants.DataConstantQueries;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Data
public class HelperMethods {
	protected static String BLOCK_UI_XPATH = "//*[@class='block-ui-wrapper active']";
	WebDriver driver;
	@Getter
	private static String browsers;
	private static final String localEnvironment = "LOCAL";
	private static final String qaEnvironment = "QA";
	private static final String demoEnvironment = "DEMO";
	private static final String prodEnvironment = "PROD";
	private static final String devEnvironment = "DEV";
	public static String environments;
	protected RequestFactory requestFactory;

	public HelperMethods(WebDriver driver) {
		this.driver = driver;
	}

	protected static String testEnvironments() {
		setEnvironments(System.getenv("ENVIRONMENTS"));
		setEnvironments(getEnvironments() != null ? getEnvironments() : "QA");

		switch (getEnvironments().toLowerCase()) {
			case "qa":
				return qaEnvironment;
			case "demo":
				return demoEnvironment;
			case "prod":
				return prodEnvironment;
			default:
				return devEnvironment;
		}
	}

	/**
	 * This getPassword method gets the map of environment variables defined in the system
	 *
	 * @param key it's the key of the password
	 * @return the value of the password defined on the environment variables
	 */
	public static String getPassword(String key) {
		return System.getenv().get(key);
	}

	public static String specificDate(int month, int day) {
		LocalDate localDate = LocalDate.now();
		return (localDate.withDayOfMonth(month).withMonth(day)).toString();
	}

	/**
	 * Create a Date
	 *
	 * @param option it is the part of the Date that you will need
	 */
	public static int currentYearDayOrMonthOfDate(String option) {
		LocalDate currentDate = LocalDate.now();
		switch (option) {
			case "year":
				return currentDate.getYear();
			case "day":
				return currentDate.getDayOfMonth();
			case "month":
				return currentDate.getMonthValue();
			default:
				throw new IllegalStateException("This value does not exist: " + option);
		}
	}

	/**
	 * Create random String
	 *
	 * @param value number of digits
	 */
	public static String randomString(int value) {
		return RandomStringUtils.randomAlphabetic(value);
	}

	public static String getEnvironments() {
		return environments;
	}

	public static void setEnvironments(String environments) {
		HelperMethods.environments = environments;
	}

	/**
	 * Create random alfa Numeric Code
	 *
	 * @param length is the length of the code to want to arrive.
	 */
	public static String randomAlfaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
	}

	/**
	 * This function is used to configure the ChromeOptions object used to create a ChromeDriver object
	 *
	 * @return A ChromeOptions object
	 */
	public static ChromeOptions chromeOptionsConfig() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");
		options.addArguments("window-size=1980,1080");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-setuid-sandbox");
		return options;
	}
	/**
	 * This getPassword method gets the map of environment variables defined in the system
	 *
	 * @param key it's the key of the password
	 * @return the value of the password defined on the environment variables
	 */
	public static String getEnvironmentVariable(String key) {
		return System.getenv().get(key);
	}
	/**
	 * A function that returns the selected browser.
	 *
	 * @return the selected browser
	 */
	public static String browserSelected() {
		String browser = "BROWSER";
		setBrowsers(System.getenv(browser));
		String chrome = "CHROME";
		setBrowsers(getBrowsers() != null ? getBrowsers() : chrome);
		return getBrowsers();
	}
	public static void setBrowsers(String browsers) {
		HelperMethods.browsers = browsers;
	}

	/**
	 * This getPostResponse method triggers the post-request
	 *
	 * @param payload it's the payload related with the update goal
	 * @return
	 */
	public Response getPostResponse(Object payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String payloadFormatted = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
			return requestFactory.makeRequest().body("[\n" + payloadFormatted + "\n]")
							.post(DataConstantQueries.PATH_FOR_MEMBER);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	public boolean elementExistWaitLongTime(WebElement element) {
		try {
			waitForElementPresentLong(element);
			return element.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void waitForElementPresentLong(WebElement element) {
		waitForElementPresent(element, Constant.LONGEST_TIMEOUT, this.driver);
	}

	protected void waitForElementPresent(WebElement element, int time, WebDriver optionalDriver) {
		WebDriverWait wait = new WebDriverWait(optionalDriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for an element to be present, clear the field and complete the field with the value
	 *
	 * @param element to send the value
	 * @param value   to send to the value
	 */
	public void sendKeysInElement(WebElement element, String value) {
		waitForElementPresentLong(element);
		element.clear();
		element.sendKeys(Keys.chord(Keys.DELETE));
		element.sendKeys(value);
	}

	public void waitForElements(int value) {
		LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(value * 500L));
	}

	/**
	 * Wait for an element to be present and then click the element
	 *
	 * @param element to click on
	 */
	public void clickWithActions(WebElement element) {

		if (environments.equalsIgnoreCase(localEnvironment)) {
			waitBlockUIDisappear();
		}
		waitForElementPresentLong(element);
		Actions action = new Actions(driver);
		waitForElementClickableLong(element);
		action.moveToElement(element).click(element).perform();
	}

	public void waitBlockUIDisappear() {
		int waited = 0;
		while (isBlockUIPresent() && waited < 200) {
			waitForElements(1);
			waited++;
		}
	}

	public void waitForElementClickableLong(WebElement element) {
		if (environments.equalsIgnoreCase(localEnvironment)) {
			waitBlockUIDisappear();
		}
		waitForElementPresentLong(element);
		waitForElementClickable(element, Constant.LONG_TIMEOUT, driver);
	}


	public boolean isBlockUIPresent() {
		try {
			List<WebElement> blockUIs = driver.findElements(By.xpath(BLOCK_UI_XPATH));
			return blockUIs.size() > 0;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	protected void waitForElementClickable(WebElement element, int time, WebDriver optionalDriver) {
		if (environments.equalsIgnoreCase(localEnvironment)) {
			waitBlockUIDisappear();
		}
		WebDriverWait wait = new WebDriverWait(optionalDriver, Duration.ofSeconds(time));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public boolean isElementEnabled(WebElement element) {
		try {
			waitForElementPresentLong(element);
			return element.isEnabled();
		} catch (StaleElementReferenceException ex) {
			return false;
		}
	}

	/**
	 * taking a screenshot
	 *
	 * @param name name of screenshot where will be saved in the screenshot folder.
	 */
	public void takePicture(String name, WebDriver driver) {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File("./Screenshots/" + name + ".png"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Create a Current Date
	 * return the current Date
	 */
	public static String currentDateFull() {
		return String.valueOf(java.time.Clock.systemUTC().instant());
	}

	/**
	 * this isElementPresent method search to find if the element exists
	 *
	 * @param element it's the goal element
	 * @return true or false because is tried with NoSuchElementException
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			return element.isEnabled();
		} catch (NoSuchElementException ex) {
			return false;
		}
	}
}
