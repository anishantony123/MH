package com.mh.ui.validation.event;

import java.util.List;

import javax.swing.JComponent;

import com.mh.ui.validation.ValidationMsg;

public class ValidationEvent {
	private List<ValidationMsg> resultList;
	private boolean isValid;
	private JComponent parent;
	
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
	public JComponent getParent() {
		return parent;
	}
	public void setParent(JComponent parent) {
		this.parent = parent;
	}
	
}
