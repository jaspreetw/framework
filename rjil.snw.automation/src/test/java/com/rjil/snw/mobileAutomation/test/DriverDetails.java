package com.rjil.snw.mobileAutomation.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public enum DriverDetails {
	Andriod {
		@Override
		RemoteWebDriver getDriverDetails(String udid, String portNo, String AppPackage, String AppActivity) throws MalformedURLException {
			DesiredCapabilities caps = new DesiredCapabilities();
			RemoteWebDriver driver;
			caps.setCapability("platformName", "android");
			caps.setCapability("deviceName", "Micromax");
			caps.setCapability("appPackage", AppPackage);
			caps.setCapability("appActivity", AppActivity);
			caps.setCapability("udid", udid);
			driver = new AndroidDriver(new URL("http://127.0.0.1:"+portNo+"/wd/hub"), caps);
			return driver;
		}
	},
	IOS {
		@Override
		RemoteWebDriver getDriverDetails(String udid, String portNo, String AppPackage, String AppActivity) throws MalformedURLException {
			DesiredCapabilities caps = new DesiredCapabilities();
			RemoteWebDriver driver;
			caps.setCapability("platformName", "iOS");
			caps.setCapability("platformVersion", "8.2");
			caps.setCapability("deviceName", "iPhone 6");
			caps.setCapability("app", "/Users/Jazz/Downloads/Tej.app");
			caps.setCapability("UUID", "9b6000c0ff47146f19f4b18ccbb7ea72e81fa964");
			driver = new IOSDriver(new URL("http://0.0.0.0:"+portNo+"/wd/hub"), caps);
			return driver;
		}
	},;
	
	abstract RemoteWebDriver getDriverDetails(String udid, String portNo, String AppPackage, String AppActivity) throws MalformedURLException;
}
