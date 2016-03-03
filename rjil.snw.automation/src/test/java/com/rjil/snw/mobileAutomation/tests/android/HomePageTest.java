package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.AdbResponse;
import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.UtilityClass;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class HomePageTest {

	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();;
	HomePage homePage = null;
	String udid = null;

	public HomePageTest() throws MalformedURLException {
		super();
		udid = properties.getKeyValues("SenderUdid");
		String portNo = properties.getKeyValues("SenderPortNo");
		String appPackage = properties.getKeyValues("SnwPackage");
		String appActivity = properties.getKeyValues("SnwActivity");
		device.initialiseDriver("android", udid, portNo, appPackage, appActivity);
		
	}

	@BeforeClass
	public void setup() {
		homePage = new HomePage(device.driver);
		//receiverHomePage = new HomePage(receiver.driver);
	}

	@Test
	public void testDeviceName() {
		String expected = AdbResponse.getDeviceName(udid);
		String result = homePage.getDeviceName();
		Assert.assertTrue(result.contains(expected));
	}
	
	@Test(dependsOnMethods = { "testDeviceName" })
	public void testNetworkName() {
		String expected = AdbResponse.getNetworkName(udid);
		String result = homePage.getNetworkName();
		Assert.assertTrue(result.contains(expected));
	}

	@Test(dependsOnMethods = { "testNetworkName" })
	public void testVersionNumber() {
		String expected = UtilityClass.getVersionName();
		String result = homePage.getVersionNumber();
		Assert.assertTrue(result.contains(expected));
	}
	
	@Test(dependsOnMethods = { "testVersionNumber" })
	public void testIsTandCpageDisplayed() {
		boolean response = homePage.gotoTandCpage();
		Assert.assertTrue(response);
	}
	
	@AfterTest
	public void endTest() {
		device.releaseDriver();
	}
}
