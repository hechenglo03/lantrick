package com.hcl.lantrick.server.config;

import com.hcl.common.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * hechenglong
 * 2019/9/24
 */
public class ProxyConfig {

    //需要绑定ip地址
    private String serverIp;

    private String serverPort;

    //http服务器上绑定ip
    private String httpServerIp;

    private String httpServerPort;

    private List<Client> clients;

    private static Logger logger = LoggerFactory.getLogger(ProxyConfig.class);

    private ProxyConfig(){
        this.serverIp = Config.getInstance().getStringValueWithDefault("serverIp","0.0.0.0");
        this.serverPort = Config.getInstance().getStringValueWithDefault("serverPort","9953");
        this.httpServerIp = Config.getInstance().getStringValueWithDefault("httpServerIp","0.0.0.0");
        this.httpServerPort = Config.getInstance().getStringValueWithDefault("httpServerIpPort","80");
        logger.info("serverIp:{},serverPort:{},httpServerIp:{},httpServerIpPort:{}",serverIp,serverPort,httpServerIp,httpServerPort);
    }

    public static class Client{

        private String clientKey;

        private String name;

        private List<ClientProxyMapping> clientProxyMappings;

        public String getClientKey() {
            return clientKey;
        }

        public void setClientKey(String clientKey) {
            this.clientKey = clientKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ClientProxyMapping> getClientProxyMappings() {
            return clientProxyMappings;
        }

        public void setClientProxyMappings(List<ClientProxyMapping> clientProxyMappings) {
            this.clientProxyMappings = clientProxyMappings;
        }
    }

    public static class ClientProxyMapping{

        private String lanInfo;

        private String clientName;

        //代理服务器端口
        private String clientPort;

        public String getLanInfo() {
            return lanInfo;
        }

        public void setLanInfo(String lanInfo) {
            this.lanInfo = lanInfo;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientPort() {
            return clientPort;
        }

        public void setClientPort(String clientPort) {
            this.clientPort = clientPort;
        }
    }




}
