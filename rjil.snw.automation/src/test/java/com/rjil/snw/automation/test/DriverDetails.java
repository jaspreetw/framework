package com.rjil.snw.automation.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.ios.IOSDriver;

public enum DriverDetails {
		   Andriod {
		      @Override
		      RemoteWebDriver doSomething() throws MalformedURLException {
		    	  DesiredCapabilities caps = new DesiredCapabilities();
		    	  RemoteWebDriver driver;
				  caps.setCapability("platformName", "iOS");
				  caps.setCapability("platformVersion", "8.2"); 
				  caps.setCapability("deviceName", "iPhone 6");
				  caps.setCapability("app", "/Users/Jazz/Downloads/SwitchNWalk_Sprint7_2"); 
				  caps.setCapability("UUID", "9b6000c0ff47146f19f4b18ccbb7ea72e81fa964");
				  driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
				  return driver;
		      }
		   },
		   IOS {
		      @Override
		      RemoteWebDriver doSomething() throws MalformedURLException {
		    	  DesiredCapabilities caps = new DesiredCapabilities();
		    	  RemoteWebDriver driver;
				  caps.setCapability("platformName", "iOS");
				  caps.setCapability("platformVersion", "8.2"); 
				  caps.setCapability("deviceName", "iPhone 6");
				  caps.setCapability("app", "/Users/Jazz/Downloads/Tej.app"); 
				  caps.setCapability("UUID", "9b6000c0ff47146f19f4b18ccbb7ea72e81fa964");
				  driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
				  return driver;
		      }
		   },
		   Web {
			      @Override
			      RemoteWebDriver doSomething() throws MalformedURLException {
			    	  DesiredCapabilities caps = new DesiredCapabilities();
			    	  RemoteWebDriver driver;
					  caps.setCapability("platformName", "iOS");
					  caps.setCapability("platformVersion", "8.2"); 
					  caps.setCapability("deviceName", "iPhone 6");
					  caps.setCapability("app", "/Users/Jazz/Downloads/SwitchNWalk_Sprint7_2.7.0_QA.ipap"); 
					  caps.setCapability("UUID", "9b6000c0ff47146f19f4b18ccbb7ea72e81fa964");
					  driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
					  return driver;
			      }
			   },
		   ;
		   abstract RemoteWebDriver doSomething() throws MalformedURLException;
		}

