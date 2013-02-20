package com.mh.ui.validation;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.mh.ui.validation.annotations.Required;

public class RequiredValidator {

    public static boolean validate(Object obj, List<ValidationMsg> resultList){
    	boolean hasErrors = false;
        Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
        for( int i = 0; i < fields.length; i++ ){
            Required annotations = (Required)fields[i].getAnnotation(Required.class);
            if(annotations != null ){
                try{
                	fields[i].setAccessible(true);
                    if(fields[i].get(obj) != null){
                    	JTextComponent t= (JTextComponent) fields[i].get(obj);
                    	if(t.getText()==null || t.getText().trim().equals("")){// for required true
                    		ValidationMsg out = new ValidationMsg();
                    		out.setX(t.getX());
                    		out.setY(t.getY());
                    		out.setMsg(((Required)annotations).message());
                    		
                    		resultList.add(out);
                    		
                    		hasErrors= true;
                    	}
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return hasErrors;
    }

}
