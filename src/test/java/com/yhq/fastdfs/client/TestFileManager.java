package com.yhq.fastdfs.client;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;
import org.junit.Assert;
import org.junit.Test;

import com.ruin.app.fasfdfs.client.FastDFSFile;
import com.ruin.app.fasfdfs.client.FastDFSFileManager;
/**
 * 
 * @ClassName:TestFileManager
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年7月18日
 */
public class TestFileManager {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test  
    public void upload() throws Exception {  
        File content = new File("C:\\Users\\zhangjr\\Desktop\\1.jpg");  
          
        FileInputStream fis = new FileInputStream(content);  
        byte[] file_buff = null;  
        if (fis != null) {  
            int len = fis.available();  
            file_buff = new byte[len];  
            fis.read(file_buff);  
        }  
          
        FastDFSFile file = new FastDFSFile(content.getName(), file_buff, "jpg");  
          
        String fileAbsolutePath = FastDFSFileManager.upload(file);  
        System.out.println(fileAbsolutePath);  
        fis.close();  
    }  
	
	@Test  
    public void getFile() throws Exception {  
        FileInfo file = FastDFSFileManager.getFile("group1", "M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg");  
        Assert.assertNotNull(file);  
        String sourceIpAddr = file.getSourceIpAddr();  
        long size = file.getFileSize();  
        System.out.println("ip:" + sourceIpAddr + ",size:" + size);  
    }  
      
    @Test  
    public void getStorageServer() throws Exception {  
        StorageServer[] ss = FastDFSFileManager.getStoreStorages("group1");  
        Assert.assertNotNull(ss);  
          
        for (int k = 0; k < ss.length; k++){  
            System.err.println(k + 1 + ". " + ss[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + ss[k].getInetSocketAddress().getPort());  
        }  
    }  
      
    @Test  
    public void getFetchStorages() throws Exception {  
        ServerInfo[] servers = FastDFSFileManager.getFetchStorages("group1", "M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg");  
        Assert.assertNotNull(servers);  
          
        for (int k = 0; k < servers.length; k++) {  
            System.err.println(k + 1 + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());  
        }  
    }  
}
