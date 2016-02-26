package com.rjil.snw.automation.pageobjects.android;

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
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.continueButton));
		this.continueButton.click();
	}
	
	public void clickTryAgain() {
		this.tryAgain.click();
	}
	
	public void clickExit() {
		this.exitButton.click();
	}
	
	public void clickYes() {
		this.confirmYes.click();
	}
	
}
