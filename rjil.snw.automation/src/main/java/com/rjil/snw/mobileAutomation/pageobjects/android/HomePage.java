package com.rjil.snw.mobileAutomation.pageobjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	private RemoteWebDriver driver;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/deviceNetworkInfo")
	private MobileElement deviceInfo;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/appInfo")
	private MobileElement versionInfo;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/tandcLink")
	private MobileElement linkToTandCpage;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/connectAsSenderButton")
	private MobileElement send;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/connectAsReceiverButton")
	private MobileElement receive;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/connectToAndroidPeerButton")
	private MobileElement androidPeer;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/instruction")
	private MobileElement wifiName;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/continueButton")
	private MobileElement continueButton;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/webView")
	private MobileElement termsAndConditionsPage;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/installButton")
	private MobileElement install;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/installOldAppsButton")
	private MobileElement installOldPhoneApps;
	
	public HomePage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public boolean isDispalyed() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.deviceInfo));
		return this.deviceInfo.isDisplayed();
	}
	
	public String getDeviceName() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.deviceInfo));
		String result = this.deviceInfo.getText();	
		return result;
	}
	
	
	public String getNetworkName() {
		String result = this.deviceInfo.getText();	
		return result;
	}
	
	
	public String getVersionNumber() {
		String result = this.versionInfo.getText();
		return result;
	}
	
	public boolean installEnabled() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.deviceInfo));
		this.installOldPhoneApps.click();
		return this.install.isEnabled();
	}
	
	public boolean installDisabled() {
		this.installOldPhoneApps.click();
		return !(this.install.isEnabled());
	}
	
	public boolean gotoTandCpage() {
		this.linkToTandCpage.click(); 
		return this.termsAndConditionsPage.isDisplayed();
	}
	
	public String getWifiName() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(this.receive));
		this.receive.click();
		this.androidPeer.click();
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.elementToBeClickable(this.continueButton));
		String hotspotText = this.wifiName.getText();
		String hotspotId = hotspotText.substring(hotspotText.lastIndexOf("SNW"));
		this.continueButton.click();
		return hotspotId;
	}

	
	public void setWifiName(String wifiName) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(this.continueButton));
		driver.findElement(By.xpath("//android.widget.TextView[@text='" + wifiName + "']")).click();
		this.continueButton.click();
	}
	
	public void getWifiPage() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(this.send));
		this.send.click();
		this.androidPeer.click();
	}
	
	public boolean isWifiNamePresent(String wifiName) {
		return driver.findElement(By.xpath("//android.widget.TextView[@text='" + wifiName + "']")).isDisplayed();
	}
}
