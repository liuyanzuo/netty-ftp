package org.felix.netty.ftp.domain;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.felix.netty.ftp.inhandler.CommandDispatchHandler;
import org.felix.netty.ftp.inhandler.InboundHandlerContext;
import org.felix.netty.ftp.state.SessionHolder;
import org.felix.netty.ftp.state.SessionId;
import org.felix.netty.ftp.utils.CommandConstants;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.felix.netty.ftp.state.SessionStateMachine.READY_TRANSFORM;
import static org.felix.netty.ftp.utils.RetEnum.PORT_OK;

/**
 * 主动模式下的PORT 命令处理
 */
public class PortService implements IFTPCommandService {

    private static final Logger LOG = LoggerFactory.getLogger(PortService.class);

    //connect
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public void init() {
        CommandDispatchHandler.registed(CommandConstants.PORT, this);
    }

    @Override
    public void execute(ChannelHandlerContext context, InboundHandlerContext ftpContext) {
        FTPCommand ftpCommand = ftpContext.getCommand();
        String portListString = ftpCommand.getParam();
        String[] strArr = portListString.split(",");
        assert strArr.length == 6;

        StringBuilder clientAddressBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            clientAddressBuilder.append(strArr[i]);
            clientAddressBuilder.append(".");
        }
        String clientAddress = clientAddressBuilder.substring(0, clientAddressBuilder.length() - 1);

        Integer choosePort = Integer.valueOf(strArr[4]) * 256 + Integer.valueOf(strArr[5]);
        //connect to the client open port
        context.writeAndFlush(Tools.enumToMessageString(PORT_OK));
        try {
            connectToClient(clientAddress, choosePort, ftpContext.getSessionId());
        } catch (InterruptedException e) {
            LOG.error("[Port Service]-[connect error]", e);
        }
    }

    private void connectToClient(String remoteAddress, int port, SessionId sessionId) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture sync = bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                //change state and save ChannelCtx
                                SessionHolder.updateSessionState(sessionId, READY_TRANSFORM, ctx);
                            }
                        });
                    }
                })
                .connect(remoteAddress, port).sync();
    }
}
