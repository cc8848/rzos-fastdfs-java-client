package com.ruin.app.fasfdfs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.cleverframe.fastdfs.cfg.ClientGlobal;
import org.cleverframe.fastdfs.client.DefaultStorageClient;
import org.cleverframe.fastdfs.client.DefaultTrackerClient;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.client.TrackerClient;
import org.cleverframe.fastdfs.conn.DefaultCommandExecutor;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.pool.ConnectionPool;
import org.cleverframe.fastdfs.pool.PooledConnectionFactory;
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
public class FastDFSFileManager2 implements FastDFSFileManagerConfig {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(FastDFSFileManager2.class);
	private static ConnectionPool connectionPool;
	private static StorageClient storageClient;
	private static TrackerClient trackerClient;
	// Initialize Fast DFS Client configurations
	static {
		try {
			String classPath = new File(FastDFSFileManager2.class.getResource("/").getFile()).getCanonicalPath();

			String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;

			logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);

			ClientGlobal.init(fdfsClientConfigFilePath);
			PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(ClientGlobal.getSotimeout(),
					ClientGlobal.getTimeout());
			GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
			conf.setMaxTotal(ClientGlobal.getMaxTotal());
			conf.setMaxTotalPerKey(ClientGlobal.getMaxTotalPerKey());
			conf.setMaxIdlePerKey(ClientGlobal.getMaxIdlePerKey());
			connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
			DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(ClientGlobal.getTrackerSet(),
					connectionPool);
			trackerClient = new DefaultTrackerClient(commandExecutor);
			storageClient = new DefaultStorageClient(commandExecutor, trackerClient);

		} catch (Exception e) {
			logger.error("error:", e);

		}
	}

	/**
	 * 上传文件  
	 * 
	 * @Title: upload  
	 * @Author:zhangjr
	 * @Create Date:2017年7月27日
	 * @return StorePath
	 * @param upFile
	 * @return
	 * @throws IOException 
	 */
	public static StorePath upload(File upFile) throws IOException {
		FileInputStream fileInputStream = FileUtils.openInputStream(upFile);
		String fileName = upFile.getName();
		String ext_name = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		StorePath storePath = storageClient.uploadFile(fileInputStream, upFile.length(), ext_name, null);
		StringBuilder baseUrl = new StringBuilder(PROTOCOL);
		baseUrl.append(trackerClient.getStorageNode().getInetSocketAddress().getAddress().getHostAddress()).append(":")
				.append(ClientGlobal.getTrackerNgnixPort()).append(SEPARATOR);
		storePath.setBaseUrl(baseUrl.toString());
		return storePath;

	}

	/**
	 * 上传文件  @Title: upload  
	 * 
	 * @Author:zhangjr
	 * @Create Date:2017年7月27日
	 * @return StorePath
	 * @param upFile
	 * @param meteData
	 * @return
	 * @throws IOException 
	 */
	public static StorePath upload(FastDFSFile2 file, Set<MateData> meteData) throws IOException {
		String fileName = file.getName();
		String ext_name = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		StorePath storePath = storageClient.uploadFile(file.getInpuStream(), file.getLength(), ext_name, meteData);
		StringBuilder baseUrl = new StringBuilder(PROTOCOL);
		baseUrl.append(trackerClient.getStorageNode().getInetSocketAddress().getAddress().getHostAddress()).append(":")
				.append(ClientGlobal.getTrackerNgnixPort()).append(SEPARATOR);
		storePath.setBaseUrl(baseUrl.toString());
		return storePath;

	}

}
