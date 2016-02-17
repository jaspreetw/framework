package com.rjil.snw.automation.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.ios.IOSDriver;

public class BaseTest {
	
	public static RemoteWebDriver driver;

	// Application setup methods..
	/**
	 * @param product this contains name of the product
	 * @param browser this is type of the browser
	 * @return WebDriver
	 * @throws MalformedURLException
	 */
	public  BaseTest(String device) throws MalformedURLException  {
		if (device.equalsIgnoreCase("IOS"))
		{
			driver = DriverDetails.IOS.doSomething();
		}
		else
		{
			driver = DriverDetails.Andriod.doSomething();
		}

	}
	
	
	
}
