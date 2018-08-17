package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.state.SessionHolder;
import org.felix.netty.ftp.utils.RetEnum;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static org.felix.netty.ftp.state.SessionStateMachine.CONNECTED;


public class ActiveReplyHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveReplyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        String remoteAddress = inetSocketAddress.getAddress().toString();
        int remotePort = inetSocketAddress.getPort();

        LOG.info("[New channel]-[Address:{}:{}]", remoteAddress,remotePort);
        SessionHolder.addSession(remoteAddress,remotePort);
        SessionHolder.updateSessionState(remoteAddress,remotePort,CONNECTED);
        LOG.debug("[New channel]-[Address:{}]-[create session successful]", socketAddress.toString());
        ctx.writeAndFlush(Tools.enumToMessageString(RetEnum.CONN_SUCCESS_WC));
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        String remoteAddress = inetSocketAddress.getAddress().toString();
        int remotePort = inetSocketAddress.getPort();
        SessionHolder.removeSession(remoteAddress,remotePort);

        LOG.info("[Close channel]-[Address:{}]-[remove session successful]", remoteAddress);
        ctx.writeAndFlush(Tools.enumToMessageString(RetEnum.CONN_SUCCESS_WC));
        ctx.fireChannelInactive();
    }
}
