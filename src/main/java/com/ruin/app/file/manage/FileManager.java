package com.ruin.app.file.manage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.model.StorePath;
import org.springframework.web.multipart.MultipartFile;

import com.ruin.app.fasfdfs.client.FastDFSFile2;
import com.ruin.app.fasfdfs.client.FastDFSFileManager2;


/**
 * 文件上传下载管理器
 * @ClassName:FileManager
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年11月6日
 */
public class FileManager {
	/**
	 * 
	 * 支持多文件同时上传
	 * 
	 * @Author:zhangjr
	 * @Create Date:2017年7月27日
	 * @return String
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public static String multithreadUploadFile(String filePath) throws Exception {
		if (StringUtils.isBlank(filePath)) {
			throw new Exception("文件路径不能为空！");
		}
		File upFile = new File(filePath);

		StorePath storePath = FastDFSFileManager2.upload(upFile);

		String fileAbsolutePath = storePath.getFullPath();

		String baseUrl = storePath.getBaseUrl();
		return baseUrl + fileAbsolutePath;
	}
	
	/**
	 * 
	 *  @Title: multithreadUploadFile  
	 * 
	 * @Author:zhangjr
	 * @Create Date:2017年7月27日
	 * @return String
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public static String multithreadUploadFile(File upFile) throws Exception {
		if (upFile == null) {
			throw new Exception("文件不能为空！");
		}
		StorePath storePath = FastDFSFileManager2.upload(upFile);

		String fileAbsolutePath = storePath.getFullPath();

		String baseUrl = storePath.getBaseUrl();
		return baseUrl + fileAbsolutePath;
	}
	
	/**
	 * 结合spring 上传文件 
	 * @Author:zhangjr
	 * @Create Date:2017年7月24日
	 * @return String
	 * @param attach
	 * @return
	 * @throws Exception 
	 */
	public static String multithreadUploadFile(MultipartFile attach) throws Exception {
		// 获取文件后缀名
		String ext = attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf(".") + 1);
		FastDFSFile2 file = new FastDFSFile2(attach.getBytes(), ext, attach.getInputStream());
		Set<MateData> meteData = new HashSet<>();
		meteData.add(new MateData("fileName", attach.getOriginalFilename()));
		meteData.add(new MateData("fileLength", attach.getOriginalFilename()));
		meteData.add(new MateData("fileExt", ext));

		StorePath storePath = FastDFSFileManager2.upload(file, meteData);
		String fileAbsolutePath = storePath.getFullPath();

		String baseUrl = storePath.getBaseUrl();
		return baseUrl + fileAbsolutePath;
	}
}
