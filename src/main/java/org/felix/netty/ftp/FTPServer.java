package org.felix.netty.ftp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.felix.netty.ftp.tools.ConfigTool;
import org.felix.netty.ftp.tools.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.felix.netty.ftp.tools.ServerConfig.DEFAULT_CONFIG_PATH;

/**
 * FTP服务器的启动入口
 * -
 *
 * @author Felix
 */
public class FTPServer {

    private static final Logger LOG = LoggerFactory.getLogger(FTPServer.class);

    private ServerConfig serverConfig;

    public FTPServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void start() {
        LOG.info("[FTP Server]-[control listener-{}:{}]", serverConfig.getControlServerAddress(), serverConfig.getControlServerPort());
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup ioGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, ioGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    });
            ChannelFuture future = server.bind(serverConfig.getControlServerAddress(), serverConfig.getControlServerPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error("[FTP Server Error]", e);
        } finally {
            //always shutdown whatever server close
            bossGroup.shutdownGracefully();
            ioGroup.shutdownGracefully();
        }
        LOG.info("[FTP Server started]");
    }
}
