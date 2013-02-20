package com.mh.model;

import java.io.File;
import java.sql.Blob;
import java.util.List;

public class UserData {
	private Long id;
	private Long userId;
	private String fileName;
	private List<String> filePath;
	private File file;
	private Integer sequence;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getFilePath() {
		return filePath;
	}
	public void setFilePath(List<String> filePath) {
		this.filePath = filePath;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
	
	

}
