package com.rjil.snw.mobileAutomation.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

public class Logger {

	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Logger.class);

	public static Level defaultLogLevel = Level.INFO;

	private static Level currentLogLevel = Level.INFO;

	public static Level getCurrentLogLevel() {
		return currentLogLevel;
	}

	public static void initLogger() throws IOException {

		File file = new File("log4j.properties");
		System.out.println(file.getAbsolutePath() + " " + file.getName());

		InputStream in = new FileInputStream(file);

		Properties configProps = new Properties();

		configProps.load(in);

		in.close();

		PropertyConfigurator.configure(configProps);

		currentLogLevel = org.apache.log4j.Logger.getRootLogger().getLevel();

	}

	public static void setCurrentLogLevel(Level level) {

		if (level.equals(org.apache.log4j.Level.INFO)) {

			logger.setLevel(org.apache.log4j.Level.INFO);

			currentLogLevel = Level.INFO;
		}

		else if (level.equals(org.apache.log4j.Level.ERROR)) {

			logger.setLevel(org.apache.log4j.Level.ERROR);

			currentLogLevel = Level.ERROR;
		}

		else if (level.equals(org.apache.log4j.Level.DEBUG)) {

			logger.setLevel(org.apache.log4j.Level.DEBUG);

			currentLogLevel = Level.DEBUG;
		}

		else if (level.equals(org.apache.log4j.Level.ALL)) {

			logger.setLevel(org.apache.log4j.Level.ALL);

			currentLogLevel = Level.ALL;
		}

		else if (level.equals(org.apache.log4j.Level.WARN)) {

			logger.setLevel(org.apache.log4j.Level.WARN);

			currentLogLevel = Level.WARN;
		}

		else if (level.equals(org.apache.log4j.Level.FATAL)) {

			logger.setLevel(org.apache.log4j.Level.FATAL);

			currentLogLevel = Level.FATAL;
		}

		else {

			logger.setLevel(org.apache.log4j.Level.INFO);

			currentLogLevel = Level.INFO;
		}
		if (logger.isInfoEnabled())
			logger.info("log level changed");

	}
}
