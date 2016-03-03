package com.rjil.snw.mobileAutomation.pageobjects.android;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rjil.snw.mobileAutomation.AdbResponse;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HeavyDataTransferPage {
	private RemoteWebDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='2']")
	private MobileElement timeEstimate;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/startButton")
	private MobileElement sendData;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/progressPerCent")
	private MobileElement progressPercent;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/okButton")
	private MobileElement okButton;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='CANCEL']")
	private MobileElement cancelTransfer;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/cancelButton")
	private MobileElement confirmCancel;

	public HeavyDataTransferPage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public Long getEstimatedTime() {
		String time = this.timeEstimate.getText();
		String[] lines = time.split("\\n");
		time = lines[1];
		int hours = Integer.parseInt(time.substring(0, 2));
		int minutes = Integer.parseInt(time.substring(3, 5));
		Long estimatedTime = (long) ((hours * 60 + minutes) * 60000);
		return estimatedTime;
	}

	public Long getActualDataTransferTime() {
		this.sendData.click();
		Long startTime = System.currentTimeMillis();

		String perc = this.progressPercent.getText();
		while (!perc.equals("100%")) {
			System.out.println("Percentage of transfer =" + perc);
			perc = this.progressPercent.getText();
		}

		Long endTime = System.currentTimeMillis();
		return (long) (endTime - startTime);
	}

	public String getPercentageTransferred() {
		return this.progressPercent.getText();
	}

	public void transferData() {
		String perc = this.progressPercent.getText();
		while (!perc.equals("100%")) {
			perc = this.progressPercent.getText();
		}
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

	public boolean isSenderWifiInterrupted(String udid) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.sendData));
		this.sendData.click();
		AdbResponse.stopWifi(udid);
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.visibilityOf(this.okButton));
		boolean response = this.okButton.isDisplayed();
		this.okButton.click();
		return response;
	}

	public boolean isReceiverWifiInterrupted() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(this.okButton));
		boolean response = this.okButton.isDisplayed();
		this.okButton.click();
		return response;
	}

	public void cancelDataTransfer() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.sendData));
		this.sendData.click();
		this.cancelTransfer.click();
		this.confirmCancel.click();
	}
}
