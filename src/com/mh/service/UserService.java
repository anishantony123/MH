package com.mh.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileDeleteStrategy;

import com.mh.dao.UserDAO;
import com.mh.model.HMConstants;
import com.mh.model.User;
import com.mh.model.UserData;
import com.mh.ui.AddUserPanel;

public class UserService {
	UserDAO userDAO;
	PDFServices pdfServices;
	public Boolean save(User user,AddUserPanel addUserPanel) {
		// TODO Auto-generated method stub
		userDAO=new UserDAO();
		pdfServices= new PDFServices();
		List<UserData> userData= user.getUserData();
		if(userData!=null){
			int currSequenceMax = 0;//add new
			if(user.getId()!=null && user.getId()>0){// update
				List<UserData> alreadyList = userDAO.findUserDataByuserId(user.getId());
				currSequenceMax = (alreadyList!=null && alreadyList.size()>0)?alreadyList.size():0;
			}
			int length = userData.size();
			double dx = 100/length;
				for(UserData userD :userData){
					List<String> files=null;
					if((files = userD.getFilePath())!=null){						
							String path= pdfServices.convertImageToPdf(files.get(0));
							if(path!=null){
								currSequenceMax++;
								userD.setFile(new File(path));
								userD.setSequence(currSequenceMax);
							}
						
					}
					addUserPanel.timer += dx;
					System.out.println(addUserPanel.timer);
				}
			
		}
		user.setActiveYN(HMConstants.ACTIVE);
		userDAO.save(user);
		return true;
		
	}
	public List<User> getUsers(){
		userDAO=new UserDAO();
		return userDAO.getUsers();
	}
	@Deprecated
	public List<UserData>  findUserDataByuserId(Long id){
		
		userDAO=new UserDAO();
		return userDAO.findUserDataByuserId(id);
	}
	
	public String  viewUserDataFiles(Long id){
		
		UUID uid = UUID.randomUUID();
		
		userDAO=new UserDAO();
		userDAO.retrieveUserDataFiles(id,uid.toString());
		return uid.toString();
	}
	public void cleanDir(String tempdirectorypath) {
		if(tempdirectorypath!=null && !tempdirectorypath.trim().equals("")){
			File f= new File(tempdirectorypath);
			File [] files = f.listFiles();
			int length =0;
			if(files!=null && (length=files.length) >0){
				for(int i=0;i<length;i++){
					try {
						FileDeleteStrategy.FORCE.delete(files[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						new JOptionPane().setVisible(true);
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	public void deactivateUser(User user) {
		// TODO Auto-generated method stub
		userDAO=new UserDAO();
		user.setActiveYN(HMConstants.DEACTIVE);
		userDAO.toggleActivateUser(user);
	}
	

}
