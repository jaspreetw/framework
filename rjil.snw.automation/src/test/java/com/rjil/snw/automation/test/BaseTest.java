package com.rjil.snw.automation.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.ios.IOSDriver;

public class BaseTest {

	public RemoteWebDriver driver;

	public void initialiseDriver(String device, String udid, String portNo) {
		try {
			if (device.equalsIgnoreCase("IOS")) {
				driver = DriverDetails.IOS.getDriverDetails(udid, portNo);
			} else if (device.equalsIgnoreCase("Android")) {
				driver = DriverDetails.Andriod.getDriverDetails(udid, portNo);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void releaseDriver() {
		driver.quit();
	}

}
