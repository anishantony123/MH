package com.mh.ui.validation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.Main;
import com.mh.ui.validation.event.ValidationEvent;
import com.mh.ui.validation.listener.ClientValidationListener;


public class ValidationPane extends  JComponent implements ClientValidationListener{

	private ValidationEvent vEvent;
	private Main main;
	
	
	protected void paintComponent(Graphics g) {
		if(vEvent!=null){
			Graphics2D  g2d = (Graphics2D) g; 
			if(!vEvent.isValid()){ 	          
	           int tx =vEvent.getParent().getX()+main.getContent().getX()+30+vEvent.getDx();
	           int ty =vEvent.getParent().getY()+main.getContent().getY()-10+vEvent.getDy();
	        	for(ValidationMsg valid:vEvent.getResultList()){
	        		int length = valid.getMsg().length()*(int)(getFont().getSize2D()/1.7);
	        		int height = (int) (1.4*getFont().getSize());
	        		int []x ={ 0,2, length-2,length, length,length-2, 10, 7, 7, 2,0};
	        		int []y ={2,0, 0, 2,height-2, height, height,height+7, height, height, height-2};
	        		for(int i=0;i<x.length;i++){
	        			x[i]+=(tx+valid.getX());
	        			y[i]+=(ty+valid.getY());
	        		}
	        		g2d.setColor(Color.gray);   			
	        		g2d.fillPolygon(x,y,x.length);
	        		g2d.setColor(Color.white);
	        		g2d.drawString(valid.getMsg(), (int)(tx+valid.getX()+10), (int)(ty+valid.getY()+10));
	        		
	        	}        	
	        
	        }else{
	        	g2d.clearRect(0, 0, vEvent.getParent().getWidth(), vEvent.getParent().getHeight());
	        }
		}
		
    }

   
    public ValidationPane(JSplitPane splitPane,Main main) {
    	ChangeListener listener = new ChangeListener(splitPane,this, main.getContentPane());
        addMouseListener(listener);        
        addMouseMotionListener(listener);
        this.main=main;
        setVisible(false);
    }
    
    class ChangeListener extends MouseInputAdapter {
        Toolkit toolkit;
        JSplitPane parent;
        ValidationPane glassPane;
        Container contentPane;

        public ChangeListener(Component parent,ValidationPane glassPane, Container contentPane) {
            toolkit = Toolkit.getDefaultToolkit();
            this.glassPane = glassPane;
            this.contentPane = contentPane;
            this.parent = (JSplitPane) parent;
        }

        public void mouseMoved(MouseEvent e) {
           // redispatchMouseEvent(e, false);
        }

        public void mouseDragged(MouseEvent e) {
           // redispatchMouseEvent(e, false);
        }

        public void mouseClicked(MouseEvent e) {
            //redispatchMouseEvent(e, false);
        }

        public void mouseEntered(MouseEvent e) {
           redispatchMouseEvent(e, false);
        }

        public void mouseExited(MouseEvent e) {
           // redispatchMouseEvent(e, false);
        }

        public void mousePressed(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseReleased(MouseEvent e) {
           redispatchMouseEvent(e, false);
        }

        //A basic implementation of redispatching events.
        private void redispatchMouseEvent(MouseEvent e,
                                          boolean repaint) {
                       //Update the glass pane if requested.
            if (repaint) {
                glassPane.setVisible(false);
                glassPane.repaint();
            }
         //   parent.dispatchEvent(e);
        }
    }



	@Override
	public void onFailure(ValidationEvent vEvent) {
		// TODO Auto-generated method stub
		this.vEvent= vEvent;
		setVisible(true);
		repaint();
	}

	@Override
	public void onSuccess(ValidationEvent vEvent) {
		// TODO Auto-generated method stub
		
	}


}
