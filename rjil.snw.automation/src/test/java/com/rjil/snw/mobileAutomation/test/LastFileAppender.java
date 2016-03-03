package com.rjil.snw.mobileAutomation.test;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

public class LastFileAppender extends RollingFileAppender {
	File logFile;

	@Override
	public void append(LoggingEvent event) {
		checkLogFileExist();
		super.append(event);
	}

	private void checkLogFileExist() {
		if (logFile == null)
			logFile = new File(super.fileName);
		if (!logFile.exists()) {
			try {
				logFile = new File(super.fileName);
				logFile.createNewFile();
				super.setFile(super.fileName, true, super.getBufferedIO(),
						super.getBufferSize());
			} catch (IOException e) {
				System.out.println("Error while create new log file.");
			}
		}
	}
}