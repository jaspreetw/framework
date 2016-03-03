package com.rjil.snw.mobileAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	Properties prop = new Properties();

	public String getKeyValues(String key) {
		try {
			InputStream input = null;
			String value = "";
				input = new FileInputStream("snw.properties");
				prop.load(input);
				value = prop.getProperty(key);
				System.out.println("Key -->" + key +" value =="+value);	
				return value;
		} catch (IOException e) {
			e.printStackTrace();
			return "No such value";
		}
	}// end of getKeyValues

}
