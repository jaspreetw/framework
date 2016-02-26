package com.rjil.snw.automation.test;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class LoggingClass extends TestListenerAdapter{
	private int m_count = 0;
	private org.apache.log4j.Logger logger = Logger.logger;
	
    @Override
    public void onTestFailure(ITestResult tr) {
        log(tr.getName()+ "--Test method failed\n");
        log(tr.getThrowable().getMessage());
    }
	 
    @Override
    public void onTestSkipped(ITestResult tr) {
        log(tr.getName()+ "--Test method skipped\n");
    }
	 
    @Override
    public void onTestSuccess(ITestResult tr) {
        log(tr.getName()+ "--Test method success\n");
    }
	 
    private void log(String string) {
        System.out.print(string);
        try {
			Logger.initLogger();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.info(string);
        if (++m_count % 40 == 0) {
	    System.out.println("");
        }
    }
}
