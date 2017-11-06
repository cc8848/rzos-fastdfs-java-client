package org.cleverframe.fastdfs.cfg;

import java.util.HashSet;
import java.util.Set;

import org.cleverframe.fastdfs.utils.StringUtils;
import org.csource.common.IniFileReader;
import org.csource.common.MyException;

/**
 * 
 * @ClassName:ClientGlobal
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年7月28日
 */
public class ClientGlobal {

	// #Tracker Server IP地址
	public static final String CONF_KEY_TRACKERS = "fileupload.FastDFS.trackers";

	// #连接soTimeout设置
	public static final String CONF_KEY_NETWORK_SOTIMEOUT = "fileupload.FastDFS.soTimeout";
	// #连接超时设置
	public static final String CONF_KEY_NETWORK_CONNECT_TIMEOUT = "fileupload.FastDFS.connectTimeout";
	// #连接池 maxTotal
	public static final String CONF_KEY_MAXTOTAL = "fileupload.FastDFS.maxTotal";
	// #连接池 maxTotalPerKey
	public static final String CONF_KEY_MAXTOTALPERKEY = "fileupload.FastDFS.maxTotalPerKey";
	// #连接池 maxIdlePerKey 最大空闲连接数(影响并发性能)
	public static final String CONF_KEY_MAXIDLEPERKEY = "fileupload.FastDFS.maxIdlePerKey";
	// nginx 代理访问端口
	public static final String CONF_KEY_TRACKER_NGNIX_PORT = "tracker_ngnix_port";

	public static final int DEFAULT_NETWORK_SOTIMEOUT = 5; // second
	public static final int DEFAULT_NETWORK_CONNECT_TIMEOUT = 30; // second
	public static final int DEFAULT_MAXTOTAL = 200;
	public static final int DEFAULT_MAXTOTALPERKEY = 200;
	public static final int DEFAULT_MAXIDLEPERKEY = 50;
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String DEFAULT_TRACKER_NGNIX_PORT = "80";

	public static int sotimeout = DEFAULT_NETWORK_SOTIMEOUT * 1000;
	public static int timeout = DEFAULT_NETWORK_CONNECT_TIMEOUT * 1000;
	public static int maxTotal = DEFAULT_NETWORK_CONNECT_TIMEOUT;
	public static int maxTotalPerKey = DEFAULT_NETWORK_CONNECT_TIMEOUT;
	public static int maxIdlePerKey = DEFAULT_NETWORK_CONNECT_TIMEOUT;
	public static String trackerNgnixPort = DEFAULT_TRACKER_NGNIX_PORT;
	public static Set<String> trackerSet = new HashSet<>();

	private ClientGlobal() {
	}

	public static void init(String conf_filename) throws Exception {
		IniFileReader iniReader;
		String[] szTrackerServers;
		iniReader = new IniFileReader(conf_filename);

		sotimeout = iniReader.getIntValue(CONF_KEY_NETWORK_SOTIMEOUT, DEFAULT_NETWORK_SOTIMEOUT);
		if (sotimeout < 0) {
			sotimeout = DEFAULT_NETWORK_SOTIMEOUT * 1000;
		}
		sotimeout *= 1000;// millisecond

		timeout = iniReader.getIntValue(CONF_KEY_NETWORK_CONNECT_TIMEOUT, DEFAULT_NETWORK_CONNECT_TIMEOUT);
		if (timeout < 0) {
			timeout = DEFAULT_NETWORK_CONNECT_TIMEOUT * 1000;
		}
		timeout *= 1000;// millisecond

		maxTotal = iniReader.getIntValue(CONF_KEY_MAXTOTAL, DEFAULT_MAXTOTAL);

		if (maxTotal < 0) {
			maxTotal = DEFAULT_MAXTOTAL;
		}
		maxTotalPerKey = iniReader.getIntValue(CONF_KEY_MAXTOTALPERKEY, DEFAULT_MAXTOTALPERKEY);

		if (maxTotalPerKey < 0) {
			maxTotalPerKey = DEFAULT_MAXTOTALPERKEY;
		}
		maxIdlePerKey = iniReader.getIntValue(CONF_KEY_MAXIDLEPERKEY, DEFAULT_MAXIDLEPERKEY);

		if (maxIdlePerKey < 0) {
			maxIdlePerKey = DEFAULT_MAXIDLEPERKEY;
		}

		trackerNgnixPort = iniReader.getStrValue(CONF_KEY_TRACKER_NGNIX_PORT);

		if (StringUtils.isBlank(trackerNgnixPort)) {
			trackerNgnixPort = DEFAULT_TRACKER_NGNIX_PORT;
		}

		szTrackerServers = iniReader.getValues("tracker_server");
		if (szTrackerServers == null) {
			throw new MyException("item \"tracker_server\" in " + conf_filename + " not found");
		} else {
			for (String trackerServer : szTrackerServers) {
				trackerSet.add(trackerServer);
			}
		}
	}

	public static int getSotimeout() {
		return sotimeout;
	}

	public static void setSotimeout(int sotimeout) {
		ClientGlobal.sotimeout = sotimeout;
	}

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		ClientGlobal.timeout = timeout;
	}

	public static Set<String> getTrackerSet() {
		return trackerSet;
	}

	public static void setTrackerSet(Set<String> trackerSet) {
		ClientGlobal.trackerSet = trackerSet;
	}

	public static int getMaxTotal() {
		return maxTotal;
	}

	public static void setMaxTotal(int maxTotal) {
		ClientGlobal.maxTotal = maxTotal;
	}

	public static int getMaxTotalPerKey() {
		return maxTotalPerKey;
	}

	public static void setMaxTotalPerKey(int maxTotalPerKey) {
		ClientGlobal.maxTotalPerKey = maxTotalPerKey;
	}

	public static int getMaxIdlePerKey() {
		return maxIdlePerKey;
	}

	public static void setMaxIdlePerKey(int maxIdlePerKey) {
		ClientGlobal.maxIdlePerKey = maxIdlePerKey;
	}

	public static String getTrackerNgnixPort() {
		return trackerNgnixPort;
	}

	public static void setTrackerNgnixPort(String trackerNgnixPort) {
		ClientGlobal.trackerNgnixPort = trackerNgnixPort;
	}

}
