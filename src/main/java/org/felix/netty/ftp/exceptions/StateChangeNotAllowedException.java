package org.felix.netty.ftp.exceptions;

import lombok.Data;

@Data
public class StateChangeNotAllowedException extends RuntimeException {

    private int nowState;

    private int futureState;

    public StateChangeNotAllowedException(String message, int nowState, int futureState) {
        super(message);
        this.nowState = nowState;
        this.futureState = futureState;
    }

    public StateChangeNotAllowedException(int nowState, int futureState) {
        this.nowState = nowState;
        this.futureState = futureState;
    }

}
