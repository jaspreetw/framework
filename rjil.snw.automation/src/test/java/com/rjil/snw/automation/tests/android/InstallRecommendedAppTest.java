package com.rjil.snw.automation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.automation.AdbResponse;
import com.rjil.snw.automation.CommonFunctions;
import com.rjil.snw.automation.PropertyFileReader;
import com.rjil.snw.automation.pageobjects.android.HomePage;
import com.rjil.snw.automation.pageobjects.android.InstallAppsPage;
import com.rjil.snw.automation.test.BaseTest;

public class InstallRecommendedAppTest {

	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();
	InstallAppsPage installAppsPage = null;
	String udid = null;

	public InstallRecommendedAppTest() throws MalformedURLException {
		super();
		udid = properties.getKeyValues("ReceiverUdid");
		//AdbResponse.uninstallSnwApp(udid);
		//AdbResponse.cleanPhone(udid);
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
		Assert.assertEquals(appCount, CommonFunctions.getAppCount());
	}

}