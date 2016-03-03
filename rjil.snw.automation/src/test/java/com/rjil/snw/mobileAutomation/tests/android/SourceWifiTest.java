package com.rjil.snw.mobileAutomation.tests.android;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rjil.snw.mobileAutomation.PropertyFileReader;
import com.rjil.snw.mobileAutomation.pageobjects.android.HomePage;
import com.rjil.snw.mobileAutomation.test.BaseTest;

public class SourceWifiTest {
static PropertyFileReader properties = new PropertyFileReader();
	
	BaseTest sender = new BaseTest();
	BaseTest receiver1 = new BaseTest();
	BaseTest receiver2 = new BaseTest();
	
	HomePage senderHomePage = null;
	HomePage receiverHomePage1 = null;
	HomePage receiverHomePage2 = null;
	
	String senderUdid = null;
	String receiverUdid1 = null;
	String receiverUdid2 = null;
	
	public SourceWifiTest() {
		senderUdid = properties.getKeyValues("SenderUdid");
		receiverUdid1 = properties.getKeyValues("ReceiverUdid");
		receiverUdid2 = properties.getKeyValues("ReceiverUdid2");
		String senderPortNo = properties.getKeyValues("SenderPortNo");
		String receiverPortNo1 = properties.getKeyValues("ReceiverPortNo");
		String receiverPortNo2= properties.getKeyValues("ReceiverPortNo2");
		String appPackage = properties.getKeyValues("SnwPackage");
		String appActivity = properties.getKeyValues("SnwActivity");
		sender.initialiseDriver("android", senderUdid, senderPortNo, appPackage, appActivity);
		receiver1.initialiseDriver("android", receiverUdid1, receiverPortNo1, appPackage, appActivity);
		receiver2.initialiseDriver("android", receiverUdid2, receiverPortNo2, appPackage, appActivity);
	}
	
	@BeforeClass
	public void setup() {
		senderHomePage = new HomePage(sender.driver);
		receiverHomePage1 = new HomePage(receiver1.driver);
		receiverHomePage2 = new HomePage(receiver2.driver);
	}
	
	@Test
	public void wifiRecognitionTest() {
		String wifiName1 = receiverHomePage1.getWifiName();
		String wifiName2 = receiverHomePage2.getWifiName();
		senderHomePage.getWifiPage();
		Assert.assertTrue(senderHomePage.isWifiNamePresent(wifiName1));
		Assert.assertTrue(senderHomePage.isWifiNamePresent(wifiName2));
	}
	
	@AfterTest
	public void endTest() {
		receiver1.releaseDriver();
		receiver2.releaseDriver();
		sender.releaseDriver();
	}
}
