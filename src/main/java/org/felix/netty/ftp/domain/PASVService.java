package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;
import org.felix.netty.ftp.inhandler.InboundHandlerContext;

public class PASVService implements IFTPCommandService {

    private static final int DATA_PORT = 22;

    @Override
    public void execute(ChannelHandlerContext context, InboundHandlerContext ftpContext) {
        //open dataPort for client

    }
}
