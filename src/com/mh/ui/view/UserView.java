package com.mh.ui.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.Main;
import com.mh.model.HMConstants;
import com.mh.model.User;
import com.mh.ui.validation.ValidationMsg;
import com.mh.ui.validation.Validator;
import com.mh.ui.validation.annotations.Required;
import com.mh.ui.validation.event.ValidationEvent;
import com.mh.ui.validation.listener.ValidationListener;
//import com.toedter.calendar.JDateChooser;

public class UserView extends JPanel{
	private User user=null;
	protected String operation=HMConstants.ADD;  
	@Required(message="Name can't be empty",target="name")
	protected JTextField name;
	@Required(message="Reg No. can't be empty",target="regNo")
	protected JTextField regNo;
	protected JComboBox age;
	//protected JDateChooser dob;
	protected JTextField diagonosis;
	protected JTextField center;
	protected JTextField careOff;
	protected JTextField informer;
	//protected JTextArea address2;	
	@Required(message="Address1 can't be empty",target="address1")
	protected JTextField address1;	
	//protected JScrollPane saddress1;
	protected JTextField city;
	protected JTextField district;
	protected JTextField state;
	//protected JScrollPane saddress2;
	protected JComboBox sex;
	
	protected boolean hasErrors=false;
	protected List<ValidationMsg> resultList;
	protected List<ValidationListener> validationRegistry;
	protected JTextField pin;
	
