package com.rjil.snw.automation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.automation.AdbResponse;
import com.rjil.snw.automation.PropertyFileReader;
import com.rjil.snw.automation.pageobjects.android.HomePage;
import com.rjil.snw.automation.test.BaseTest;

public class HomePageTest {

	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();;
	HomePage homePage = null;
	String udid = null;

	public HomePageTest() throws MalformedURLException {
		super();
		udid = properties.getKeyValues("SenderUdid");
		String portNo = properties.getKeyValues("SenderPortNo");
		device.initialiseDriver("android", udid, portNo);
		
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
	
	@Test
	public void testNetworkName() {
		String expected = AdbResponse.getNetworkName(udid);
		String result = homePage.getNetworkName();
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void testVersionNumber() {
		String expected = AdbResponse.getVersionNumber();
		String result = homePage.getVersionNumber();
		Assert.assertTrue(result.contains(expected));
	}
	
	@Test
	public void testIsTandCpageDisplayed() {
		boolean response = homePage.gotoTandCpage();
		Assert.assertTrue(response);
	}
	
	@AfterTest
	public void endTest() {
		device.releaseDriver();
	}
}
