package com.kcm.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.kcm.dto.AttachFile;
import com.kcm.dto.Thumbnail;


public interface FileService {
	
	public AttachFile fileUpload(FileItem item);
	
	public Thumbnail setThumbnail(File file, String saveFileName);
	
	public void fileDown(HttpServletRequest request, HttpServletResponse response);

	public int delete(String no, String savefilename, String filepath, String thumb_filename);
}
