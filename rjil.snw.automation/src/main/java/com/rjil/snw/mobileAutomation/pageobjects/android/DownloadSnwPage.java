package com.rjil.snw.mobileAutomation.pageobjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class DownloadSnwPage {
	private RemoteWebDriver driver;
	
	@FindBy(xpath = "//button[text()='Download switchNwalk app']")
	private WebElement downloadSnw;
	
	@FindBy(name = "OK")
	private WebElement OKButton;
	
	public DownloadSnwPage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void downloadSnwApp() {
		((AndroidDriver) driver).context("WEBVIEW_1");
		driver.get("http://google.com");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.downloadSnw));
		driver.findElement(By.xpath("//button[text()='Download switchNwalk app']")).click();
		this.downloadSnw.click();
		((AndroidDriver) driver).context("NATIVE_APP");
		this.OKButton.click();
	}
}
