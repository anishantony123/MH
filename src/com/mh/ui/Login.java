package com.mh.ui;

import java.awt.Window;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import com.Main;

public class Login extends JInternalFrame{
	private LoginPanel login;
	private Main main;
	public Login(Main main){
		super("Login",false,true,false,true);
		this.main=main;
		login = new LoginPanel(main);
		getContentPane().add(login);
		setBounds(250, 250, 300, 180); 
		try {
			setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(true);
		setClosable(false);
		
	}

	public LoginPanel getLogin() {
		return login;
	}

	public void setLogin(LoginPanel login) {
		this.login = login;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void clear() {
		// TODO Auto-generated method stub
		login.getUserNameField().setText("");
		login.getPasswordField().setText("");
	}
	
	

}
