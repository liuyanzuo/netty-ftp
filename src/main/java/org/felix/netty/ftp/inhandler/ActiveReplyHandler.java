package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.state.StateHolder;
import org.felix.netty.ftp.utils.RetEnum;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;


public class ActiveReplyHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveReplyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        LOG.info("[New channel]-[Address:{}]", socketAddress.toString());
        String remoteAddress = ctx.channel().remoteAddress().toString();
        StateHolder.createStateMachine(remoteAddress);
        ctx.writeAndFlush(Tools.enumToMessageString(RetEnum.CONN_SUCCESS_WC));
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddress = ctx.channel().remoteAddress().toString();
        StateHolder.removeStateMachine(remoteAddress);
        LOG.info("[Close channel]-[Address:{}]", remoteAddress);
        ctx.writeAndFlush(Tools.enumToMessageString(RetEnum.CONN_SUCCESS_WC));
        ctx.fireChannelInactive();
    }
}
