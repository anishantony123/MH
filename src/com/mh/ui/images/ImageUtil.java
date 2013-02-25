package com.mh.ui.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.Main;

public class ImageUtil {
	public static BufferedImage logoutImg;
	public static BufferedImage loginImg; 
	public static BufferedImage patientsImg;
	public static BufferedImage settingsImg;
	public static BufferedImage addnewImg;
	public static BufferedImage searchImg;
	public static BufferedImage cancelImg;
	public static BufferedImage saveImg;
	public static BufferedImage uploadImg;
	public static BufferedImage viewImg;
	public static BufferedImage editIcon;
	public static BufferedImage viewIcon;
	public static BufferedImage header;
	public static BufferedImage deleteIcon;
	public static BufferedImage refreshIcon;
	public static void init(){
		try {
			loginImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/login.png"));	
			logoutImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/logout.png"));			
			patientsImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/patients.png"));
			settingsImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Settings.png"));
			addnewImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/New.png"));
			searchImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Search.png"));
			cancelImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Cancel.png"));
			saveImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Save.png"));
			uploadImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Upload.png"));
			viewImg = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/View.png"));
			editIcon = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/EditIcon.png"));
			viewIcon = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/ViewIcon.png"));
			header = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/hospital.png"));
			deleteIcon = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/delete.png"));
			refreshIcon = ImageIO.read(ImageUtil.class.getClassLoader().getResource("com/mh/ui/images/Refresh.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static ImageIcon getIcon(String img){
		ImageIcon icon= null;
		try {
			Field imageField= Class.forName("com.mh.ui.images.ImageUtil").getField(img);
			imageField.setAccessible(true);
			if(imageField.get(null)!=null){
				icon = new ImageIcon((BufferedImage) imageField.get(null));
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
		
	}

}
