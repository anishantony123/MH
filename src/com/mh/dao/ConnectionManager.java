package com.mh.dao;

import static com.mh.model.HMConstants.CONNECTION_DRIVER;
import static com.mh.model.HMConstants.CONNECTION_PASSWORD;
import static com.mh.model.HMConstants.CONNECTION_STRING;
import static com.mh.model.HMConstants.CONNECTION_SUCCESS_STRING;
import static com.mh.model.HMConstants.CONNECTION_USRNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionManager {
	static Connection con=null;
	public static Connection createConnection(){
		
		try {
			Class.forName(CONNECTION_DRIVER);
			String url = CONNECTION_STRING;
			con = DriverManager.getConnection(url, CONNECTION_USRNAME, CONNECTION_PASSWORD);
			System.out.println(CONNECTION_SUCCESS_STRING);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}

	public static void inValidateConnection() {
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
