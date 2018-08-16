package org.felix.netty.ftp.state;

import org.felix.netty.ftp.exceptions.StateNotMatchException;

import java.util.HashMap;

public class StateHolder {

    static final HashMap<String, SessionStateMachine> connStateMap = new HashMap<>();

    public static SessionStateMachine getStateMachine(String remoteAddress) {
        return connStateMap.get(remoteAddress);
    }

    public static void createStateMachine(String remoteAddress) {
        SessionStateMachine tmp = new SessionStateMachine();
        tmp.setState(SessionStateMachine.CONNECTED);
        connStateMap.put(remoteAddress, tmp);
    }

    public static void removeStateMachine(String remoteAddress) {
        connStateMap.remove(remoteAddress);
    }

    public static void updateData(int nowState, String remoteAddress, Object data) {
        SessionStateMachine tmp = connStateMap.get(remoteAddress);
        if (tmp.getState() != nowState) {
            throw new StateNotMatchException("state not match", tmp.getState());
        }
        tmp.setExtData(data);
    }

    public static Object getData(String remoteAddress) {
        SessionStateMachine tmp = connStateMap.get(remoteAddress);
        return tmp.getExtData();
    }

}
