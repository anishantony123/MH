package com.mh.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.Main;
import com.mh.model.HMConstants;
import com.mh.model.UserData;
import com.mh.service.PDFServices;
import com.mh.service.UserService;
import com.mh.ui.images.ImageUtil;
import com.mh.ui.util.Utils;
import com.mh.ui.view.UserView;

public class AddUserPanel extends UserView{
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnView;
	private JButton btnUpload;
	private UsersPanel usersPanel;
	private Main main;
	JOptionPane progressPane;
	JProgressBar progressBar = new JProgressBar();
	public Double timer = 0.0;
	JDialog dialog = new JDialog();
	private Boolean done=false;

	
	
	public AddUserPanel(Main main, Component component) {
		super();
		try {
		this.main = main;
		usersPanel = (UsersPanel) component;
		//address2.setAutoscrolls(true);
		address1.setAutoscrolls(true);
		JLabel lblNewLabel_1 = new JLabel("Attachment");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 12;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		btnView = new JButton(ImageUtil.getIcon(HMConstants.VIEW_IMG));
		btnView.setContentAreaFilled(false);
		btnView.setBorderPainted( false );
		//btnView = new JButton("view");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserService userService =new UserService();
				String path =userService.viewUserDataFiles(getUser().getId());
				String filePath=new PDFServices().mergeFiles(HMConstants.tempDirectoryPath+File.separator+path);
				
				new ViewerComponent().viewFile(filePath);
			}
		});
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnView.insets = new Insets(0, 0, 5, 5);
		gbc_btnView.gridx = 3;
		gbc_btnView.gridy = 12;
		add(btnView, gbc_btnView);
		
		
		btnUpload = new JButton(ImageUtil.getIcon(HMConstants.UPLOAD_IMG));
		btnUpload.setContentAreaFilled(false);
		btnUpload.setBorderPainted( false );
		//btnUpload = new JButton("upload");
		
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File[] choosedFileArray=getFileInfoFromChooser();
				if(choosedFileArray!=null){
				for(int i=0;i<choosedFileArray.length;i++){
				String imagePath = choosedFileArray[i].getPath();
				if(imagePath!=null){
					UserData userData =new UserData();
					List<String> files= new ArrayList<String>();
					files.add(imagePath);
					userData.setFilePath(files);
										
					getUser().addUserData(userData);
					
				}
			}}
			}
			
			
			private File[] getFileInfoFromChooser() {
				File[] result = null;
				JFileChooser chooser = new JFileChooser();
				 FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg","png","bmp","gif");
				 chooser.addChoosableFileFilter(filter);
				chooser.setCurrentDirectory(new File(getCurrentDirectory()));
				chooser.setMultiSelectionEnabled(true);
				int option = chooser.showSaveDialog(AddUserPanel.this.main);
				if (option == JFileChooser.APPROVE_OPTION) {
					result = chooser.getSelectedFiles();
					setCurrentDirectory(chooser.getCurrentDirectory().getAbsolutePath().toString());
				} else {
					
					
				}

				return result;
			}
			private void setCurrentDirectory(String path) {
				try{
					Properties prop = new Properties();
					prop.load(new FileInputStream("config.properties"));
					prop.setProperty("ChooserDirectory", path);
					prop.store(new FileOutputStream("config.properties"), null);
				}catch(Exception ee)
				{System.out.println(ee);}
				
			}


			private String getCurrentDirectory(){
				String resultPath = null;
				try{
					Properties prop = new Properties();
					prop.load(new FileInputStream("config.properties"));
					resultPath = prop.getProperty("ChooserDirectory").toString();
				}catch(Exception ee)
				{System.out.println(ee);}
				
				return resultPath;

			}

		});
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpload.gridwidth = 2;
		gbc_btnUpload.gridx = 4;
		gbc_btnUpload.gridy = 12;
		add(btnUpload, gbc_btnUpload);
		
		
		
		btnSave = new JButton(ImageUtil.getIcon(HMConstants.SAVE_IMG));
		btnSave.setContentAreaFilled(false);
		btnSave.setBorderPainted( false );
		//btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!validateThis()){
					done = false;
					timer =0.0;				
					
				    progressBar.setValue(timer.intValue());
				    progressBar.setStringPainted(true);
				    Border border = BorderFactory.createTitledBorder("Saving...");
				    progressBar.setBorder(border);
				    progressBar.setOpaque(true);
				    progressBar.paintImmediately(progressBar.getBounds());
				    progressBar.setVisible(true);
				    
				    
				    
				    dialog.add(progressBar);
				    dialog.setUndecorated(true);
				    dialog.setResizable(false);
				    dialog.setMinimumSize(new Dimension(200, 50));
				    dialog.setPreferredSize(new Dimension(200, 50));
				    dialog.setMaximumSize(new Dimension(200,Short.MAX_VALUE));
				    dialog.setVisible(true);
					/**
					 * added 2 independent threads , first for showing progress bar 
					 * second for save
					 */
					
				    new Thread(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							while(timer<=100 && !done){
								dialog.setLocationRelativeTo(AddUserPanel.this);
								dialog.setAlwaysOnTop(true);
								progressBar.setValue(timer.intValue());
								waitTill(300);
							}
							if(done){
								progressBar.setValue(100);
								waitTill(400);
							}
							dialog.setVisible(false);
						}

						public void waitTill(int time) {
							try {
								Thread.sleep(time);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}.start();
					
					
					
					  new Thread(){

							@Override
							public void run() {
								done = new UserService().save(getUser(),AddUserPanel.this);
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								usersPanel.setTable(getOperation());
								getMain().getSplitPane().setRightComponent(usersPanel);
							}
					  }.start();
					
				}
				
				
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 13;
		add(btnSave, gbc_btnSave);
		
		if(usersPanel.getMode().equals(HMConstants.VIEW)){
			btnUpload.setVisible(false);
			btnSave.setEnabled(false);
		}
		
		btnCancel = new JButton(ImageUtil.getIcon(HMConstants.CANCEL_IMG));
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted( false );
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usersPanel.setTable(getOperation());
				getMain().getSplitPane().setRightComponent(usersPanel);
				
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridwidth = 2;
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 13;
		add(btnCancel, gbc_btnCancel);
		
		GridBagConstraints gbc_progressBar= Utils.getConStraints(3, 0, new Insets(0, 0, 5, 5));
		gbc_progressBar.gridwidth=3;
		gbc_progressBar.gridheight=1;
		
		progressPane = new JOptionPane();
		
		
		
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(650, 500));
		setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		//for validation
		addClientValidationListener(main.getGlassPane());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}
	
}
