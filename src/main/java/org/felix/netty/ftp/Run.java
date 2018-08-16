package org.felix.netty.ftp;

import org.felix.netty.ftp.tools.ConfigTool;
import org.felix.netty.ftp.tools.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Run {

    private static final Logger LOG = LoggerFactory.getLogger(Run.class);

    public static void main(String[] args) {
        String configPath;
        if (args.length == 0) {
            configPath = ConfigTool.getResource(ServerConfig.DEFAULT_CONFIG_PATH);
        } else {
            configPath = args[0];
        }
        try {
            ServerConfig serverConfig = ConfigTool.loadConfig(configPath);
            new FTPServer(serverConfig).start();
        } catch (IOException e) {
            LOG.error("[can't load config]");
        }
    }

}
