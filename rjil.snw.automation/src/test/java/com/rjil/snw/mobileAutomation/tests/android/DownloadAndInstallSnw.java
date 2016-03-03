package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.AdbResponse;
import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.pageobjects.android.DownloadSnwPage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class DownloadAndInstallSnw {

	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();
	String udid = null;

	public DownloadAndInstallSnw() throws MalformedURLException {
		super();
		udid = properties.getKeyValues("ReceiverUdid");
		AdbResponse.uninstallSnwApp(udid);
		AdbResponse.cleanPhone(udid);
		String portNo = properties.getKeyValues("ReceiverPortNo");
		String appPackage = properties.getKeyValues("ChromePackage");
		String appActivity = properties.getKeyValues("ChromeActivity");
		device.initialiseDriver("android", udid, portNo, appPackage, appActivity);
		System.out.println("initialized");
	}

	@BeforeClass
	public void connectPhoneToBoxWifi() {
		AdbResponse.connectToBoxWifi(udid);
	}

	@Test
	public void downloadTest() {
		DownloadSnwPage downloadPage = new DownloadSnwPage(device.driver);
		downloadPage.downloadSnwApp();
	}

	@Test(dependsOnMethods = { "downloadTest" })
	public void pullSnwTest() {
		try {
			Thread.sleep(10000);
			String result = AdbResponse.pullSnw(udid);
			Assert.assertNull(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "pullSnwTest" })
	public void installTest() {
		String result = AdbResponse.installSnw(udid);
		Assert.assertEquals(result, "Success");
	}

	@AfterTest
	public void endTest() {
		device.releaseDriver();
	}

}
