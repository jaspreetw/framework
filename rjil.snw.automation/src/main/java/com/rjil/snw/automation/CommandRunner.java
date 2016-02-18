package com.rjil.snw.automation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.exec.OS;

public class CommandRunner {
	static PropertyFileReader properties = new PropertyFileReader();
	@SuppressWarnings("finally")
	public static BufferedReader executeCommand(String cmd) throws IOException {
		BufferedReader buf = null;
		try {
			System.out.println("Executing command :" + cmd);
			Runtime run = Runtime.getRuntime();
			Process pr = null;
			if (OS.isFamilyMac()) {
				String path =properties.getKeyValues("MacExecutionPath").concat(cmd);
				String[] commands = { "/bin/sh", "-c", path };
				pr = run.exec(commands);
			} else if (OS.isFamilyWindows()) {
				pr = run.exec(cmd);
			}
			pr.waitFor();
			buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			//System.out.println("buf"+buf.readLine());

		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//System.out.println("Getting out of callAdb");
			return buf;
		}
	}
}
