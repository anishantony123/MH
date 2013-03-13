package com.mh.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	private Long id;
	private String name;
	private Integer age;
	private Date dob;
	private String address1;
	private String address2;
	private String pincode;
	private String city;
	private String district;
	private String state;	
	private String regNo;
	private String year;
	private String diagonosis;
	private String center;
	private String activeYN;
	private Long modifiedBy;
	private Long createdBy=1l;
	private Date createdDate;
	private Date modifiedDate;
	private String sex;
	private String careOff;
	private String informer;
	private List<UserData> userData;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDiagonosis() {
		return diagonosis;
	}
	public void setDiagonosis(String diagonosis) {
		this.diagonosis = diagonosis;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getActiveYN() {
		return activeYN;
	}
	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public List<UserData> getUserData() {
		return userData;
	}
	public void setUserData(List<UserData> userData) {
		this.userData = userData;
	}
	public void addUserData(UserData userData) {
		if(this.userData== null){
			this.userData = new ArrayList<UserData>();
		}
		this.userData.add(userData);
	}
	public String getCareOff() {
		return careOff;
	}
	public void setCareOff(String careOff) {
		this.careOff = careOff;
	}
	public String getInformer() {
		return informer;
	}
	public void setInformer(String informer) {
		this.informer = informer;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
