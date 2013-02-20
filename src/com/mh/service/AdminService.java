package com.mh.service;

import com.mh.dao.AdminDAO;
import com.mh.model.Admin;

public class AdminService {
	AdminDAO adminDAO;
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

}
