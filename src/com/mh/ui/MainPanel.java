package com.mh.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import com.Main;

public class MainPanel extends JPanel{
	private JPanel header;
	private JPanel content;
	private JPanel footer;
	private Main parent;
	
	/**
	 * @wbp.parser.constructor
	 */
	public MainPanel(Main parent) {
		this.parent=parent;
		setLayout(new BorderLayout(5,5));	
		
	}
	public MainPanel(Main parent,JPanel header,JPanel content,JPanel footer) {
		this.parent=parent;
		setLayout(new BorderLayout(5,5));	
		this.header = new JPanel();
		add(header,BorderLayout.NORTH);
		
		
		this.content = new JPanel();
		add(content,BorderLayout.CENTER);
		
		this.footer = new JPanel();
		add(footer,BorderLayout.SOUTH);
	}
	public JPanel getHeader() {
		return header;
	}
	public void setHeader(JPanel header) {
		this.header = header;
		this.header.setVisible(true);
		add(this.header,BorderLayout.NORTH);
	}
	public JPanel getFooter() {
		return footer;
	}
	public void setFooter(JPanel footer) {
		this.footer = footer;
		this.footer.setVisible(true);
		add(this.footer,BorderLayout.SOUTH);
	}
	public JPanel getContent() {
		return content;
	}
	public void setContent(JPanel content) {
		this.content = content;
		this.content.setVisible(true);
		add(this.content,BorderLayout.CENTER);
	}	
	
}
