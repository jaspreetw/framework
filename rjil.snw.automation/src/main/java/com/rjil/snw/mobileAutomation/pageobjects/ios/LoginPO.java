package com.rjil.snw.mobileAutomation.pageobjects.ios;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;;

public class LoginPO {

	private RemoteWebDriver driver;
	
	@iOSFindBy(name="email_textfield")
	private MobileElement  emailAddress;
	
	@iOSFindBy(id="Next")
	private MobileElement next;
	
	@iOSFindBy(id="Done")
    private MobileElement done; 
	
	@iOSFindBy(name="login_btn")
	private MobileElement loginButton;
	
	public LoginPO(RemoteWebDriver remoteWebDriver)
	{
		driver = (AppiumDriver) remoteWebDriver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

	}

	
	public void enterLoginDetails(String emailAddress, String password) throws InterruptedException
	{
		this.emailAddress.tap(1, 10);
		this.emailAddress.clear();
		driver.executeScript("target.frontMostApp().keyboard().typeString('" + "walia.jaspreet@gmail.com" + "')");
		next.tap(1, 2);
		driver.executeScript("target.frontMostApp().keyboard().typeString('" + "sdsadsa" + "')");
        done.tap(1, 2);
        loginButton.tap(1, 3);

        synchronized (driver)
		{
		    driver.wait();
		}

	}
	

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}