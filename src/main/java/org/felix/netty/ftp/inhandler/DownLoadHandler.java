package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.utils.CommandConstants;

public class DownLoadHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FTPCommand ftpCommand = ((FTPCommand) msg);

        if (CommandConstants.PORT.equals(ftpCommand.getCommand())) {

        }
    }
}
