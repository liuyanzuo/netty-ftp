package org.felix.netty.ftp.state;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.time.LocalTime;

/**
 * 每一个连接（客户端IP+端口标示）都会关联到一个Session
 */

@Data
public class FTPSession {

    private SessionId id;

    private LocalTime startTime;

    private boolean isTimeOut;

    /*数据通道*/
    private ChannelHandlerContext dataChannel;

    /*状态机*/
    private SessionStateMachine state;

}
