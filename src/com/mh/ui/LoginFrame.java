package com.mh.ui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LoginFrame extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	public LoginFrame() {
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		getContentPane().add(internalFrame, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial",Font.BOLD,13));
		internalFrame.getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSjdflshdfdsf = new JLabel("sjdflshdfdsf");
		panel.add(lblSjdflshdfdsf);
		internalFrame.setVisible(true);
		
		pack();
	}

}
