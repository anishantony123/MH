package com.mh.ui.validation.event;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;

import com.mh.ui.validation.ValidationMsg;

public class ValidationEvent {
	private List<ValidationMsg> resultList;
	private boolean isValid;
	private Component parent;
	private int dx=0;
	private int dy=0;
	
	public List<ValidationMsg> getResultList() {
		return resultList;
	}
	public void setResultList(List<ValidationMsg> resultList) {
		this.resultList = resultList;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Component getParent() {
		return parent;
	}
	public void setParent(Component parent) {
		this.parent = parent;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	
}
