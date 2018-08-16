package org.felix.netty.ftp.exceptions;

public class StateNotMatchException extends RuntimeException{

    private int nowState;

    public StateNotMatchException(String message, int nowState) {
        super(message);
        this.nowState = nowState;
    }
}
