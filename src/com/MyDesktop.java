package com;
import javax.swing.*;

import java.util.*;      // for the ArrayList

public class MyDesktop extends JDesktopPane {

	// a JDesktopPane
	// it contains an array of 10 JInternal frame that it will create
	// it has simply 3 methods:
	// one to give focus to the previous JInternalFrame
	// one one to give focus to the next one
	// one to remove a JInternal frame that is closed from the list
	
	// to keep a list of the InternalFrame that I display
	ArrayList<MyInternalFrame> al;
	
	MyDesktop() {
		// create 10 InternalFrame
		al = new ArrayList<MyInternalFrame>();
		for(int i = 0; i < 10; i++) {
			MyInternalFrame mif = new MyInternalFrame(this, i);
			// put them in cascade
			mif.setBounds(i * 30, i * 30, 200, 100);
			add(mif);				// add to the desktop frame
			al.add(mif);			// add to my list
		}
		// make the fist one as the active one
		try {
			al.get(0).setSelected(true);
		}
		catch(Exception e) {
			// nothing I can do
		}

	}
	
	// called to show next InternalFrame
	void next(int id) {
		nextPrevious(id, +1);   // calls common private method which use a delat +1 or -1
	}
	// called to show the prrevious InternalFrame
	void previous(int id) {
		nextPrevious(id, -1);
	}
	// called internally for next or previous
	private void nextPrevious(int id, int delta) {
		// if no more frame (the arraylist contains only that internal frame)
		int len = al.size();
		if(len <= 1)
			return;
		int i = 0;
		// identify the Internaml Frame that did the call
		for(; i < len; ++i) {
			MyInternalFrame issuer = al.get(i);
			if(issuer.getId() == id)
				break;
		}
		// next of previous
		i += delta;
		// wrap around
		if(i < 0)
			i = len - 1;
		else if(i == len)
			i = 0;
		
		// give focus to that one
		try {
			MyInternalFrame issuer = al.get(i);
			// if it is iconified
			if(issuer.isIcon()) {
				issuer.setIcon(false);   // restore it
			}
			issuer.setSelected(true);
		}
		catch(Exception e) {
			// nothing I can do
		}
	}
	
	// an InternalFrame closes
	void close(int id) {
		// identify the Internaml Frame that did the call
		for(int i = 0; i < al.size(); ++i) {
			MyInternalFrame issuer = al.get(i);
			// and remove it
			if(issuer.getId() == id) {
				al.remove(i);
				break;
			}
		}
		
	}
	
	// to start the whole application
	public static void main(String[] args) {
		// build a JFrame
		JFrame frame = new JFrame("My Desktop Pane");
		// add a JDesktopPane into it
		JDesktopPane jdp = new MyDesktop();
		frame.add(jdp);
		frame.add(new JLabel("SDFffffffffffsdfsdfsdf sdfsdfsdfsdfsdfsd"));
		// give the JFrame a Size and make it visible
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

