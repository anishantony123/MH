package com.mh.ui.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JFileChooser;

import com.mh.model.HMConstants;
import com.mh.ui.AdvancedPanel;

public class Utils {
	public static GridBagConstraints getConStraints(int gridx,int gridy ,Insets inset) {
		GridBagConstraints gbc_contraint = new GridBagConstraints();
		gbc_contraint.anchor = GridBagConstraints.WEST;
		if(inset!=null)
			gbc_contraint.insets = inset;
		else
			gbc_contraint.insets = new Insets(0, 0, 5, 5);
		gbc_contraint.gridx = gridx;
		gbc_contraint.gridy = gridy;
		return gbc_contraint;
	}
	
	public static String getConfigValue(String key){
		String resultPath = "";
		try{
			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			resultPath = prop.getProperty(key).toString();
		}catch(Exception ee){
			System.out.println(ee);
		}
		
		return resultPath;

	}

	public static void setConfigValue(String key,String value) {
		try{
			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			prop.setProperty(key, value);
			prop.store(new FileOutputStream("config.properties"), null);
		}catch(Exception ee){
			System.out.println(ee);
		}
	}
	public static File[] choosedFiles(JFileChooser backupFileChooser, Component parent, String configKey,int selModel,boolean multiselection) {
		File[] result = null;
		backupFileChooser.setCurrentDirectory(new File(Utils.getConfigValue(configKey)));
		backupFileChooser.setMultiSelectionEnabled(multiselection);
		backupFileChooser.setFileSelectionMode(selModel);
		int option = backupFileChooser.showSaveDialog(parent);
		if (option == JFileChooser.APPROVE_OPTION) {
			if(multiselection){
				result = backupFileChooser.getSelectedFiles();
			}else {
				result = new File[]{backupFileChooser.getSelectedFile()};
			}
				
			Utils.setConfigValue(configKey,backupFileChooser.getCurrentDirectory().getAbsolutePath().toString());
		}
		return result;
	}

}
