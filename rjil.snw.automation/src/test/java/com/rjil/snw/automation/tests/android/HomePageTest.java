package com.rjil.snw.automation.tests.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.rjil.snw.automation.AdbResponse;
import com.rjil.snw.automation.CommandRunner;
import com.rjil.snw.automation.pageobjects.android.HomePage;
import com.rjil.snw.automation.test.BaseTest;

public class HomePageTest extends BaseTest {

	HomePage homePage = null;

	public HomePageTest() throws MalformedURLException {
		super();
		initialiseDriver("Android");
	}

	@BeforeClass
	public void setup() {
		homePage = new HomePage(driver);
	}

	@Parameters({ "udid" })
	@Test
	public void testDeviceName(String udid) {
		String expected = AdbResponse.getDeviceName(udid);
		String result = homePage.getDeviceName();
		Assert.assertTrue(result.contains(expected));
	}
	
	@Parameters({ "udid" })
	@Test
	public void testNetworkName(String udid) {
		String expected = AdbResponse.getNetworkName(udid);
		String result = homePage.getNetworkName();
		Assert.assertTrue(result.contains(expected));
	}

	@Parameters({ "udid" })
	@Test
	public void testVersionNumber(String udid) {
		String expected = AdbResponse.getVersionNumber();
		String result = homePage.getVersionNumber();
		Assert.assertTrue(result.contains(expected));
	}
	
	@AfterTest
	public void endTest() {
		releaseDriver();
	}
}
