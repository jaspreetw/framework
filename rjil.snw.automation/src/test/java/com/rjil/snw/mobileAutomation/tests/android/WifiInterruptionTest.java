package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.AdbResponse;
import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.pageobjects.android.HeavyDataTransferPage;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.pageobjects.android.LightDataTransferPage;
import com.rjil.snw.mobileAutomation.pageobjects.android.PicturePage;
import com.rjil.snw.mobileAutomation.pageobjects.android.ReportPage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class WifiInterruptionTest {
static PropertyFileReader properties = new PropertyFileReader();
	
	BaseTest sender = new BaseTest();
	BaseTest receiver = new BaseTest();
	
	HomePage senderHomePage = null;
	HomePage receiverHomePage = null;
	
	PicturePage senderPicturePage = null;
	PicturePage receiverPicturePage = null;
	
	LightDataTransferPage senderLightDataPage = null;
	
	HeavyDataTransferPage senderHeavyDataPage = null;
	HeavyDataTransferPage receiverHeavyDataPage = null;
	
	ReportPage senderReportPage = null;
	ReportPage receiverReportPage = null;
	
	String senderUdid = null;
	String receiverUdid = null;
	
	HashMap<String, String> hashmap = null;
	
	public WifiInterruptionTest() throws MalformedURLException {
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
		
		receiverPicturePage = new PicturePage(receiver.driver);
		String imageName = receiverPicturePage.getImageName();
		senderPicturePage = new PicturePage(sender.driver);
		senderPicturePage.setImage(imageName);
	}
	
	@Test
	public void interruptWifiTest() {
		senderLightDataPage = new LightDataTransferPage(sender.driver);
		senderLightDataPage.skipToHeavyDataTransfer();
		senderHeavyDataPage = new HeavyDataTransferPage(sender.driver);
		receiverHeavyDataPage = new HeavyDataTransferPage(receiver.driver);
		Assert.assertTrue(senderHeavyDataPage.isSenderWifiInterrupted(senderUdid));
		Assert.assertTrue(receiverHeavyDataPage.isReceiverWifiInterrupted());
	}
	
	@Test(dependsOnMethods = {"interruptWifiTest"})
	public void resumeDataTransferTest() {
		AdbResponse.startWifi(senderUdid);
		setup();
		senderLightDataPage = new LightDataTransferPage(sender.driver);
		senderLightDataPage.resumeDataTransfer();
		senderHeavyDataPage = new HeavyDataTransferPage(sender.driver);
		receiverHeavyDataPage = new HeavyDataTransferPage(receiver.driver);
		senderHeavyDataPage.transferData();
		Assert.assertEquals(receiverHeavyDataPage.getPercentageTransferred(), senderHeavyDataPage.getPercentageTransferred());
	}
	
	@AfterTest
	public void endTest() {
		senderReportPage = new ReportPage(sender.driver);
		receiverReportPage = new ReportPage(receiver.driver);
		receiverReportPage.clickContinue();
		senderReportPage.clickExit();
		senderReportPage.clickYes();
		receiver.releaseDriver();
		sender.releaseDriver();
	}
	
}

