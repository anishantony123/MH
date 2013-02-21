package com;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.apache.commons.io.FileDeleteStrategy;

import com.mh.model.HMConstants;
import com.mh.ui.AdvancedPanel;
import com.mh.ui.Login;
import com.mh.ui.MainPanel;
import com.mh.ui.SettingsPanel;
import com.mh.ui.SettingsTabbedPane;
import com.mh.ui.UsersPanel;
import com.mh.ui.images.ImageUtil;
import com.mh.ui.session.Session;
import com.mh.ui.validation.ValidationPane;


public class Main extends JFrame {
	
	JPanel panel_1;
	MainPanel mainPanel;
	JPanel header;
	JPanel content;
	JPanel footer;
	JSplitPane splitPane;
	JLayeredPane layeredPane;
	JPanel contentPanel;
	JPanel buttonPanel;
	ValidationPane validationPane;
	JButton loginButton;
	Login login;
	JDesktopPane desktop;
	JButton btnSettings;
	JButton btnUsers;
	public SettingsTabbedPane settings;
	SettingsPanel settingsPanel;
	AdvancedPanel advancedPanel;
	
	
	public static Session session=new Session();
	
	public Main() {
		try {
		login = new Login(this);
		
		 getContentPane().add(login);
		 login.setVisible(false);
		mainPanel = new MainPanel(this);
		header = new JPanel();		
			
		
		loginButton = new JButton(ImageUtil.getIcon(HMConstants.LOGIN_IMG));
		loginButton.setActionCommand("Login");
		loginButton.setContentAreaFilled(false);
		loginButton.setBorderPainted( false );
		
		//header.setSize(800, 400);
		header.setMinimumSize(new Dimension(800, 60));
		header.setPreferredSize(new Dimension(800, 60));
		header.setMaximumSize(new Dimension(800, 60));
		ImagePanel panel = new ImagePanel(ImageUtil.getIcon(HMConstants.HEADER).getImage());
		header.add(panel);
		
		/*header.setMaximumSize(new Dimension(Short.MAX_VALUE,200));
		header.add(Box.createRigidArea(new Dimension((int) (header.getPreferredSize().getWidth()-100),20)));
		header.add(Box.createHorizontalGlue());
		
		header.add(loginButton);	
		*/
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( arg0.getActionCommand().equals("Login")){					
					loginAction();
				}else{
					logoutAction();
				}
				
			}
		});
		//header.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray), header.getBorder()));
		
		mainPanel.setHeader(header);
		
		content =new JPanel();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		
		contentPanel = new UsersPanel(this);
		contentPanel.setMinimumSize(new Dimension(630, 450));
		contentPanel.setPreferredSize(new Dimension(630, 450));
		contentPanel.setMaximumSize(new Dimension(630,450));
		//contentPanel.setBackground(Color.WHITE);
		//contentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), contentPanel.getBorder()));
				
		
		splitPane.setRightComponent(contentPanel);
		splitPane.setContinuousLayout(true);
	    splitPane.setOneTouchExpandable(true);
	    splitPane.setDividerLocation(170);
		//contentPanel.setLayout(new CardLayout(0, 0));
		
		buttonPanel = new JPanel();
		splitPane.setLeftComponent(buttonPanel);
		//buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		//buttonPanel.setBackground(Color.RED);
		buttonPanel.setMinimumSize(new Dimension(170, 450));
		buttonPanel.setPreferredSize(new Dimension(170, 450));
		buttonPanel.setMaximumSize(new Dimension(170,Short.MAX_VALUE));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(100,50)));
		
		
		
	
		btnUsers = new JButton(ImageUtil.getIcon(HMConstants.PATIENTS_IMG));
		btnUsers.setContentAreaFilled(false);
		btnUsers.setBorderPainted( false );
		//btnUsers = new JButton("Patients");
		
		//btnUsers.setEnabled(false);
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//btnUsers.setEnabled(false);
				//btnSettings.setEnabled(true);
				splitPane.setRightComponent(contentPanel);
			}
		});
		buttonPanel.add(btnUsers);
		
		
		//settings
		
		settings = new SettingsTabbedPane();
		
		settingsPanel= new SettingsPanel(this);
		
		settingsPanel.setMinimumSize(new Dimension(600, 450));
		settingsPanel.setPreferredSize(new Dimension(650, 450));
		settingsPanel.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		settingsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50), contentPanel.getBorder()));
		
		advancedPanel = new AdvancedPanel(this);
		advancedPanel.setMinimumSize(new Dimension(600, 450));
		advancedPanel.setPreferredSize(new Dimension(650, 450));
		advancedPanel.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		advancedPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50), contentPanel.getBorder()));
	
		
		settings.add("General",settingsPanel);
		settings.add("Advanced",advancedPanel);
		
		btnSettings = new JButton(ImageUtil.getIcon(HMConstants.SETTINGS_IMG));
		btnSettings.setContentAreaFilled(false);
		btnSettings.setBorderPainted( false );
		//btnSettings = new JButton("Settings");
		btnSettings.setVisible(false);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//btnUsers.setEnabled(true);
				//btnSettings.setEnabled(false);
				settingsPanel.init();
				splitPane.setRightComponent(settings);
				settingsPanel.addClientValidationListener(validationPane);
				advancedPanel.addClientValidationListener(validationPane);
				
			}
		});
		
		buttonPanel.add(btnSettings);
		
		
		content.setMinimumSize(new Dimension(800, 500));
		content.setPreferredSize(new Dimension(800, 500));
		content.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		
		content.add(splitPane);
		mainPanel.setContent(content);
		
		

		footer = new JPanel();		
		mainPanel.setFooter(footer);
		footer.setMinimumSize(new Dimension(250, 10));
		footer.setPreferredSize(new Dimension(400, 20));
		footer.setMaximumSize(new Dimension(Short.MAX_VALUE,20));
		
		
		mainPanel.setVisible(true);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray), header.getBorder()));
		//getContentPane().setBackground(Color.RED);
		//getContentPane().setLayout(new GridLayout(1,3));
		getContentPane().add(mainPanel);
		
		validationPane = new ValidationPane(splitPane,this);
		setGlassPane(validationPane);
		//splitPane.setBackground(Color.BLUE);
		
		pack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JPanel getPanel_1() {
		return panel_1;
	}

	public void setPanel_1(JPanel panel_1) {
		this.panel_1 = panel_1;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JPanel getHeader() {
		return header;
	}

	public void setHeader(JPanel header) {
		this.header = header;
	}

	public JPanel getContent() {
		return content;
	}

	public void setContent(JPanel content) {
		this.content = content;
	}

	public JPanel getFooter() {
		return footer;
	}

	public void setFooter(JPanel footer) {
		this.footer = footer;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}
	
	public ValidationPane getValidationPane() {
		return validationPane;
	}

	public void setValidationPane(ValidationPane validationPane) {
		this.validationPane = validationPane;
	}

	public static void main(String[] a){
		ImageUtil.init();
		loadProperties();
		cleanDir(HMConstants.tempDirectoryPath);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
		
		
	}
	
	 private static void createAndShowGUI(){
		 Main main= new Main();
		 
 		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
 		int midHeight = (int)(dim.getHeight()/2)-300;
 		int midWidth = (int)(dim.getWidth()/2)-400;
 		//main.setSize(800,600);
 		main.setLayout(new BorderLayout());
 		
 		main.setMinimumSize(new Dimension(800,400));
 		main.setPreferredSize(new Dimension(800,400));
 		main.setMaximumSize(new Dimension(800,400));
 		
 		main.setLocation(midWidth, midHeight);
 		main.setResizable(false);
 		main.setVisible(true);
 		
	 }
	public static void loadProperties(){
		try{
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		HMConstants.CONNECTION_STRING = prop.getProperty("ConnectionString").toString();
		HMConstants.CONNECTION_USRNAME = prop.getProperty("DBUserName").toString();
		HMConstants.CONNECTION_PASSWORD = prop.getProperty("DBPassword").toString();
		HMConstants.tempDirectoryPath = prop.getProperty("tempDirectory").toString();
		}catch(Exception e){
			
		}
	}
	public static void cleanDir(String tempdirectorypath) {
		if(tempdirectorypath!=null && !tempdirectorypath.trim().equals("")){
			File f= new File(tempdirectorypath);
			File [] files = f.listFiles();
			int length =0;
			if(files!=null && (length=files.length) >0){
				for(int i=0;i<length;i++){
					try {
						FileDeleteStrategy.FORCE.delete(files[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						new JOptionPane().setVisible(true);
						e.printStackTrace();
					}
				}
			}
		}
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public JButton getBtnSettings() {
		return btnSettings;
	}

	public void setBtnSettings(JButton btnSettings) {
		this.btnSettings = btnSettings;
	}

	public JButton getBtnUsers() {
		return btnUsers;
	}
	public void setBtnUsers(JButton btnUsers) {
		this.btnUsers = btnUsers;
	}
	public void loginAction(){
		login.setVisible(true);
		mainPanel.setVisible(false);				
	}
	public void logoutAction(){
		session.inValidateSession();
		loginButton.setIcon(ImageUtil.getIcon(HMConstants.LOGIN_IMG));
		loginButton.setActionCommand("Login");
		btnUsers.setEnabled(false);
		btnSettings.setVisible(false);
		UsersPanel usersPanel = (UsersPanel)getSplitPane().getRightComponent();
		usersPanel.setTable(HMConstants.VIEW);
		usersPanel.setMode(HMConstants.VIEW);
		usersPanel.getAddNewButton().setVisible(false);
	}


}
class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}

