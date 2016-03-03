package com.rjil.snw.mobileAutomation.pageobjects.android;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LightDataTransferPage {
	private RemoteWebDriver driver;

	@AndroidFindBy(xpath = "//android.widget.ListView/android.widget.LinearLayout[@index='2']/android.widget.FrameLayout[@index='0']")
	private MobileElement callLogs;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/startButton")
	private MobileElement sendData;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/progressPerCent")
	private MobileElement progressPercent;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='CONTINUE']")
	private MobileElement continueButton;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/skipButton")
	private MobileElement skipButton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='YES']")
	private MobileElement resume;

	public LightDataTransferPage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public boolean unselectCallLogs() {
		this.callLogs.click();
		return (this.callLogs.isSelected());
	}

	public void startDataTransfer() {
		this.sendData.click();
		String perc = this.progressPercent.getText();
		while (!perc.equals("100%")) {
			System.out.println("Percentage of transfer =" + perc);
			perc = this.progressPercent.getText();
		}
	}

	public String getPercentageTransferred() {
		return this.progressPercent.getText();
	}

	public void continueToHeavyDataTransfer() {
		this.continueButton.click();
	}

	public void skipToHeavyDataTransfer() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.skipButton));
		this.skipButton.click();
	}
	
	public HashMap<String, String> createList() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.sendData));
		HashMap<String, String> hashmap = new HashMap<String, String>();
		for (int i = 0; i < 5; i++) {
			String str1 = driver.findElement(By.xpath("//android.widget.ListView/android.widget.LinearLayout[@index='"
					+ i + "']/android.widget.TextView[@index='1']")).getText();
			String str2 = driver.findElement(By.xpath("//android.widget.ListView/android.widget.LinearLayout[@index='"
					+ i + "']/android.widget.TextView[@index='2']")).getText();
			hashmap.put(str1, str2);
		}
		return hashmap;
	}
	
	public void resumeDataTransfer() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.resume));
		this.resume.click();
	}

}
