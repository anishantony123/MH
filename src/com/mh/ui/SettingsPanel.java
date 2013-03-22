package com.mh.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.Main;
import com.mh.model.Admin;
import com.mh.model.HMConstants;
import com.mh.service.AdminService;
import com.mh.ui.images.ImageUtil;
import com.mh.ui.validation.ValidationMsg;
import com.mh.ui.validation.Validator;
import com.mh.ui.validation.annotations.Compare;
import com.mh.ui.validation.annotations.Required;
import com.mh.ui.validation.event.ValidationEvent;
import com.mh.ui.validation.listener.ValidationListener;

public class SettingsPanel extends JPanel{
	@Required(message="Username can't be empty",target="userField")
	private JTextField userField;
	@Required(message="NewPassword can't be empty",target="passwordField")	
	private JPasswordField passwordField;
	@Required(message="Confirm password can't be empty",target="confirmPwd")	
	@Compare(compareTo="passwordField",message="Confirm Password not matched!")
	private JPasswordField confirmPwd;
	@Required(message="Current password can't be empty",target="currentPwd")	
	@Compare(compareTo="session",message="Current Password not matched!")
	private JPasswordField currentPwd;
	Main main;
	JComboBox comboBox;
	JLabel lblCurrentPassword;
	JLabel lblPassword;
	String mode="0";
	
	protected boolean hasErrors=false;
	protected List<ValidationMsg> resultList;
	List<ValidationListener> validationRegistry;
	
	public SettingsPanel(Main main) {
		try {
		this.main = main;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Arial",Font.BOLD,13));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString().equals("Edit Me")){
					userField.setText(Main.session.getUser());
					userField.setEditable(false);
					passwordField.setVisible(true);
					lblCurrentPassword.setVisible(true);
					currentPwd.setVisible(true);
					lblPassword.setText("New Password");
					mode="0";
				}else{
					userField.setEditable(true);
					userField.setText("");
					currentPwd.setVisible(false);
					currentPwd.setText(Main.session.getPassword());
					lblCurrentPassword.setVisible(false);
					lblPassword.setText("Password");
					mode="1";
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Edit Me", "Add New"}));
		comboBox.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 4;
		add(lblUsername, gbc_lblUsername);
		
		userField = new JTextField();
		userField.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 4;
		add(userField, gbc_textField);
		userField.setColumns(10);
		userField.setEditable(false);
		
		lblCurrentPassword = new JLabel("Current Password");
		GridBagConstraints gbc_lblCurrentPassword = new GridBagConstraints();
		gbc_lblCurrentPassword.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentPassword.gridx = 2;
		gbc_lblCurrentPassword.gridy = 5;
		add(lblCurrentPassword, gbc_lblCurrentPassword);
		
		currentPwd = new JPasswordField();
		currentPwd.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_passwordField_2 = new GridBagConstraints();
		gbc_passwordField_2.gridwidth = 3;
		gbc_passwordField_2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_2.gridx = 4;
		gbc_passwordField_2.gridy = 5;
		add(currentPwd, gbc_passwordField_2);
		
		lblPassword = new JLabel("New Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 6;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 6;
		add(passwordField, gbc_passwordField);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 2;
		gbc_lblConfirmPassword.gridy = 7;
		add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		confirmPwd = new JPasswordField();
		confirmPwd.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.gridwidth = 3;
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 4;
		gbc_passwordField_1.gridy = 7;
		add(confirmPwd, gbc_passwordField_1);
		
		//JButton btnSave = new JButton("Save");
		JButton btnSave = new JButton(ImageUtil.getIcon(HMConstants.SAVE_IMG));
		btnSave.setContentAreaFilled(false);
		btnSave.setBorderPainted( false );
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!validateThis()){
					Admin admin =new Admin();
					admin.setUsername(userField.getText());
					admin.setPassword(new String(passwordField.getPassword()));
					String msg = "Updated";
					if(mode.equals("0")){//edit me
						new AdminService().updateAdminByName(admin);
						Main.session.setPassword(admin.getPassword());
					}else{
						new AdminService().save(admin);
						msg ="Saved";
					}
					JOptionPane.showMessageDialog(SettingsPanel.this, msg+" successfully!");
					reset();
				}
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 4;
		gbc_btnSave.gridy = 9;
		add(btnSave, gbc_btnSave);
		
		
		//JButton btnCancel = new JButton("Cancel");
		JButton btnCancel = new JButton(ImageUtil.getIcon(HMConstants.CANCEL_IMG));
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted( false );
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 6;
		gbc_btnCancel.gridy = 9;
		add(btnCancel, gbc_btnCancel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init(){
		userField.setText(Main.session.getUser());
	}
	private void reset(){
		if(userField.isEditable()){
			userField.setText("");
		}
		passwordField.setText("");
		confirmPwd.setText("");
	}
	protected boolean validateThis() {
		if(resultList == null){
			resultList=new ArrayList<ValidationMsg>();
		}else{
			resultList.clear();
		}
		 
		hasErrors = Validator.validate(this,resultList);
		
		//update registered listeners
		if(validationRegistry!=null){
			for(ValidationListener listener:validationRegistry){
				ValidationEvent vEvent =  new ValidationEvent(); 
				vEvent.setValid(!hasErrors);
				vEvent.setResultList(resultList);
				vEvent.setParent(this.main.settings);
				vEvent.setDx(40);
				vEvent.setDy(20);
				if(hasErrors){
					listener.onFailure(vEvent);
				}else{
					listener.onSuccess(vEvent);
				}
			}
		}
		repaint();
		if(!hasErrors){//compare
			hasErrors = Validator.comparePassword(this,resultList);
			
			//update registered listeners
			if(validationRegistry!=null){
				for(ValidationListener listener:validationRegistry){
					ValidationEvent vEvent =  new ValidationEvent(); 
					vEvent.setValid(!hasErrors);
					vEvent.setResultList(resultList);
					vEvent.setDx(40);
					vEvent.setDy(20);
					vEvent.setParent(this.main.settings);
					if(hasErrors){
						listener.onFailure(vEvent);
					}else{
						listener.onSuccess(vEvent);
					}
				}
			}
			repaint();
		}
		return hasErrors;
	}
	public void addClientValidationListener(Component glassPane) {
		// TODO Auto-generated method stub
		if(validationRegistry==null){
			validationRegistry = new ArrayList<ValidationListener>();
		}
		if(!validationRegistry.contains(glassPane)){
			validationRegistry.add((ValidationListener) glassPane);
		}
		
	}
}