	public UserView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 10, 50, 100,50, 100, 10,10,10, 10, 10, 100,10};
		gridBagLayout.rowHeights = new int[]{56, 20, 20, 0, 20, 20, 20, 20, 20, 20, 20, 20, 20,20,20};
		gridBagLayout.columnWeights = new double[]{0.0,0.0,0.0, 0.0, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		name = new JTextField();
		name.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.gridwidth = 2;
		gbc_name.anchor = GridBagConstraints.NORTH;
		gbc_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.gridx = 3;
		gbc_name.gridy = 1;
		add(name, gbc_name);
		name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Reg No.");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		regNo = new JTextField();
		regNo.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_regNo = new GridBagConstraints();
		gbc_regNo.gridwidth = 1;
		gbc_regNo.anchor = GridBagConstraints.NORTH;
		gbc_regNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_regNo.insets = new Insets(0, 0, 5, 5);
		gbc_regNo.gridx = 3;
		gbc_regNo.gridy = 2;
		add(regNo, gbc_regNo);
		regNo.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex");
		
		GridBagConstraints gbc_lblSex = new GridBagConstraints();
		gbc_lblSex.anchor = GridBagConstraints.WEST;
		gbc_lblSex.insets = new Insets(0, 0, 5, 5);
		gbc_lblSex.gridx = 1;
		gbc_lblSex.gridy = 3;
		add(lblSex, gbc_lblSex);
		
		sex= new JComboBox();
		sex.setFont(new Font("Arial",Font.BOLD,13));
		sex.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		sex.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 3;
		add(sex, gbc_comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("age");
		//JLabel lblNewLabel_2 = new JLabel("DOB");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 4;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		/*
		dob = new JDateChooser();
		dob.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_age = new GridBagConstraints();
		gbc_age.anchor = GridBagConstraints.NORTH;
		gbc_age.fill = GridBagConstraints.HORIZONTAL;
		gbc_age.insets = new Insets(0, 0, 5, 5);
		gbc_age.gridx = 3;
		gbc_age.gridy = 4;
		add(dob, gbc_age);*/
		
		age = new JComboBox();
		age.setFont(new Font("Arial",Font.BOLD,13));
		for(int i=1;i<=100;i++){
			age.addItem(new Integer(i));
		}
		GridBagConstraints gbc_age = new GridBagConstraints();
		gbc_age.anchor = GridBagConstraints.NORTH;
		gbc_age.fill = GridBagConstraints.HORIZONTAL;
		gbc_age.insets = new Insets(0, 0, 5, 5);
		gbc_age.gridx = 3;
		gbc_age.gridy = 4;
		add(age, gbc_age);
		
		JLabel lblNewLabel_3 = new JLabel("Address");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 5;
		add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		address1 = new JTextField();
		///saddress1 = new JScrollPane(address1); 
		address1.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_address1 = new GridBagConstraints();
		gbc_address1.gridwidth = 1;
		gbc_address1.gridheight = 1;
		gbc_address1.fill = GridBagConstraints.BOTH;
		gbc_address1.insets = new Insets(0, 0, 5, 5);
		gbc_address1.gridx = 3;
		gbc_address1.gridy = 5;
		add(address1, gbc_address1);
		
		JLabel lblNewLabel_31 = new JLabel("City");
		GridBagConstraints gbc_lblNewLabel_31 = new GridBagConstraints();
		gbc_lblNewLabel_31.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_31.gridx = 4;
		gbc_lblNewLabel_31.gridy = 5;
		add(lblNewLabel_31, gbc_lblNewLabel_31);
		
		city = new JTextField();
		city.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_city = new GridBagConstraints();
		gbc_city.gridwidth = 1;
		gbc_city.anchor = GridBagConstraints.NORTH;
		gbc_city.fill = GridBagConstraints.HORIZONTAL;
		gbc_city.insets = new Insets(0, 0, 5, 5);
		gbc_city.gridx = 5;
		gbc_city.gridy = 5;
		add(city, gbc_city);
		
		JLabel lblNewLabel_32 = new JLabel("District");
		GridBagConstraints gbc_lblNewLabel_32 = new GridBagConstraints();
		gbc_lblNewLabel_32.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_32.gridx = 1;
		gbc_lblNewLabel_31.gridy = 6;
		add(lblNewLabel_32, gbc_lblNewLabel_32);
		
		district = new JTextField();
		district.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_district = new GridBagConstraints();
		gbc_district.gridwidth = 1;
		gbc_district.anchor = GridBagConstraints.NORTH;
		gbc_district.fill = GridBagConstraints.HORIZONTAL;
		gbc_district.insets = new Insets(0, 0, 5, 5);
		gbc_district.gridx = 3;
		gbc_district.gridy = 6;
		add(district, gbc_district);
		
		JLabel lblNewLabel_33 = new JLabel("State");
		GridBagConstraints gbc_lblNewLabel_33 = new GridBagConstraints();
		gbc_lblNewLabel_33.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_33.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_33.gridx = 1;
		gbc_lblNewLabel_33.gridy = 7;
		add(lblNewLabel_33, gbc_lblNewLabel_33);
		
		state = new JTextField();
		state.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_state = new GridBagConstraints();
		gbc_state.anchor = GridBagConstraints.NORTH;
		gbc_state.fill = GridBagConstraints.HORIZONTAL;
		gbc_state.insets = new Insets(0, 0, 5, 5);
		gbc_state.gridx = 3;
		gbc_state.gridy = 7;
		add(state, gbc_state);
		
		JLabel lblNewLabel_43 = new JLabel("Pincode");
		GridBagConstraints gbc_lblNewLabel_43 = new GridBagConstraints();
		gbc_lblNewLabel_43.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_43.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_43.gridx = 4;
		gbc_lblNewLabel_43.gridy = 7;
		add(lblNewLabel_43, gbc_lblNewLabel_43);
		
		pin = new JTextField();
		pin.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_pin = new GridBagConstraints();
		gbc_pin.anchor = GridBagConstraints.NORTH;
		gbc_pin.fill = GridBagConstraints.HORIZONTAL;
		gbc_pin.insets = new Insets(0, 0, 5, 5);
		gbc_pin.gridx = 5;
		gbc_pin.gridy = 7;
		add(pin, gbc_pin);
		/*JLabel lblNewLabel_4 = new JLabel("Address2");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 6;
		add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		address2 = new JTextArea();
		saddress2 = new JScrollPane(address2);
		address2.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_address2 = new GridBagConstraints();
		gbc_address2.gridwidth = 2;
		gbc_address2.fill = GridBagConstraints.BOTH;
		gbc_address2.insets = new Insets(0, 0, 5, 5);
		gbc_address2.gridx = 3;
		gbc_address2.gridy = 6;
		add(saddress2, gbc_address2);*/
		
		JLabel lblNewLabel_4 = new JLabel("Care of");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 8;
		add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		careOff = new JTextField();
		careOff.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_careoff = new GridBagConstraints();
		gbc_careoff.gridwidth = 2;
		gbc_careoff.fill = GridBagConstraints.HORIZONTAL;
		gbc_careoff.insets = new Insets(0, 0, 5, 5);
		gbc_careoff.gridx = 3;
		gbc_careoff.gridy = 8;
		add(careOff, gbc_careoff);
		
		JLabel lblNewLabel_5 = new JLabel("Diagonosis");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 9;
		add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		diagonosis = new JTextField();
		diagonosis.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_diagonosis = new GridBagConstraints();
		gbc_diagonosis.gridwidth = 2;
		gbc_diagonosis.anchor = GridBagConstraints.NORTH;
		gbc_diagonosis.fill = GridBagConstraints.HORIZONTAL;
		gbc_diagonosis.insets = new Insets(0, 0, 5, 5);
		gbc_diagonosis.gridx = 3;
		gbc_diagonosis.gridy = 9;
		add(diagonosis, gbc_diagonosis);
		diagonosis.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Center");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 10;
		add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		center = new JTextField();
		center.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_center = new GridBagConstraints();
		gbc_center.gridwidth = 2;
		gbc_center.insets = new Insets(0, 0, 5, 5);
		gbc_center.anchor = GridBagConstraints.NORTH;
		gbc_center.fill = GridBagConstraints.HORIZONTAL;
		gbc_center.gridx = 3;
		gbc_center.gridy = 10;
		add(center, gbc_center);
		center.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Informer");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 11;
		add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		informer = new JTextField();
		informer.setFont(new Font("Arial",Font.BOLD,13));
		GridBagConstraints gbc_informer = new GridBagConstraints();
		gbc_informer.gridwidth = 2;
		gbc_informer.insets = new Insets(0, 0, 5, 5);
		gbc_informer.anchor = GridBagConstraints.NORTH;
		gbc_informer.fill = GridBagConstraints.HORIZONTAL;
		gbc_informer.gridx = 3;
		gbc_informer.gridy = 11;
		add(informer, gbc_informer);
		informer.setColumns(10);
		
	}
	public User getUser() {
		//validateThis();
		if(this.user==null && operation.equals(HMConstants.ADD)){
			this.user = new User();
			user.setCreatedDate(new Date());
			user.setCreatedBy((Main.session.getId()>0l)?Main.session.getId():1l);
		}else{
			user.setModifiedDate(new Date());
			user.setModifiedBy((Main.session.getId()>0l)?Main.session.getId():1l);
		}
		user.setAddress1(address1.getText());
		user.setCity(city.getText());
		user.setDistrict(district.getText());
		user.setState(state.getText());
		user.setPincode(pin.getText());
		user.setName(name.getText());
		int age01 = (age.getSelectedItem()!=null && !age.getSelectedItem().toString().equals(""))?Integer.parseInt(age.getSelectedItem().toString()):0;
				
		//user.setDob(dob.getDate());
		if(age01>0){
			user.setAge(age01);
		}
				
		user.setCenter(center.getText());
		user.setDiagonosis(diagonosis.getText());
		user.setRegNo(regNo.getText());
		user.setSex(sex.getSelectedItem().toString());
		user.setCareOff(careOff.getText());
		user.setInformer(informer.getText());
		return user;
	}
	protected boolean validateThis() {
		getUser();
		if(resultList == null){
			resultList=new ArrayList<ValidationMsg>();
		}else{
			resultList.clear();
		}
		 
		hasErrors = Validator.validateSuper(this,resultList);
		//update registered listeners
		if(validationRegistry!=null){
			for(ValidationListener listener:validationRegistry){
				ValidationEvent vEvent =  new ValidationEvent(); 
				vEvent.setValid(!hasErrors);
				vEvent.setResultList(resultList);
				vEvent.setParent(this);
				if(hasErrors){
					listener.onFailure(vEvent);
				}else{
					listener.onSuccess(vEvent);
				}
			}
		}
		repaint();
		return hasErrors;
	}
	public void setUser(User user) {
		this.user = user;
		if(this.user!=null){
			name.setText(user.getName());
			regNo.setText(user.getRegNo());
			age.setSelectedItem(user.getAge());
			//dob.setDate(user.getDob());
			diagonosis.setText(user.getDiagonosis());
			center.setText(user.getCenter());
			address1.setText(user.getAddress1());
			city.setText(user.getCity());
			district.setText(user.getDistrict());
			state.setText(user.getState());
			pin.setText(user.getPincode());
			center.setText(user.getCenter());
			diagonosis.setText(user.getDiagonosis());
			sex.setSelectedItem(user.getSex());
			careOff.setText(user.getCareOff());
			informer.setText(user.getInformer());
		}
		
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
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
