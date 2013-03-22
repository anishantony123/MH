package com.mh.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.mh.model.HMConstants;
import com.mh.model.User;
import com.mh.model.UserData;

public class UserDAO {
//INSERT INTO `hospital`.`users` (`id`, `name`, `reg_no`, `age`, `address_1`, `address_2`, `year`, `diaganosis`, `center`, `active_YN`, `created_by`, `modified_by`, `created_date`, `modified_date`, `sex`) VALUES (1, ?johny?, ?020?, 29, ?cochi?, ?cochi?, ?2007?, ?pranthan?, ?kusumagiri?, ?Y?, 1, 1, ?12-12-2012?, ?12-12-2012?, ?M?);
	/*private String saveQuery="insert into users"+"" +
			"( `name`, `reg_no`, `dob`, `address_1`, `address_2`, `year`, `diaganosis`, `center`, `active_YN`,`sex`, `created_by`, `created_date`)"+
			" VALUES"+
			" (?,?,? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";*/
	private String saveQuery="insert into users"+"" +
			"( `name`, `reg_no`, `age`, `address_1`, `pincode`, `year`, `diaganosis`, `center`, `active_YN`,`sex`, `created_by`, `created_date`,`care_off`,`informer`,`city`,`district`,`state`)"+
			" VALUES"+
			" (?,?,? ,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
	/*private String updateQuery="update users set "+
			"name=?, reg_no=?, dob=?, address_1=?, address_2=?, year=?, diaganosis=?, center=?, active_YN=?, sex=?,modified_by=?, modified_date=?"+
			" where id = ?";*/
	private String updateQuery="update users set "+
			"name=?, reg_no=?, age=?, address_1=?, pincode=?, year=?, diaganosis=?, center=?, active_YN=?, sex=?,modified_by=?, modified_date=?, care_off=? ,informer=?, city=?, district=?,state=?"+
			" where id = ?";
	private String toggleactivateUser="update users set active_YN = ? where id = ? ";
	private String findUserById="select * from user where id = ? and active_YN = ?";
	private String findAllUsersForXls="select `name`, `reg_no`, `age`, `address_1`,`city`,`district`,`state`, `pincode`, `year`, `diaganosis`, `center`, `active_YN`,`sex`, `created_by`, `created_date`,`care_off`,`informer` from users where active_YN = 'A'";
	private String findUserDataByUserId="select * from user_data where USER_ID = ? order by SEQUENCE";
	private String fileInsertQuery="INSERT INTO user_data (USER_ID, DATAFILE_NAME,DATAFILE_CONTENT, SEQUENCE) VALUES (?,?,?,?)";
	//private String fileUpdateQuery="INSERT INTO user_data (`USER_ID`, `DATAFILE_NAME`,'DATAFILE_CONTENT', `SEQUENCE`) VALUES (?,?,? ?)";
	
