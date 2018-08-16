package org.felix.netty.ftp.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.felix.netty.ftp.tools.ServerConfig.*;

public class ConfigTool {

    private ConfigTool() {
    }

    public static ClassLoader classLoader() {
        return ConfigTool.class.getClassLoader();
    }

    public static String getResource(String path) {
        return classLoader().getResource(path).getPath();
    }

    public static ServerConfig loadConfig(String path) throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream(path));
        ServerConfig ret = new ServerConfig();
        ret.setControlServerAddress((String) config.getOrDefault(CONTROL_SERVER_ADDRESS, DEFAULT_CONTROL_SERVER_ADDRESS));
        ret.setControlServerPort(Integer.valueOf((String) config.getOrDefault(CONTROL_SERVER_PORT, DEFAULT_CONTROL_SERVER_PORT)));
        ret.setAnonymousAllowed(Boolean.valueOf((String) config.getOrDefault(IS_ANONYMOUS, DEFAULT_IS_ANONYMOUS)));
        return ret;
    }

}
