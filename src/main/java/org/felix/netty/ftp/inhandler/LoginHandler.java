package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.domain.UserPermissionService;
import org.felix.netty.ftp.state.SessionStateMachine;
import org.felix.netty.ftp.state.StateHolder;
import org.felix.netty.ftp.utils.RetEnum;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.felix.netty.ftp.state.SessionStateMachine.CONNECTED;
import static org.felix.netty.ftp.state.SessionStateMachine.USER_LOGGED;
import static org.felix.netty.ftp.state.SessionStateMachine.USER_NAME_READY;
import static org.felix.netty.ftp.utils.CommandConstants.PASS;
import static org.felix.netty.ftp.utils.CommandConstants.USER;


public class LoginHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FTPCommand command = (FTPCommand) msg;
        String remoteAddress = ctx.channel().remoteAddress().toString();
        SessionStateMachine stateMachine = StateHolder.getStateMachine(remoteAddress);

        if (Objects.isNull(stateMachine)) {
            //connection not active
            ctx.write(Tools.enumToMessageString(RetEnum.UNIDENTIFY_CONNECTION));
            //TODO:close channel
            return;
        }

        if (USER.equals(command.getCommand())) {
            //specify user
            String user = command.getParam();
            StateHolder.updateData(CONNECTED, remoteAddress, user);
            StateHolder.updateState(USER_NAME_READY, remoteAddress);
            LOG.info("[Login message]-[User:{}]", user);
            ctx.write(Tools.enumToMessageString(RetEnum.USER_NEED_PASSWORD));
            return;
        }

        if (PASS.equals(command.getCommand())) {
            Object extData = stateMachine.getExtData();
            if (Objects.isNull(extData)) {
                //no user info
                ctx.write(Tools.enumToMessageString(RetEnum.USER_NOT_IDENTIFY));
                return;
            }
            String userName = (String) extData;
            //password validation
            if (UserPermissionService.checkLogged(userName, command.getParam())) {
                StateHolder.updateData(USER_NAME_READY, remoteAddress, USER_LOGGED);
                StateHolder.updateState(USER_LOGGED, remoteAddress);
                ctx.write(Tools.enumToMessageString(RetEnum.USER_LOGGED));
                return;
            }
            ctx.write(Tools.enumToMessageString(RetEnum.NOT_LOGIN));
        }

        //judge is pass
        if (stateMachine.getState() >= USER_LOGGED) {
            ctx.fireChannelRead(msg);
        } else {
            //not login
            ctx.write(Tools.enumToMessageString(RetEnum.NOT_LOGIN));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
