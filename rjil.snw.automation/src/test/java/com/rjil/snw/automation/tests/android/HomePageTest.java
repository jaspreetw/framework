package com.rjil.snw.automation.tests.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.rjil.snw.automation.AdbCommandGenerator;
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
	public void getDetails(String udid) {
		String expected = AdbCommandGenerator.getDeviceName(udid);
		String result = homePage.getDeviceName();
		Assert.assertTrue(result.contains(expected));
	}

}
