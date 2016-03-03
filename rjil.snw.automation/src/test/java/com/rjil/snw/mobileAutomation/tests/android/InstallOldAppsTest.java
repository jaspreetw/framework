package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.AdbResponse;
import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class InstallOldAppsTest {
	static PropertyFileReader properties = new PropertyFileReader();
	BaseTest device = new BaseTest();
	HomePage homePage = null;
	String senderUdid = null;
	String receiverUdid = null;

	public InstallOldAppsTest() throws MalformedURLException {
		super();
		senderUdid = properties.getKeyValues("SenderUdid");
		receiverUdid = properties.getKeyValues("ReceiverUdid");
		String portNo = properties.getKeyValues("ReceiverPortNo");
		String appPackage = properties.getKeyValues("SnwPackage");
		String appActivity = properties.getKeyValues("SnwActivity");
		device.initialiseDriver("android", receiverUdid, portNo, appPackage, appActivity);
		System.out.println("initialized");
	}
	
	@Test
	public void installAppsFromOldPhone() {
		homePage = new HomePage(device.driver);
		Assert.assertTrue(homePage.installEnabled());
		AdbResponse.createLoactionFile(senderUdid);
		AdbResponse.installOldPhoneApps(senderUdid, receiverUdid);
		Assert.assertTrue(homePage.installDisabled());
	}
}
