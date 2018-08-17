package org.felix.netty.ftp.inhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.state.SessionId;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static org.felix.netty.ftp.utils.Tools.SPLIT;


public class CommandSplitHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(CommandSplitHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = Tools.msgToString(((ByteBuf) msg));
        String[] messageSplits = message.split(SPLIT);
        if (messageSplits.length <= 0) {
            //TODO:illegal command
        }

        InboundHandlerContext context = new InboundHandlerContext();

        //fill session info
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        String remoteAddress = inetSocketAddress.getAddress().toString();
        int remotePort = inetSocketAddress.getPort();
        SessionId sessionId = new SessionId(remoteAddress, remotePort);
        context.setSessionId(sessionId);

        //fill command info
        String param = Tools.attachStrings(messageSplits, false);
        FTPCommand ftpCommand = new FTPCommand(messageSplits[0], param);
        LOG.debug("[Split command]-[{}]", ftpCommand);
        context.setCommand(ftpCommand);

        ctx.fireChannelRead(context);
    }
}
