package com.mh.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mh.model.Admin;

public class AdminDAO {
	public String update = "update admin set password = ? where username= ?";
	public String login = "select * from admin where username= ? and password = ?";
	public String save ="INSERT INTO admin (username, password) VALUES (?,?)";
	public Long ValidateAdmin(String username,String password){
		Long id = -1l;
		Connection conn= ConnectionManager.createConnection();
		try {
			PreparedStatement pStmt =conn.prepareStatement(login);
			pStmt.setString(1, username);
			pStmt.setString(2, password);
			ResultSet rs = pStmt.executeQuery();
			
			if(rs!=null && rs.next()){
				id = rs.getLong("id");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return id;
	}
	public void updateByName(Admin admin) {
		Connection conn= ConnectionManager.createConnection();
		try {
			PreparedStatement pStmt =conn.prepareStatement(update);
			pStmt.setString(1, admin.getPassword());
			pStmt.setString(2, admin.getUsername());
			 pStmt.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void save(Admin admin) {
		// TODO Auto-generated method stub
		Connection conn= ConnectionManager.createConnection();
		try {
			PreparedStatement pStmt =conn.prepareStatement(save );
			pStmt.setString(1, admin.getUsername());
			pStmt.setString(2, admin.getPassword());
			 pStmt.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
