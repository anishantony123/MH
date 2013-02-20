package com.mh.ui.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private String user;
	private String password;
	private Long id=-1l;
	private Map<String,Object> parameterMap=new HashMap<String,Object>();
	
	public void setParameter(String key,Object value){
		parameterMap.put(key, value);
	}
	public Object getParameter(String key){
		return parameterMap.get(key);
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isValid(){
		if(user!=null && !user.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	synchronized public void inValidateSession(){
		if(user!=null){
			user=null;
		}
		parameterMap.clear();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
