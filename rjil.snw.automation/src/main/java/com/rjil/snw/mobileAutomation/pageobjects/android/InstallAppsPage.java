package com.rjil.snw.mobileAutomation.pageobjects.android;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class InstallAppsPage {
	private RemoteWebDriver driver;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/genderFemale")
	private MobileElement gender;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/ageGroup2")
	private MobileElement age;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/continueButton")
	private MobileElement continueButton;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/installButton")
	private MobileElement install;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/installRecommendedAppsButton")
	private MobileElement installRecommendedApps;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Bill Bachao']")
	private MobileElement lastAppPage;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/webView")
	private MobileElement wifiNotConnected;
	
	public InstallAppsPage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public boolean wifiNotConnectedDialogDisplayed() {
		return this.wifiNotConnected.isDisplayed();
	}
	
	public boolean downloadApps() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.installRecommendedApps));
		this.installRecommendedApps.click();
		this.gender.click();
		this.age.click();
		this.continueButton.click();
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.visibilityOf(this.install));
		this.install.click();
		WebDriverWait wait2 = new WebDriverWait(driver, 100);
		wait2.until(ExpectedConditions.visibilityOf(this.lastAppPage));
		return this.lastAppPage.isDisplayed();
	}
	
}
