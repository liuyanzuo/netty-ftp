package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;
import org.felix.netty.ftp.inhandler.InboundHandlerContext;

public interface IFTPCommandService {

    void execute(ChannelHandlerContext context, InboundHandlerContext ftpContext);

}
