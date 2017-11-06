package com.ruin.app.fasfdfs.client;

import java.io.Serializable;
/**
 * 
 * @ClassName:FileManagerConfig
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年7月18日
 */
public interface FastDFSFileManagerConfig extends Serializable {
	public static final String FILE_DEFAULT_WIDTH   = "120";  
    public static final String FILE_DEFAULT_HEIGHT  = "120";  
    public static final String FILE_DEFAULT_AUTHOR  = "Zhangjr";  
      
    public static final String PROTOCOL = "http://";  
    public static final String SEPARATOR = "/";  
      
    public static final String TRACKER_NGNIX_PORT   = "8888"; //8889 
      
    public static final String CLIENT_CONFIG_FILE   = "fdfs_client.conf"; 
}
