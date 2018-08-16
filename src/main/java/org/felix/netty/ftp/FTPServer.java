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
import org.felix.netty.ftp.inhandler.ActiveReplyHandler;
import org.felix.netty.ftp.inhandler.CommandSplitHandler;
import org.felix.netty.ftp.inhandler.LoginHandler;
import org.felix.netty.ftp.outhandler.LineDelimiterOutHandler;
import org.felix.netty.ftp.utils.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.felix.netty.ftp.utils.Tools.MAX_CMD_LEN;

/**
 * FTP服务器的启动入口
 * -
 *
 * @author Felix
 */
public class FTPServer {

    private static final Logger LOG = LoggerFactory.getLogger(FTPServer.class);

    public void start() {
        LOG.info("[FTP Server]-[control listener-{}:{}]", ServerConfig.getControlServerAddress(), ServerConfig.getControlServerPort());
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
                            ch.pipeline().addLast(new LineDelimiterOutHandler());
                            ch.pipeline().addLast(new LineBasedFrameDecoder(MAX_CMD_LEN));//resolve half package and split package
                            ch.pipeline().addLast(new ActiveReplyHandler());//active and inactive
                            ch.pipeline().addLast(new CommandSplitHandler());//ByteBuf -> Ftp message
                            ch.pipeline().addLast(new LoginHandler());
                        }
                    });
            ChannelFuture future = server.bind(ServerConfig.getControlServerAddress(), ServerConfig.getControlServerPort()).sync();
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
