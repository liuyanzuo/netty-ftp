package org.felix.netty.ftp.state;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.io.File;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 每一个连接（客户端IP+端口标示）都会关联到一个Session
 */

public class FTPSession {

    private SessionId id;

    private LocalTime startTime;

    private boolean isTimeOut;

    /*数据通道*/
    private ChannelHandlerContext dataChannel;

    /*状态机*/
    private SessionStateMachine state;

    /*当前位置*/
    private File presentFile;

    public SessionId getId() {
        return id;
    }

    public void setId(SessionId id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

    public void setTimeOut(boolean timeOut) {
        isTimeOut = timeOut;
    }

    public synchronized ChannelHandlerContext getDataChannel() throws InterruptedException {
        if(Objects.isNull(dataChannel)){
            wait();
        }
        return dataChannel;
    }

    public synchronized void setDataChannel(ChannelHandlerContext dataChannel) {
        this.dataChannel = dataChannel;//make sure data exists before wake up
        notifyAll();
    }

    public SessionStateMachine getState() {
        return state;
    }

    public void setState(SessionStateMachine state) {
        this.state = state;
    }

    public File getPresentFile() {
        return presentFile;
    }

    public void setPresentFile(File presentFile) {
        this.presentFile = presentFile;
    }
}
