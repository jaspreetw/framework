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

public class PicturePage {
	private RemoteWebDriver driver;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/largeSizeImage")
	private MobileElement imageName;
	
	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/instruction")
	private MobileElement picturePage;
	
	public PicturePage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public String getImageName() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(this.imageName));
		String imageName = this.imageName.getAttribute("name");
		return imageName;
	}
	
	public void setImage(String imageName) {
		driver.findElement(By.name(imageName)).click();
	}
	
	public boolean isDisplayed() {
		return this.picturePage.isDisplayed();
	}

}
