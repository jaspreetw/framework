package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.AdbResponse;
import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.UtilityClass;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.pageobjects.android.InstallAppsPage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class InstallRecommendedAppTest {

	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();
	InstallAppsPage installAppsPage = null;
	String udid = null;

	public InstallRecommendedAppTest() throws MalformedURLException {
		super();
		udid = properties.getKeyValues("ReceiverUdid");
		String portNo = properties.getKeyValues("ReceiverPortNo");
		String appPackage = properties.getKeyValues("SnwPackage");
		String appActivity = properties.getKeyValues("SnwActivity");
		device.initialiseDriver("android", udid, portNo, appPackage, appActivity);
		System.out.println("initialized");
	}
	
	@BeforeClass
	public void setup() {
		installAppsPage = new InstallAppsPage(device.driver);
	}
	
	@Test
	public void downloadApps() {
		boolean result = installAppsPage.downloadApps();
		Assert.assertTrue(result);
	}
	
	@Test
	public void installApps() {
		int appCount = AdbResponse.installApps(udid);
		Assert.assertEquals(appCount, UtilityClass.getBoxAppCount());
	}

}