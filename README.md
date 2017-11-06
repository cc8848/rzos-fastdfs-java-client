# rzos-fastdfs-java-client
基于https://gitee.com/afeifqh/fastdfs-java-client  结合项目实际，修改和改进代码

使用方法，找到fdfs_client.conf 配置项，修改：

#内网环境
tracker_ip_addr = 192.168.1.169
tracker_server = 192.168.1.169:22122
# nginx 访问端口
#tracker_ngnix_port = 8889

三项配置为你的fastdfs服务器地址即可