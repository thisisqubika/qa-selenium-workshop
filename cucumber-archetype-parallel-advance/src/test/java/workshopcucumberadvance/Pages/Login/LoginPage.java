package workshopcucumberadvance.Pages.Login;

import workshopcucumberadvance.Pages.BasePage;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.HelperMethods;
import utils.ScreenshotUtils;
import utils.constants.Constant;
import utils.constants.DataConstant;
import utils.enums.UserRole;

public class LoginPage extends BasePage {
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'card-body')]")
	private WebElement LOGIN_PAGE;
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='email']")
	private WebElement EMAIL_FIELD;
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='password']")
	private WebElement PASSWORD_FIELD;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'btn-primary')][text()='Autenticar']")
	private WebElement LOGIN_BUTTON;
	@FindBy(how = How.XPATH, using = "//*[@id='toast-container']")
	private WebElement TOAST_MESSAGE;
	HelperMethods helperMethods;

	public LoginPage(WebDriver driver, Scenario scenario) {
		super(driver, scenario);
		helperMethods = new HelperMethods(driver);
	}

	public boolean isLoginPageDisplayed() {
		return helperMethods.isElementExistWaitLongTime(LOGIN_PAGE);
	}

	public void completeEmail(UserRole role) {
		switch (role) {
			case superAdmin -> helperMethods.sendKeysInElement(EMAIL_FIELD, DataConstant.EMAIL_SUPER_ADMIN);
			case normalAccount -> helperMethods.sendKeysInElement(EMAIL_FIELD, DataConstant.EMAIL_ACCOUNT_ADMIN);
		}
	}

	public void completeEmail(String role) {
		helperMethods.sendKeysInElement(EMAIL_FIELD, role);
		ScreenshotUtils.saveScreenshot(helperMethods.getDriver());
	}

	public void completePassword(UserRole role) {
		switch (role) {
			case superAdmin -> helperMethods.sendKeysInElement(PASSWORD_FIELD, DataConstant.PASSWORD_SUPER_ADMIN);
			case normalAccount -> helperMethods.sendKeysInElement(PASSWORD_FIELD, DataConstant.PASSWORD);
		}
	}

	public void completePassword(String password) {
		helperMethods.sendKeysInElement(PASSWORD_FIELD, password);
	}

	public void clickLoginBtn() {
		helperMethods.waitForElements(Constant.SHORT_TIMEOUT);
		helperMethods.waitForElementPresentLong(LOGIN_BUTTON);
		helperMethods.clickWithActions(LOGIN_BUTTON);
	}

	public boolean loginButtonVisibility() {
		helperMethods.waitForElements(Constant.SHORT_TIMEOUT);
		helperMethods.waitForElementPresentLong(LOGIN_BUTTON);
		return helperMethods.isElementEnabled(LOGIN_BUTTON);
	}

	public boolean isToastMessageDisplayed() {
		helperMethods.waitForElementPresentLong(TOAST_MESSAGE);
		return helperMethods.isElementEnabled(TOAST_MESSAGE);
	}
}
