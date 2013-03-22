package com.mh.service;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mh.dao.AdminDAO;
import com.mh.dao.UserDAO;
import com.mh.model.Admin;
import com.mh.model.HMConstants;

public class AdminService {
	AdminDAO adminDAO;
	UserDAO userDAO;
	public Long validateAdmin(String username,String password){
		adminDAO = new AdminDAO();
		return adminDAO.ValidateAdmin(username, password);
		
	}
	public void updateAdminByName(Admin admin) {
		// TODO Auto-generated method stub
		adminDAO = new AdminDAO();
		adminDAO.updateByName(admin);
	}
	public void save(Admin admin) {
		// TODO Auto-generated method stub
		adminDAO = new AdminDAO();
		adminDAO.save(admin);
	}
	public void exportToExcel(String fileName) throws Exception{
		// TODO Auto-generated method stub
		// Create new Excel workbook and sheet
		
		userDAO = new UserDAO();
	    HSSFWorkbook xlsWorkbook = new HSSFWorkbook();
	    HSSFSheet xlsSheet = xlsWorkbook.createSheet();
	    short rowIndex = 0;
	    ResultSet rs = userDAO.getUsersForXls();
	    ResultSetMetaData colInfo = rs.getMetaData();
	    List<String> colNames = new ArrayList<String>();
	    HSSFRow titleRow = xlsSheet.createRow(rowIndex++);

	    for (int i = 1; i <= colInfo.getColumnCount(); i++) {
	      colNames.add(colInfo.getColumnName(i));
	      titleRow.createCell((short) (i-1)).setCellValue(
	        new HSSFRichTextString(colInfo.getColumnName(i)));
	      xlsSheet.setColumnWidth((short) (i-1), (short) 4000);
	    }

	    // Save all the data from the database table rows
	    while (rs.next()) {
	      HSSFRow dataRow = xlsSheet.createRow(rowIndex++);
	      short colIndex = 0;
	      for (String colName : colNames) {
	        dataRow.createCell(colIndex++).setCellValue(
	          new HSSFRichTextString(rs.getString(colName)));
	      }
	    }
	    
	    // Write to disk
	    xlsWorkbook.write(new FileOutputStream(fileName));
		
	}

}
