package com.rjil.snw.automation;

import java.io.BufferedReader;
import java.io.IOException;

public class AdbResponse {
	static PropertyFileReader properties = new PropertyFileReader();

	public static String getDeviceName(String udid) {
		try {
			String str = "adb -s " + udid + " shell getprop ro.product.model";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			return buffer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getNetworkName(String udid) {
		try {
			String str = "adb -s " + udid + " shell dumpsys netstats | grep -E 'iface=wlan.*networkId'";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			String wifi = buffer.readLine();
			if (wifi != null) {
				wifi = wifi.substring(wifi.indexOf("networkId") + 10, wifi.indexOf("]"));
			} 
			else {
				wifi = "Not connected to any network";
			}
			return wifi;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getVersionNumber() {
		try {
			String aaptPath =properties.getKeyValues("AaptPath");
			String apkPath =properties.getKeyValues("ApkPath");
			String str = aaptPath + " dump badging " + apkPath + " | grep 'versionName'";
			BufferedReader buffer = CommandRunner.executeCommand(str);
			String versionName = buffer.readLine();
			versionName = versionName.substring(versionName.indexOf("versionName") + 13,
					versionName.indexOf("platformBuildVersionName") - 2);
			return versionName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
