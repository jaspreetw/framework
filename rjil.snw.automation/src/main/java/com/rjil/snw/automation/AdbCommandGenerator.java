package com.rjil.snw.automation;

import java.io.BufferedReader;
import java.io.IOException;

public class AdbCommandGenerator {

	public static String getDeviceName(String udid) {
		try {
			String str = "adb -s " + udid + " shell getprop ro.product.model";
			BufferedReader buffer = CommandRunner.executeCommand(str);
			return buffer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
