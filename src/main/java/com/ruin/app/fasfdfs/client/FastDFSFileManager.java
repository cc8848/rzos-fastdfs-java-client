package com.ruin.app.fasfdfs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName:FileManager
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年7月18日
 */
public class FastDFSFileManager implements FastDFSFileManagerConfig {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(FastDFSFileManager.class);
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;

	 //Initialize Fast DFS Client configurations
	static {
		try {
			String classPath = new File(FastDFSFileManager.class.getResource("/").getFile()).getCanonicalPath();

			String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;

			logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);
			ClientGlobal.init(fdfsClientConfigFilePath);

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();

			storageClient = new StorageClient(trackerServer, storageServer);

		} catch (Exception e) {
			logger.error("错误", e);

		}
	}

	/**
	 * 
	 *  @Title: upload  
	 * 
	 * @Author:zhangjr
	 * @Create Date:2017年7月24日
	 * @return String
	 * @param file
	 * @return 
	 */
	public static String upload(FastDFSFile file) {
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", "120");
		meta_list[1] = new NameValuePair("heigth", "120");
		meta_list[2] = new NameValuePair("author", "Zhangjr");
		return upload(file, meta_list);

	}

	/**
	 * 
	 *  @Title: upload  
	 * 
	 * @Author:zhangjr
	 * @Create Date:2017年7月24日
	 * @return String
	 * @param file
	 * @param valuePairs
	 * @return 
	 */
	public static String upload(FastDFSFile file, NameValuePair[] valuePairs) {
		logger.info("File Name: " + file.getName() + "     File Length: " + file.getContent().length);
		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		String groupName = null;
		String remoteFileName = null;
		StorageServer storageServer = null;
		try {
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), valuePairs);
			groupName = uploadResults[0];
			remoteFileName = uploadResults[1];
			storageServer = trackerClient.getStoreStorage(trackerServer, groupName);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file: " + file.getName(), e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
		}
		logger.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null) {
			logger.info("upload file fail, error code: " + storageClient.getErrorCode());
		}
		long currrentTime2 = System.currentTimeMillis();
		StringBuilder fileAbsolutePathBuilder = new StringBuilder(PROTOCOL);
		fileAbsolutePathBuilder.append(storageServer.getInetSocketAddress().getAddress().getHostAddress()).append(":")
				.append(TRACKER_NGNIX_PORT).append(SEPARATOR).append(groupName).append(SEPARATOR)
				.append(remoteFileName);
		logger.info("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:" + " "
				+ remoteFileName);
		logger.info("使用 sb 获取绝对路径用时：" + (System.currentTimeMillis() - currrentTime2) + " ms");
		return fileAbsolutePathBuilder.toString();

	}

	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	public static byte[] downloadFile(String groupName, String remoteFileName) {
		try {
			return storageClient.download_file(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}
	public static void deleteFile(String groupName, String remoteFileName) throws Exception {
		storageClient.delete_file(groupName, remoteFileName);
	}

	public static StorageServer[] getStoreStorages(String groupName) throws IOException {
		return trackerClient.getStoreStorages(trackerServer, groupName);
	}

	public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
		return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
	}

	public static void main(String[] args) throws Exception {

		File content = new File("C:\\Users\\zhangjr\\Desktop\\1.jpg");

		FileInputStream fis = new FileInputStream(content);
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}
		String fileName = content.getName();
		String ext_name = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		FastDFSFile file = new FastDFSFile(content.getName(), file_buff, ext_name);
		System.out.println(content.getName());
		System.out.println("ext_name:" + ext_name);
		String fileAbsolutePath = FastDFSFileManager.upload(file);
		System.out.println(fileAbsolutePath);
		fis.close();
	}
}
