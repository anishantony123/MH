package com;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

// A class that will be used for all the JInternalFrame
// in real life all the JInternal frame will vary here, just to show the concept, 
// they are simply all the same:
// A Menu to close them
// A previous and a next button to switch which one will be displayed in the JDesktopPane

public class MyInternalFrame extends JInternalFrame implements ActionListener, InternalFrameListener {

	// the JDesktopPane I belong to
	private MyDesktop father;
	private JMenuItem close;
	// buttons to show the previous/next Internal frame
	private JButton previous, next;
	// my Id so my JDesttopPane can identify me
	private int id;
	// the JPanel who's background will change to show which one has the focus
	private JPanel p;

	MyInternalFrame(MyDesktop father, int id)  {
		// make it with all option available
		super("Frame #" + (id+1), true, true, true, true);
		// save who I will call back when a button is click
		this.father = father;
		this.id = id;
		// add the JMenuBar
		JMenuBar mb = new JMenuBar();
		JMenu me = new JMenu("Menu");       // with one pull down menu
		close = new JMenuItem("Close");     // containing "close"
		me.add(close);
		close.addActionListener(this);		// call me back when clicked
		mb.add(me);
		setJMenuBar(mb);
		// a JPanel with the two buttons
		p = new JPanel();
		if(id == 0)
			p.setBackground(Color.YELLOW);
		else
			p.setBackground(Color.LIGHT_GRAY);
		
		previous = new JButton("Previous");
		previous.addActionListener(this);   // call me back when clicked
		p.add(previous);
		next = new JButton("Next");
		next.addActionListener(this);		// call me back when clicked
		p.add(next);
		add(p);
		// listener to receive activate/desctivate events
		addInternalFrameListener(this);
		setVisible(true);
	}

	// returns my Id
	int getId() {
		return id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// first test if it is one of the two buttons
		if(o == next) {
			father.next(id);
			return;
		}
		if(o == previous) {
			father.previous(id);
			return;
		}
		
		// so it is the close menu 
		father.close(id);
		dispose();

	}

	// to handle InternalFrameEvent
	// we change the JPanel background color when we become the active InternalFrame or not
	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		p.setBackground(Color.YELLOW);
	}
	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		p.setBackground(Color.LIGHT_GRAY);
	}
	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
		father.close(id);   // inform my father I do not exist anymore
	}
	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) {
	}
	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
	}
	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
	}
	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
	}
}

