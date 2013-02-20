package com.mh.ui;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javax.swing.*;
public class ViewerComponent extends WindowAdapter{
	SwingController controller;
	 public void viewFile(String filePath) {
	        
	        controller = new SwingController();

	        SwingViewBuilder factory = new SwingViewBuilder(controller);

	        JPanel viewerComponentPanel = factory.buildViewerPanel();

	        // add interactive mouse link annotation support via callback
	        controller.getDocumentViewController().setAnnotationCallback(
	                new org.icepdf.ri.common.MyAnnotationCallback(
	                        controller.getDocumentViewController()));

	        JFrame applicationFrame = new JFrame();
	        applicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        applicationFrame.getContentPane().add(viewerComponentPanel);

	        // Now that the GUI is all in place, we can try openning a PDF
	        controller.openDocument(filePath);

	        // show the component
	        applicationFrame.pack();
	        applicationFrame.setVisible(true);
	    }
	 public void windowClosed(WindowEvent e){
		 if(controller!=null){
			 controller.closeDocument();
			 controller.dispose();
		 }
	 }

}
