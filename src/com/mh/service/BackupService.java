package com.mh.service;

import com.mh.ui.exception.OnFailure;
import com.mh.ui.exception.OnSuccess;

public class BackupService {

	public void backupDB(String dbName, String dbUserName, String dbPassword, String path) throws Throwable {

        String executeCmd = "mysqldump -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;
        Process runtimeProcess;
       
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
            	 throw new OnSuccess("Backup created successfully !");
            } else {
            	 throw new OnFailure("Backup failed !");
            }
       

    }
public void restoreDB(String dbUserName, String dbPassword, String source) throws Throwable {
		
		

        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source "+source};

        Process runtimeProcess;

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                throw new OnSuccess("Backup restored successfully !");
            } else {
            	throw new OnFailure("Restore Failed !");
            }
    }
	public void dropAndRestoreDB(String dbName, String dbUserName, String dbPassword, String source) throws Throwable {
		System.out.println("DB Username:"+dbUserName);
		System.out.println("DB Password:"+dbPassword);
		System.out.println("DB Name:"+dbName);
		System.out.println("DB Source:"+source);

        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, dbName,"-e", "source "+source};

        Process runtimeProcess;
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                throw new OnSuccess("Backup restored successfully !");
            } else {
            	throw new OnFailure("Restore Failed !");
            }
       
    }
	
}
