package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.pageobjects.android.HeavyDataTransferPage;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.pageobjects.android.LightDataTransferPage;
import com.rjil.snw.mobileAutomation.pageobjects.android.PicturePage;
import com.rjil.snw.mobileAutomation.pageobjects.android.ReportPage;
import com.rjil.snw.mobileAutomation.test.BaseTest;


public class DataTransferTest {
	static PropertyFileReader properties = new PropertyFileReader();
	
	BaseTest sender = new BaseTest();
	BaseTest receiver = new BaseTest();
	
	HomePage senderHomePage = null;
	HomePage receiverHomePage = null;
	
	PicturePage senderPicturePage = null;
	PicturePage receiverPicturePage = null;
	
	LightDataTransferPage senderLightDataPage = null;
	LightDataTransferPage receiverLightDataPage = null;
	
	HeavyDataTransferPage senderHeavyDataPage = null;
	HeavyDataTransferPage receiverHeavyDataPage = null;
	
	ReportPage senderReportPage = null;
	ReportPage receiverReportPage = null;
	
	String senderUdid = null;
	String receiverUdid = null;
	
	HashMap<String, String> hashmap = null;
	
	public DataTransferTest() throws MalformedURLException {
		senderUdid = properties.getKeyValues("SenderUdid");
		receiverUdid = properties.getKeyValues("ReceiverUdid");
		String senderPortNo = properties.getKeyValues("SenderPortNo");
		String receiverPortNo = properties.getKeyValues("ReceiverPortNo");
		String appPackage = properties.getKeyValues("SnwPackage");
		String appActivity = properties.getKeyValues("SnwActivity");
		sender.initialiseDriver("android", senderUdid, senderPortNo, appPackage, appActivity);
		receiver.initialiseDriver("android", receiverUdid, receiverPortNo, appPackage, appActivity);
	}
	
	@BeforeClass
	public void setup() {
		senderHomePage = new HomePage(sender.driver);
		receiverHomePage = new HomePage(receiver.driver);
		
		String wifiName = receiverHomePage.getWifiName();
		senderHomePage.getWifiPage();
		senderHomePage.setWifiName(wifiName);
	}
	
	@Test
	public void picturePairingPageTest() {
		receiverPicturePage = new PicturePage(receiver.driver);
		String imageName = receiverPicturePage.getImageName();
		senderPicturePage = new PicturePage(sender.driver);
		Assert.assertTrue(senderPicturePage.isDisplayed());
		senderPicturePage.setImage(imageName);
	}
	
	@Test(dependsOnMethods = { "picturePairingPageTest" })
	public void remindersNotSupportedTest() {
		senderLightDataPage = new LightDataTransferPage(sender.driver);
		receiverLightDataPage = new LightDataTransferPage(receiver.driver);
		hashmap = senderLightDataPage.createList();
		Assert.assertEquals(hashmap.get("Reminders"), "Not Supported");
	}
	
	@Test(dependsOnMethods = { "remindersNotSupportedTest" })
	public void lightDataCountTest() {
		Assert.assertEquals(hashmap.get("Contacts"), properties.getKeyValues("Contacts"));
		Assert.assertEquals(hashmap.get("Call Logs"), properties.getKeyValues("CallLogs"));
		Assert.assertEquals(hashmap.get("Calendar"), properties.getKeyValues("Calendar"));
		Assert.assertEquals(hashmap.get("Messages"), properties.getKeyValues("Messages"));
	}
	
	@Test(dependsOnMethods = { "lightDataCountTest" })
	public void selectiveDataTransferTest() {
		Assert.assertFalse(senderLightDataPage.unselectCallLogs());
	}
	
	@Test(dependsOnMethods = { "selectiveDataTransferTest" })
	public void lightDataTransferTest() {
		senderLightDataPage.startDataTransfer();
		Assert.assertEquals(receiverLightDataPage.getPercentageTransferred(), senderLightDataPage.getPercentageTransferred());
	}
	
	@Test(dependsOnMethods = { "lightDataTransferTest" })
	public void documentsNotSupportedTest() {
		senderLightDataPage.continueToHeavyDataTransfer();
		senderHeavyDataPage = new HeavyDataTransferPage(sender.driver);
		receiverHeavyDataPage = new HeavyDataTransferPage(receiver.driver);
		hashmap = senderHeavyDataPage.createList();
		Assert.assertEquals(hashmap.get("Documents"), "Not Supported");
	}
	
	@Test(dependsOnMethods = { "documentsNotSupportedTest" })
	public void heavyDataCountTest() {
		Assert.assertEquals(hashmap.get("Photos"), properties.getKeyValues("Photos"));
		Assert.assertEquals(hashmap.get("Music"), properties.getKeyValues("Music"));
		Assert.assertEquals(hashmap.get("Videos"), properties.getKeyValues("Videos"));
		Assert.assertEquals(hashmap.get("Applications"), properties.getKeyValues("Applications"));
	}
	
	@Test(dependsOnMethods = { "heavyDataCountTest" })
	public void heavyDataTimeEstimateTest() {
		Long estimatedTime = senderHeavyDataPage.getEstimatedTime();
		Long actualTime = senderHeavyDataPage.getActualDataTransferTime();
		System.out.println(estimatedTime + " " + actualTime);
		if (estimatedTime - 60000 > actualTime || estimatedTime + 60000 < actualTime) {
			Assert.assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods = { "heavyDataTimeEstimateTest" })
	public void heavyDataTransferTest() {
		Assert.assertEquals(receiverHeavyDataPage.getPercentageTransferred(), senderHeavyDataPage.getPercentageTransferred());
	}
	
	@Test(dependsOnMethods = { "heavyDataTransferTest" })
	public void reportSentTest() {
		receiverReportPage = new ReportPage(receiver.driver);
		boolean flag = receiverReportPage.summaryReport();
		if(!flag){
			flag = receiverReportPage.summaryReport();
		}
		Assert.assertTrue(flag);
	}
	
	@AfterTest
	public void endTest() {
		senderReportPage = new ReportPage(sender.driver);
		receiverReportPage.clickContinue();
		senderReportPage.clickExit();
		senderReportPage.clickYes();
		receiver.releaseDriver();
		sender.releaseDriver();
	}
}
