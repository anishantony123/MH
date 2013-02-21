package com.mh.ui.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.Main;
import com.mh.ui.validation.annotations.Compare;
import com.mh.ui.validation.annotations.Required;

public class Validator {

    public static boolean validateSuper(Object obj, List<ValidationMsg> resultList){
    	boolean hasErrors = false;
        Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
        for( int i = 0; i < fields.length; i++ ){
            Required annotations = (Required)fields[i].getAnnotation(Required.class);
            if(annotations != null ){
                try{
                	JTextComponent t = null;
                	fields[i].setAccessible(true);
                    if(fields[i].get(obj) != null){
                    	if(fields[i].get(obj) instanceof JTextComponent){
                    		t= (JTextComponent) fields[i].get(obj);
                    		Field targetField = obj.getClass().getSuperclass().getDeclaredField(annotations.target());
                    		targetField.setAccessible(true);
                    		JComponent target = (JComponent) targetField.get(obj);
                    		hasErrors = validateRequired(hasErrors,resultList,annotations,t, target);
                    	}
                    	
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return hasErrors;
    }
    /**
     * This method validate required field
     * @param resultList - result list contain validation msg objects
     * @param annotations - currently only Required
     * @param t - field to be validated
     * @param target - component relative to which validation message displayed
     * @return true if validation fails
     */
	protected static boolean validateRequired(boolean hasErrors,List<ValidationMsg> resultList, Required annotations, JTextComponent t,JComponent target) {
		if(t!=null &&(t.getText()==null || t.getText().trim().equals(""))){// for required true
			ValidationMsg out = new ValidationMsg();
			out.setX(target.getX());
			out.setY(target.getY());
			out.setMsg(((Required)annotations).message());                        		
			resultList.add(out);                        		
			hasErrors= true;
		}
		return hasErrors;
	}
    public static boolean validate(Object obj, List<ValidationMsg> resultList){
    	boolean hasErrors = false;
        Field[] fields = obj.getClass().getDeclaredFields();
        for( int i = 0; i < fields.length; i++ ){
        	Field currentField = fields[i];
			hasErrors = assessField(obj, resultList, currentField );          
        }
        return hasErrors;
    }
    public static boolean comparePassword(Object obj, List<ValidationMsg> resultList){
    	boolean hasErrors = false;
    	String mode="";
    	try {
			if(obj.getClass().getField("mode")!=null){
				mode = (String) obj.getClass().getField("mode").get(obj);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			mode="";
		} 
        Field[] fields = obj.getClass().getDeclaredFields();
        for( int i = 0; i < fields.length; i++ ){
            Compare annotations = (Compare)fields[i].getAnnotation(Compare.class);
            if(annotations != null ){
                try{
                	
                	fields[i].setAccessible(true);
                	JTextComponent current=null,target=null;
                    if(fields[i].get(obj) != null){
                    	if(((Compare)annotations).compareTo().equals("session")){
                    	
   	                    	 current= (JTextComponent) fields[i].get(obj);
   	                    	 if(!Main.session.getPassword().equals(current.getText())){
   	                    		 hasErrors=true;

   	                    		ValidationMsg out = new ValidationMsg();
   	                    		out.setX(current.getX());
   	                    		out.setY(current.getY());
   	                    		out.setMsg(((Compare)annotations).message());
   	                    		
   	                    		resultList.add(out);   
   	                    	 }
                    		
                    		
                    	}else{
                    		Field compareTo = obj.getClass().getDeclaredField(((Compare)annotations).compareTo());
                        	compareTo.setAccessible(true);
                        	
                        	if(compareTo.get(obj)!=null){
    	                    	 current= (JTextComponent) fields[i].get(obj);
    	                    	 target= (JTextComponent) compareTo.get(obj);
    	                    	 if(current.getText()!=null && target.getText()!=null){
    	                    		 if(!current.getText().equals(target.getText())){
    	                    			 hasErrors= true;

    	                         		ValidationMsg out = new ValidationMsg();
    	                         		out.setX(current.getX());
    	                         		out.setY(current.getY());
    	                         		out.setMsg(((Compare)annotations).message());
    	                         		
    	                         		resultList.add(out);   
    	                    		 }else if(mode.equals("0") && current.getText().equals(Main.session.getPassword())){//for edit me
    	                    			 ValidationMsg out = new ValidationMsg();
     	                         		out.setX(current.getX());
     	                         		out.setY(current.getY());
     	                         		out.setMsg(((Compare)annotations).message());
     	                         		
     	                         		resultList.add(out);  
    	                    		 }
    	                    	 }
    	                    	
                        	}
                    	}
                    	
                    	                		
                    		
                    	
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return hasErrors;
    }
    
   public static boolean validateWithFilter(Object obj, List<ValidationMsg> resultList,List<String> filterFields){
    	boolean hasErrors = false;
        Field[] fields = obj.getClass().getDeclaredFields();
        for( int i = 0; i < fields.length; i++ ){
        	Field currentField = fields[i];
        	if(filterFields.contains(currentField.getName())){
	            hasErrors = assessField(obj, resultList, currentField);
        	}
        }
        return hasErrors;
    }
	public static boolean assessField(Object obj,
			List<ValidationMsg> resultList, Field currentField) {
		boolean hasErrors = false;
		Required annotations = (Required)currentField.getAnnotation(Required.class);
		if(annotations != null ){
		    try{
		    	currentField.setAccessible(true);
		        if(currentField.get(obj) != null){
		        	JTextComponent t= (JTextComponent) currentField.get(obj);
		        	Field targetField = obj.getClass().getDeclaredField(annotations.target());
		    		targetField.setAccessible(true);
		    		JComponent target = (JComponent) targetField.get(obj);
		    		hasErrors = validateRequired(hasErrors,resultList,annotations,t, target);
		        }
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		}
		return hasErrors;
	}

}
