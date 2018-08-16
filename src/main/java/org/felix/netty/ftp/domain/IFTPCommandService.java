package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;

public interface IFTPCommandService {

    void execute(FTPCommand ftpCommand, ChannelHandlerContext context);

}
