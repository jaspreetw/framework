package com.rjil.snw.mobileAutomation.pageobjects.android;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ReportPage {
private RemoteWebDriver driver;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='CONTINUE']")
	private MobileElement continueButton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='TRY AGAIN']")
	private MobileElement tryAgain;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='EXIT']")
	private MobileElement exitButton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='YES']")
	private MobileElement confirmYes;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/message")
	private MobileElement message;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/progressBar")
	private MobileElement progressBar;
	
	public ReportPage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public boolean isDispalyed() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.continueButton));
		return this.continueButton.isDisplayed();
	}
	
	public void clickContinue() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(this.continueButton));
		this.continueButton.click();
	}
	
	public boolean summaryReport() {
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.stalenessOf(this.progressBar));
		String str = this.message.getText();
		if(str.equals("Summary report sent successfully")) {
			result = true;
		} else {
			this.tryAgain.click();
		}
		return result;
	}
	
	public void clickExit() {
		this.exitButton.click();
	}
	
	public void clickYes() {
		this.confirmYes.click();
	}
	
}