	public void save(User user) {
		Connection conn= ConnectionManager.createConnection();
		FileInputStream io=null;
		try {
			long auto_id=-1;// auto generated user_id
			boolean returnLastInsertId=false;
			PreparedStatement pStmt;
			if(user.getId()==null){
				pStmt = conn.prepareStatement(saveQuery,Statement.RETURN_GENERATED_KEYS);
				pStmt.setLong(11, user.getCreatedBy());
				pStmt.setTimestamp(12, new Timestamp(user.getCreatedDate().getTime()));
				returnLastInsertId=true;
			}else{
				pStmt = conn.prepareStatement(updateQuery);
				pStmt.setLong(11, (user.getModifiedBy()==null)?1:user.getModifiedBy());
				pStmt.setTimestamp(12, new Timestamp( user.getModifiedDate().getTime()));
				pStmt.setInt(18, user.getId().intValue());
				auto_id=user.getId();
			}			
			pStmt.setString(1, user.getName());
			pStmt.setString(2, user.getRegNo());
			//pStmt.setDate(3, user.getDob()!=null ?new Date(user.getDob().getTime()):null);
			pStmt.setInt(3, user.getAge()!=null ?user.getAge():0);
			pStmt.setString(4, user.getAddress1());
			pStmt.setString(5, user.getPincode());
			pStmt.setString(6, user.getYear());
			pStmt.setString(7, user.getDiagonosis());
			pStmt.setString(8, user.getCenter());
			pStmt.setString(9, user.getActiveYN());			
			pStmt.setString(10, user.getSex());
			pStmt.setString(13, user.getCareOff());
			pStmt.setString(14, user.getInformer());
			pStmt.setString(15, user.getCity());
			pStmt.setString(16, user.getDistrict());
			pStmt.setString(17, user.getState());
			
			pStmt.executeUpdate();
			
			if(returnLastInsertId) {
			   ResultSet rs = pStmt.getGeneratedKeys();
			    rs.next();
			   auto_id = rs.getLong(1);
			   rs.close();
			}
			pStmt.close();
			
			List<UserData> userData= user.getUserData();
			if(userData!=null){
				for(UserData userD :userData){
					pStmt=conn.prepareStatement(fileInsertQuery);					
					pStmt.setLong(1,auto_id);
					File file=userD.getFile();
					pStmt.setString(2, file.getName());					
					io = new FileInputStream(file);
					System.out.println("pdf file length: "+(int)file.length());
					pStmt.setBinaryStream(3, io,(int)file.length());
					pStmt.setInt(4, userD.getSequence());
					
					pStmt.executeUpdate();
					
					pStmt.close();
				}
			}
			
			ConnectionManager.inValidateConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectionManager.inValidateConnection();
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(io!=null){
				try {
					io.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public List<User> getUsers(){
		List<User> users=null;
			Statement stmt;
			try {
				stmt = ConnectionManager.createConnection().createStatement();
			
			ResultSet res= stmt.executeQuery("Select * from users where active_YN = 'A' order by id desc") ;
			if(res != null){
				users = new ArrayList<User>();
				while(res.next()){
					users.add(mapRow(res));
				}
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ConnectionManager.inValidateConnection();
			}catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return users;
		
	}
	
	public ResultSet getUsersForXls(){
			Statement stmt;
			ResultSet res = null;
			try {
				stmt = ConnectionManager.createConnection().createStatement();
			
				res= stmt.executeQuery(findAllUsersForXls) ;
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ConnectionManager.inValidateConnection();
			}catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return res;
		
	}
	
	public User findById(Long id){
		User user=null;
		PreparedStatement pStmt;
		Connection conn= ConnectionManager.createConnection();
		try{
			
			pStmt = conn.prepareStatement(findUserById);
			pStmt.setLong(1, (id!=null)?id.intValue():-1l);
			ResultSet res= pStmt.executeQuery() ;
			if(res != null){				
				while(res.next()){
					user =mapRow(res);
				}
			}
			
		}catch (Throwable e) {
			// TODO: handle exception
		}
		
			
		return user;
	}
	public List<UserData> findUserDataByuserId(Long id){
		List<UserData> userDataList=null;
		PreparedStatement pStmt;
		Connection conn= ConnectionManager.createConnection();
		try{
			
			pStmt = conn.prepareStatement(findUserDataByUserId);
			pStmt.setLong(1, (id!=null)?id.intValue():-1l);
			ResultSet res= pStmt.executeQuery() ;
			if(res != null){
				userDataList = new ArrayList<UserData>();
				while(res.next()){
					userDataList.add(mapRowUserData(res));
				}
			}
			
		}catch (Throwable e) {
			// TODO: handle exception
		}
		
			
		return userDataList;
	}
	
	public UserData mapRowUserData(ResultSet res) {
		UserData userData =null;
			userData= new UserData();
			FileOutputStream fos=null;
			try{
			userData.setId(res.getLong("ID"));
			userData.setUserId(res.getLong("USER_ID"));
			userData.setFileName(res.getString("DATAFILE_NAME"));
			userData.setSequence(res.getInt("SEQUENCE"));
			
			
			byte[] output = IOUtils.toByteArray(res.getBinaryStream("DATAFILE_CONTENT"));
			
			File file= new File(HMConstants.tempDirectoryPath+"/_"+userData.getSequence().toString()+".pdf");
			fos = new FileOutputStream(file);
			fos.write(output);
			userData.setFile(file);
			userData.setSequence(res.getInt("SEQUENCE"));
			}catch (Exception e) {
				// TODO: handle exception
			}
			finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return userData;
	}
	public User mapRow(ResultSet res) throws Throwable{
		User user= null;
			user = new User();
			user.setId(res.getLong("id"));
			user.setName(res.getString("name"));
			user.setRegNo(res.getString("reg_no"));
			//user.setDob(res.getDate("dob"));
			user.setAge((res.getInt("age")>0)?res.getInt("age"):null);
			user.setAddress1(res.getString("address_1"));
			user.setAddress2(res.getString("address_2"));
			user.setPincode(res.getString("pincode"));
			user.setYear(res.getString("year"));
			user.setDiagonosis(res.getString("diaganosis"));
			user.setCenter(res.getString("center"));
			user.setSex(res.getString("sex"));
			user.setActiveYN(res.getString("active_YN"));
			user.setCreatedBy(res.getLong("created_by"));
			user.setCreatedDate(res.getTimestamp("created_date"));			
			user.setModifiedBy(res.getLong("modified_by"));
			user.setCreatedDate(res.getTimestamp("modified_date"));
			user.setCareOff(res.getString("care_off"));
			user.setInformer(res.getString("informer"));
			user.setCity(res.getString("city"));
			user.setDistrict(res.getString("district"));
			user.setState(res.getString("state"));
			
		return user;			
	}
	public void retrieveUserDataFiles(Long id, String path) {

		List<UserData> userDataList=null;
		PreparedStatement pStmt;
		Connection conn= ConnectionManager.createConnection();
		try{
			
			pStmt = conn.prepareStatement(findUserDataByUserId);
			pStmt.setLong(1, (id!=null)?id.intValue():-1l);
			ResultSet res= pStmt.executeQuery() ;
			if(res != null){
				userDataList = new ArrayList<UserData>();
				while(res.next()){
					userDataList.add(mapRowUserDatatoFileStructure(res,path));
				}
			}
			
		}catch (Throwable e) {
			// TODO: handle exception
		}
		
			
	
	}
	private UserData mapRowUserDatatoFileStructure(ResultSet res, String path) {

		UserData userData =null;
			userData= new UserData();
			FileOutputStream fos=null;
			try{
			userData.setId(res.getLong("ID"));
			userData.setUserId(res.getLong("USER_ID"));
			userData.setFileName(res.getString("DATAFILE_NAME"));
			userData.setSequence(res.getInt("SEQUENCE"));
			
			
			byte[] output = IOUtils.toByteArray(res.getBinaryStream("DATAFILE_CONTENT"));
			File f= new File(HMConstants.tempDirectoryPath+"/"+path);
			f.mkdir();
			File file= new File(f.getAbsolutePath()+"/_"+userData.getSequence().toString()+".pdf");
			fos = new FileOutputStream(file);
			fos.write(output);
			userData.setFile(file);
			userData.setSequence(res.getInt("SEQUENCE"));
			}catch (Exception e) {
				// TODO: handle exception
			}
			finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return userData;
	
	}
	public void toggleActivateUser(User user) {
		// TODO Auto-generated method stub
		Connection conn= ConnectionManager.createConnection();
		PreparedStatement pStmt;
		
		try {
			pStmt = conn.prepareStatement(toggleactivateUser);
			pStmt.setString(1, user.getActiveYN());
			pStmt.setLong(2, user.getId());
			
			pStmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
}
