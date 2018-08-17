package org.felix.netty.ftp.utils;

import java.util.Set;

/**
 * 服务端配置
 *
 * @author Felix
 */
public class ServerConfig {

    public static final String CONTROL_SERVER_ADDRESS = "control.server.address";

    public static final String SERVER_WHITE_LIST = "server.whitelist";

    public static final String DEFAULT_CONTROL_SERVER_ADDRESS = "control.server.address";

    public static final String CONTROL_SERVER_PORT = "control.server.port";

    public static final String DEFAULT_CONTROL_SERVER_PORT = "21";

    public static final String IS_ANONYMOUS = "anonymous";

    public static final String ROOT_DIR = "root.dir";

    public static final String DEFAULT_ROOT_DIR = ".";

    public static final boolean DEFAULT_IS_ANONYMOUS = false;

    public static final String DEFAULT_CONFIG_PATH = "default.properties";

    /**
     * 控制通道服务端地址
     */
    private static String controlServerAddress;

    /**
     * 控制通道服务端端口
     */
    private static int controlServerPort;

    /**
     * 是否允许匿名登录
     */
    private static boolean isAnonymousAllowed;

    /**
     * 白名单
     */
    private static Set<String> whiteList;

    /**
     * FTP根目录
     */
    private static String rootDir;

    public static String getControlServerAddress() {
        return controlServerAddress;
    }

    public static void setControlServerAddress(String controlServerAddress) {
        ServerConfig.controlServerAddress = controlServerAddress;
    }

    public static int getControlServerPort() {
        return controlServerPort;
    }

    public static void setControlServerPort(int controlServerPort) {
        ServerConfig.controlServerPort = controlServerPort;
    }

    public static boolean isIsAnonymousAllowed() {
        return isAnonymousAllowed;
    }

    public static void setIsAnonymousAllowed(boolean isAnonymousAllowed) {
        ServerConfig.isAnonymousAllowed = isAnonymousAllowed;
    }

    public static Set<String> getWhiteList() {
        return whiteList;
    }

    public static void setWhiteList(Set<String> whiteList) {
        ServerConfig.whiteList = whiteList;
    }

    public static String getRootDir() {
        return rootDir;
    }

    public static void setRootDir(String rootDir) {
        ServerConfig.rootDir = rootDir;
    }
}
