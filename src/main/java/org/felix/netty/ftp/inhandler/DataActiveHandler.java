package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.state.SessionStateMachine;
import org.felix.netty.ftp.state.StateHolder;
import org.felix.netty.ftp.utils.Tools;

public class DataActiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //check is the client state
        String address = Tools.addressToStr(ctx.channel().remoteAddress());
        SessionStateMachine stateMachine = StateHolder.getStateMachine(address);
        //read to transform
        if (SessionStateMachine.READY_TRANSFORM == stateMachine.getState()) {
            //set client channel to state,ready to transform
            stateMachine.setExtData(ctx);
            return;
        }
        ctx.channel().close();
    }
}
