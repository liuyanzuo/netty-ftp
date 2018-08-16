package org.felix.netty.ftp.inhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        String param = Tools.attachStrings(messageSplits, false);
        FTPCommand ftpCommand = new FTPCommand(messageSplits[0], param);
        LOG.debug("[Split command]-[{}]", ftpCommand);
        ctx.fireChannelRead(ftpCommand);
    }
}
