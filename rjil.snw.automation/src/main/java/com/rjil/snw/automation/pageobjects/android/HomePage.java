package com.rjil.snw.automation.pageobjects.android;

import java.io.BufferedReader;
import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	private RemoteWebDriver driver;

	@AndroidFindBy(id = "com.reliance.jio.jioswitch:id/deviceNetworkInfo")
	private MobileElement deviceInfo;

	public HomePage(RemoteWebDriver remoteWebDriver) {
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public String getDeviceName() {
		String result = this.deviceInfo.getText();
		
		return result;
	}
}
