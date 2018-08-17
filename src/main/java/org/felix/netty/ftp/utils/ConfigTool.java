package org.felix.netty.ftp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;

import static org.felix.netty.ftp.utils.ServerConfig.*;

public class ConfigTool {

    private ConfigTool() {
    }

    public static ClassLoader classLoader() {
        return ConfigTool.class.getClassLoader();
    }

    public static String getResource(String path) {
        return classLoader().getResource(path).getPath();
    }

    public static void loadConfig(String path) throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream(path));
        ServerConfig.setControlServerAddress((String) config.getOrDefault(CONTROL_SERVER_ADDRESS, DEFAULT_CONTROL_SERVER_ADDRESS));
        ServerConfig.setControlServerPort(Integer.valueOf((String) config.getOrDefault(CONTROL_SERVER_PORT, DEFAULT_CONTROL_SERVER_PORT)));
        ServerConfig.setIsAnonymousAllowed(Boolean.valueOf((String) config.getOrDefault(IS_ANONYMOUS, DEFAULT_IS_ANONYMOUS)));
        ServerConfig.setRootDir((String) config.getOrDefault(ROOT_DIR,DEFAULT_ROOT_DIR));
        //check file is exists
        if(!new File(ServerConfig.getRootDir()).exists()){
            throw new FileNotFoundException("ftp root file is not exists");
        }
        String serverWhiteList = config.getProperty(SERVER_WHITE_LIST);
        if (Objects.isNull(serverWhiteList)) {
            ServerConfig.setWhiteList(new HashSet<>());
        }else{
            ServerConfig.setWhiteList(getWhiteList(serverWhiteList));
        }
    }

    private static HashSet<String> getWhiteList(String serverWhiteList) {
        HashSet<String> whiteListSet = new HashSet<>();
        String[] whiteListArr = serverWhiteList.split(",");
        for (String whiteUser : whiteListArr) {
            whiteListSet.add(whiteUser);
        }
        return whiteListSet;
    }

}
