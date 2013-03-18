package com;
import com.mh.service.BackupService;



public class MysqlRestore {
	public static String DBPath = "setup.sql";
	
public static void main(String[] args) throws Throwable {
	int num=args.length;
	  String s[]=new String[num]; 
	  if(num>0){
	  System.out.println("The values enter at argument command line are:"); 
	  for (int i = 0; i <num ; i++)
	  {
		  	if(i==0){
		  		System.out.println("Host Name is"+args[0]);
		  	}else if(i==1){
		  		System.out.println("Port is"+args[1]);
		  	}else if(i==2){
		  		System.out.println("Username is"+args[2]);
		  	}else if(i==3){
		  		System.out.println("Password is"+args[3]);
		  	}else if(i==4){
		  		System.out.println("Database is"+args[4]);
		  	}
		  
		  	BackupService backupService = new BackupService();
		  	backupService.dropAndRestoreDB(args[4], args[2], args[3], DBPath);
	  }
	  }
	
}
}
