package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;

public class PASVService implements IFTPCommandService {

    private static final int DATA_PORT = 22;

    @Override
    public void execute(FTPCommand ftpCommand, ChannelHandlerContext context) {
        //open dataPort for client

    }
}
