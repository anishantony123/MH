package com.mh.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.Main;
import com.mh.model.HMConstants;
import com.mh.service.AdminService;
import com.mh.ui.images.ImageUtil;

public class LoginPanel extends JPanel{
	private JTextField userNameField;
	private JPasswordField passwordField;
	private Main main;
	public LoginPanel(Main main) {
		this.main = main;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel userName = new JLabel("Username");
		GridBagConstraints gbc_userName = new GridBagConstraints();
		gbc_userName.insets = new Insets(0, 0, 5, 5);
		gbc_userName.gridx = 3;
		gbc_userName.gridy = 2;
		add(userName, gbc_userName);
		
		userNameField = new JTextField();
		userNameField.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.gridwidth = 2;
		gbc_userNameField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridx = 5;
		gbc_userNameField.gridy = 2;
		add(userNameField, gbc_userNameField);
		userNameField.setColumns(10);
		
		JLabel password = new JLabel("Password");
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.gridx = 3;
		gbc_password.gridy = 3;
		add(password, gbc_password);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Long id=-1l;
				if((id=new AdminService().validateAdmin(userNameField.getText(), new String(passwordField.getPassword())))>0l){
					Main.session.setUser(userNameField.getText());
					Main.session.setPassword(new String(passwordField.getPassword()));
					Main.session.setId(id);
					getMain().getLoginButton().setIcon(ImageUtil.getIcon(HMConstants.LOGOUT_IMG));
					getMain().getLoginButton().setActionCommand("Logout");
					getMain().getBtnSettings().setVisible(true);
					getMain().getBtnUsers().setEnabled(true);
					UsersPanel usersPanel = (UsersPanel) getMain().getSplitPane().getRightComponent();
					usersPanel.setTable(HMConstants.EDIT);
					usersPanel.getAddNewButton().setVisible(true);
					usersPanel.setMode(HMConstants.EDIT);
					getMain().getMainPanel().setVisible(true);
					getMain().getLogin().setVisible(false);
				}else{
					JOptionPane.showMessageDialog(LoginPanel.this,"Login failed !");
				}
				
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial",Font.BOLD,13));
		passwordField.setDropMode(DropMode.INSERT);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 5;
		gbc_passwordField.gridy = 3;
		add(passwordField, gbc_passwordField);
		GridBagConstraints gbc_login = new GridBagConstraints();
		gbc_login.insets = new Insets(0, 0, 5, 5);
		gbc_login.gridx = 5;
		gbc_login.gridy = 4;
		add(login, gbc_login);
		
		JButton cancelLogin = new JButton("Cancel");
		cancelLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getMain().getMainPanel().setVisible(true);
				getMain().getLogin().setVisible(false);
			}
		});
		GridBagConstraints gbc_cancelLogin = new GridBagConstraints();
		gbc_cancelLogin.anchor = GridBagConstraints.WEST;
		gbc_cancelLogin.insets = new Insets(0, 0, 5, 5);
		gbc_cancelLogin.gridx = 6;
		gbc_cancelLogin.gridy = 4;
		add(cancelLogin, gbc_cancelLogin);
		
		setMinimumSize(new Dimension(250, 80));
		setPreferredSize(new Dimension(389, 167));
		setMaximumSize(new Dimension(250, 80));
		
		setAlignmentX(TOP_ALIGNMENT);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 20, 20), getBorder()));
	}
	public JTextField getUserNameField() {
		return userNameField;
	}
	public void setUserNameField(JTextField userNameField) {
		this.userNameField = userNameField;
	}
	public JPasswordField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	

}
