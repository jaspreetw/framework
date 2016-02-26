package com.rjil.snw.automation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.exec.OS;

public class AdbResponse {
	static PropertyFileReader properties = new PropertyFileReader();

	public static String setFind() {
		String find = "";
		if (OS.isFamilyMac()) {
			find = "grep";
		} else if (OS.isFamilyWindows()) {
			find = "findstr";
		}
		return find;
	}

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
			String find = setFind();
			String str = "adb -s " + udid + " shell dumpsys netstats | " + find + " -E 'iface=wlan.*networkId'";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			String wifi = buffer.readLine();
			if (wifi != null) {
				wifi = wifi.substring(wifi.indexOf("networkId") + 10, wifi.indexOf("]"));
			} else {
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
			String aaptPath = properties.getKeyValues("AaptPath");
			String apkPath = properties.getKeyValues("ApkPath");
			String find = setFind();
			String str = aaptPath + " dump badging " + apkPath + " | " + find + " 'versionName'";
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

	public static void connectToBoxWifi(String udid) {
		try {
			String str = "adb -s " + udid
					+ " shell am start -n com.reliance.automationhelper/.MainActivity -e automation wifi -e state true";
			CommandRunner.executeAdbCommand(str);
			str = "adb -s " + udid
					+ " shell am start -n com.reliance.automationhelper/.MainActivity -e automation wifiConnect -e state true -e ssid "
					+ properties.getKeyValues("BoxSsid") + " -e pwd 12345678 -e nwtype WPA2";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void uninstallSnwApp(String udid) {
		try {
			String find = setFind();
			String str = "adb -s " + udid + " shell pm list packages | " + find + " com.reliance.jio.jioswitch";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			String result = buffer.readLine();
			if (result == null || result.isEmpty()) {
				System.out.println("Snw app not present on phone : ");
			} else {
				str = "adb -s " + udid + " uninstall com.reliance.jio.jioswitch";
				CommandRunner.executeAdbCommand(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cleanPhone(String udid) {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader("packagename.txt"));
			String packageName;
			String result;
			String find = setFind();
			String str;
			while ((packageName = buffer.readLine()) != null) {
				str = "adb -s " + udid + " shell pm list packages | " + find + " " + packageName;
				BufferedReader buf = CommandRunner.executeAdbCommand(str);
				result = buf.readLine();
				if (result == null || result.isEmpty()) {
					System.out.println("app not present on phone : " + packageName);
				} else {
					str = "adb -s " + udid + " uninstall " + packageName;
					CommandRunner.executeAdbCommand(str);
				}
			}
			str = "adb -s " + udid
					+ " shell  am start -n com.reliance.automationhelper/.MainActivity -e automation clearAllData -e state \"true\"";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String pullSnw(String udid) {
		try {
			String apkPathInPhone = properties.getKeyValues("ApkPathInPhone");
			String apkPath = properties.getKeyValues("ApkPath");
			String str = "adb -s " + udid + " pull " + apkPathInPhone + " " + apkPath;
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			return buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String installSnw(String udid) {
		try {
			String line = "";
			String result = "";
			String apkPath = properties.getKeyValues("ApkPath");
			String str = "adb -s " + udid + " install " + apkPath;
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			while ((line = buf.readLine()) != null) {
				if (!line.isEmpty())
					result = line;
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int installApps(String udid) {
		try {
			String str = "adb -s " + udid + " pull " + properties.getKeyValues("AppFolderInMobile") + " "
					+ properties.getKeyValues("AppFolderInSystem");
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			File appfile = new File(properties.getKeyValues("AppFolderInSystem"));
			File[] files = appfile.listFiles();
			int count = 0;
			for (File file1 : files) {
				if (file1.getName().endsWith(".apk")) {
					str = "adb -s " + udid + " install "+properties.getKeyValues("AppFolderInSystem") + file1.getName();
					buf=CommandRunner.executeAdbCommand(str);
					System.out.println(file1.getName() + " apk installed " + buf.readLine());
					count++;
				}
			}
			return count;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void stopWifi(String udid) {
		try {
			String str = "adb -s " + udid
					+ " shell am start -n com.reliance.automationhelper/.MainActivity -e automation wifi -e state false";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startWifi(String udid) {
		try {
			String str = "adb -s " + udid
					+ " shell am start -n com.reliance.automationhelper/.MainActivity -e automation wifi -e state true";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
