package com.rjil.snw.automation.tests.android;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.automation.PropertyFileReader;
import com.rjil.snw.automation.pageobjects.android.HeavyDataTransferPage;
import com.rjil.snw.automation.pageobjects.android.HomePage;
import com.rjil.snw.automation.pageobjects.android.LightDataTransferPage;
import com.rjil.snw.automation.pageobjects.android.PicturePage;
import com.rjil.snw.automation.pageobjects.android.ReportPage;
import com.rjil.snw.automation.test.BaseTest;


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

	public static RemoteWebDriver senderDriver;
	public static RemoteWebDriver receiverDriver;
	
	public DataTransferTest() throws MalformedURLException {
		senderUdid = properties.getKeyValues("SenderUdid");
		receiverUdid = properties.getKeyValues("ReceiverUdid");
		String senderPortNo = properties.getKeyValues("SenderPortNo");
		String receiverPortNo = properties.getKeyValues("ReceiverPortNo");
		sender.initialiseDriver("android", senderUdid, senderPortNo);
		receiver.initialiseDriver("android", receiverUdid, receiverPortNo);
	}
	
	@BeforeClass
	public void setup() {
		senderHomePage = new HomePage(sender.driver);
		receiverHomePage = new HomePage(receiver.driver);
		
		String wifiName = receiverHomePage.getWifiName();
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
		HashMap<String, String> hashmap = senderLightDataPage.createList();
		Assert.assertEquals(hashmap.get("Reminders"), "Not Supported");
	}
	
	@Test(dependsOnMethods = { "remindersNotSupportedTest" })
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
		HashMap<String, String> hashmap = senderHeavyDataPage.createList();
		Assert.assertEquals(hashmap.get("Documents"), "Not Supported");
	}
	
	@Test(dependsOnMethods = { "documentsNotSupportedTest" })
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
	
	@AfterTest
	public void endTest() {
		senderReportPage = new ReportPage(sender.driver);
		receiverReportPage = new ReportPage(receiver.driver);
		receiverReportPage.clickContinue();
		receiverReportPage.clickTryAgain();
		receiverReportPage.clickTryAgain();
		receiverReportPage.clickContinue();
		senderReportPage.clickExit();
		senderReportPage.clickYes();
		receiver.releaseDriver();
		sender.releaseDriver();
	}
}