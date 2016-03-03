package com.rjil.snw.mobileAutomation.test;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class LoggingClass extends TestListenerAdapter {
	private static org.apache.log4j.Logger logger = Logger.logger;

	@Override
	public void onTestFailure(ITestResult tr) {
		log(tr.getName() + "--Test method failed\n");
		log(tr.getThrowable().getMessage());
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		log(tr.getName() + "--Test method skipped\n");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		log(tr.getName() + "--Test method success\n");
	}

	public static void log(String string) {
		System.out.print(string);
		try {
			Logger.initLogger();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(string);
	}

	public static void errorLog(Exception e) {
		System.out.print(e);
		try {
			Logger.initLogger();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.error(e);
	}
}
