package com.rjil.snw.automation.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.ios.IOSDriver;

public class BaseTest {
	
	public static RemoteWebDriver driver;

	public void initialiseDriver(String device) throws MalformedURLException {
		if(device.equalsIgnoreCase("IOS")){
			driver = DriverDetails.IOS.getDriverDetails();
		}
		else if(device.equalsIgnoreCase("Android")) {
			driver = DriverDetails.Andriod.getDriverDetails();
		}
	}
	
	public void releaseDriver() {
		driver.quit();
	}
	
	
	
}
