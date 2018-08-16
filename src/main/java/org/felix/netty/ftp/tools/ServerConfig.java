package org.felix.netty.ftp.tools;

import lombok.Data;

import java.util.Set;

/**
 * 服务端配置
 *
 * @author Felix
 */
@Data
public class ServerConfig {

    public static final String CONTROL_SERVER_ADDRESS = "control.server.address";

    public static final String DEFAULT_CONTROL_SERVER_ADDRESS = "control.server.address";

    public static final String CONTROL_SERVER_PORT = "control.server.port";

    public static final String DEFAULT_CONTROL_SERVER_PORT = "21";

    public static final String IS_ANONYMOUS = "anonymous";

    public static final boolean DEFAULT_IS_ANONYMOUS = false;

    public static final String DEFAULT_CONFIG_PATH = "default.properties";

    /**
     * 控制通道服务端地址
     */
    private String controlServerAddress;

    /**
     * 控制通道服务端端口
     */
    private int controlServerPort;

    /**
     * 是否允许匿名登录
     */
    private boolean isAnonymousAllowed;

    /**
     * 白名单
     */
    private Set<String> whiteList;
}
