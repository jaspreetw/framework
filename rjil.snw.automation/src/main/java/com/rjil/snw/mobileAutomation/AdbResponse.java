package com.rjil.snw.mobileAutomation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.exec.OS;

import com.rjil.snw.mobileAutomation.test.Logger;
import com.rjil.snw.mobileAutomation.test.LoggingClass;

public class AdbResponse {
	static PropertyFileReader properties = new PropertyFileReader();

	public static String getDeviceName(String udid) {
		String deviceName = null;
		try {
			String str = "adb -s " + udid + " shell getprop ro.product.model";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			deviceName = buffer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return deviceName;
	}

	public static String getNetworkName(String udid) {
		String wifi = null;
		try {
			String find = UtilityClass.setFind();
			String str = "adb -s " + udid + " shell dumpsys netstats | " + find + " -E 'iface=wlan.*networkId'";
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			wifi = buffer.readLine();
			if (wifi != null) {
				wifi = wifi.substring(wifi.indexOf("networkId") + 10, wifi.indexOf("]"));
			} else {
				wifi = "Not connected to any network";
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return wifi;

	}

	public static void connectToBoxWifi(String udid) {
		try {
			String automationHelper = properties.getKeyValues("AutomationHelper");
			String boxSsid = properties.getKeyValues("BoxSsid");
			String str = "adb -s " + udid + " shell am start -n " + automationHelper
					+ " -e automation wifi -e state true";
			CommandRunner.executeAdbCommand(str);
			str = "adb -s " + udid + " shell am start -n " + automationHelper
					+ " -e automation wifiConnect -e state true -e ssid " + boxSsid + " -e pwd 12345678 -e nwtype WPA2";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static void uninstallSnwApp(String udid) {
		try {
			String snwPackage = properties.getKeyValues("SnwPackage");
			String find = UtilityClass.setFind();
			String str = "adb -s " + udid + " shell pm list packages | " + find + " " + snwPackage;
			BufferedReader buffer = CommandRunner.executeAdbCommand(str);
			String result = buffer.readLine();
			if (result != null || !result.isEmpty()) {
				str = "adb -s " + udid + " uninstall " + snwPackage;
				CommandRunner.executeAdbCommand(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static void cleanPhone(String udid) {
		try {
			String automationHelper = properties.getKeyValues("AutomationHelper");
			BufferedReader buffer = new BufferedReader(new FileReader("packagename.txt"));
			String packageName;
			String result;
			String find = UtilityClass.setFind();
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
			str = "adb -s " + udid + " shell  am start -n " + automationHelper
					+ " -e automation clearAllData -e state \"true\"";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static String pullSnw(String udid) {
		String result = null;
		try {
			String apkPathInPhone = properties.getKeyValues("ApkPathInPhone");
			String apkPath = properties.getKeyValues("ApkPath");
			String str = "adb -s " + udid + " pull " + apkPathInPhone + " " + apkPath;
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			result = buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return result;
	}

	public static String installSnw(String udid) {
		String result = null;
		try {
			String line = "";
			String apkPath = properties.getKeyValues("ApkPath");
			String str = "adb -s " + udid + " install " + apkPath;
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			while ((line = buf.readLine()) != null) {
				if (!line.isEmpty())
					result = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return result;
	}

	public static int installApps(String udid) {
		int count = -1;
		try {
			String appFolder = properties.getKeyValues("AppFolderInMobile");
			String appFolderSystem = properties.getKeyValues("AppFolderInSystem");
			String str = "adb -s " + udid + " pull " + appFolder + " " + appFolderSystem;
			BufferedReader buf = CommandRunner.executeAdbCommand(str);
			File appfile = new File(properties.getKeyValues("AppFolderInSystem"));
			File[] files = appfile.listFiles();
			count = 0;
			for (File file1 : files) {
				if (file1.getName().endsWith(".apk")) {
					str = "adb -s " + udid + " install " + appFolderSystem + file1.getName();
					buf = CommandRunner.executeAdbCommand(str);
					System.out.println(file1.getName() + " apk installed " + buf.readLine());
					count++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return count;
	}

	public static void createLoactionFile(String udid) {
		try {
			String automationHelper = properties.getKeyValues("AutomationHelper");
			String oldAppFileMobile = properties.getKeyValues("OldAppFileMobile");
			String str = "adb -s " + udid + " am start -n " + automationHelper
					+ " -e automation getAppFileLocation -e state true -e destinationFIle " + oldAppFileMobile;
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static void installOldPhoneApps(String senderUdid, String receiverUdid) {
		try {
			String packageName;
			String str;
			String oldAppFolderSystem = properties.getKeyValues("OldAppFolderInSystem");
			String oldAppFileMobile = properties.getKeyValues("OldAppFileMobile");
			String oldAppFileSystem = properties.getKeyValues("OldAppFileSystem");
			String fileName = properties.getKeyValues("FileName");
			
			str = "adb -s " + senderUdid + " pull " + oldAppFileMobile + " " + oldAppFileSystem;
			CommandRunner.executeAdbCommand(str);
			
			BufferedReader buffer = new BufferedReader(new FileReader(oldAppFileSystem + "/" + fileName));
			String filePath = buffer.readLine();
			while ((packageName = buffer.readLine()) != null) {
				str = "adb -s " + receiverUdid + " pull " + filePath + packageName + " " + oldAppFolderSystem;
				CommandRunner.executeAdbCommand(str);
			}
			File appfile = new File(properties.getKeyValues("OldAppFolderInSystem"));
			File[] files = appfile.listFiles();
			for (File file1 : files) {
				if (file1.getName().endsWith(".apk") && !file1.getName().equals("com.reliance.jio.jioswitch-1.apk")) {
					str = "adb -s " + receiverUdid + " install " + oldAppFolderSystem + file1.getName();
					CommandRunner.executeAdbCommand(str);
				}
			}
			
			str = "adb -s " + receiverUdid + " shell input keyevent 4";
			CommandRunner.executeAdbCommand(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static void stopWifi(String udid) {
		try {
			String automationHelper = properties.getKeyValues("AutomationHelper");
			String str = "adb -s " + udid + " shell am start -n " + automationHelper
					+ " -e automation wifi -e state false";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}

	public static void startWifi(String udid) {
		try {
			String automationHelper = properties.getKeyValues("AutomationHelper");
			String str = "adb -s " + udid + " shell am start -n " + automationHelper
					+ " -e automation wifi -e state true";
			CommandRunner.executeAdbCommand(str);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
	}
}
