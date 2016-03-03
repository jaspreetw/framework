package com.rjil.snw.mobileAutomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rjil.snw.mobileAutomation.test.LoggingClass;

import org.apache.commons.exec.OS;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UtilityClass {

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

	public static String getVersionName() {
		String versionName = null;
		try {
			String aaptPath = properties.getKeyValues("AaptPath");
			String apkPath = properties.getKeyValues("ApkPath");
			String find = UtilityClass.setFind();
			String str = aaptPath + " dump badging " + apkPath + " | " + find + " 'versionName'";
			BufferedReader buffer = CommandRunner.executeCommand(str);
			versionName = buffer.readLine();
			versionName = versionName.substring(versionName.indexOf("versionName") + 13,
					versionName.indexOf("platformBuildVersionName") - 2);
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return versionName;
	}
	
	public static int getBoxAppCount() {
		int countOfApps = -1;
		try {
			AdbResponse.connectToBoxWifi(properties.getKeyValues("BoxSsid"));
			String uri = properties.getKeyValues("uri");
			String url = properties.getKeyValues("url");
			String brand = properties.getKeyValues("brand");
			String header = properties.getKeyValues("header");
			String boxIp = properties.getKeyValues("boxIp");

			String str = "adb connect " + boxIp;
			CommandRunner.executeAdbCommand(str);

			str = "adb -s " + boxIp + ":5555 remount";
			CommandRunner.executeAdbCommand(str);

			String final_uri = uri + "&brand=" + brand;
			System.out.println("uri = " + final_uri + " header = " + header);
			str = callGet(url, final_uri, header);

			JSONArray json = new JSONArray(str);
			JSONObject jsonobject = json.getJSONObject(0);
			JSONArray jsonarray = jsonobject.getJSONArray("applications");
			HashMap<String, ArrayList<String>> appDetails = new HashMap<String, ArrayList<String>>();
			countOfApps = jsonarray.length();
			PrintWriter writer = new PrintWriter("packagename.txt", "UTF-8");
			writer.println("com.reliance.jio.jioswitch");
			writer.println("io.appium.unlock");
			writer.println("io.appium.settings");

			for (int i = 0; i < countOfApps; i++) {
				ArrayList<String> appDetailsArray = new ArrayList<String>();
				appDetailsArray.add(jsonarray.getJSONObject(i).getString("appPackage"));
				writer.println(jsonarray.getJSONObject(i).getString("appPackage"));
				appDetailsArray.add(jsonarray.getJSONObject(i).getString("appSHA256"));
				appDetailsArray.add(jsonarray.getJSONObject(i).getString("filename"));
				appDetails.put(jsonarray.getJSONObject(i).getString("appName"), appDetailsArray);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			LoggingClass.errorLog(e);
		}
		return countOfApps;
	}

	public static String callGet(String url, String uri, String header) throws ClientProtocolException, IOException {

		String responseString = null;
		HttpGet http = new HttpGet(url + uri);
		ArrayList<String> List;
		List = new ArrayList<String>(Arrays.asList(header.split(",")));
		for (String headerList : List) {
			String[] header1 = headerList.split(":");
			http.addHeader(header1[0], header1[1]);

			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = null;
			response = httpClient.execute(http);

			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity, "UTF-8");

		}
		return responseString;

	}
}
