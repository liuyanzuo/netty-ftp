package org.felix.netty.ftp.state;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session保持
 */
public class SessionHolder {

    private static final ConcurrentHashMap<SessionId, FTPSession> sessionMap = new ConcurrentHashMap<>();

    public static void addSession(String remoteAddress, int remotePort) {
        FTPSession session = new FTPSession();
        session.setStartTime(LocalTime.now());

        SessionStateMachine stateMachine = new SessionStateMachine();
        session.setState(stateMachine);

        SessionId id = new SessionId(remoteAddress, remotePort);
        session.setId(id);

        sessionMap.put(id, session);
    }

    public static Optional<FTPSession> getSession(String remoteAddress, int remotePort) {
        SessionId id = new SessionId(remoteAddress, remotePort);
        return getSession(id);
    }

    public static Optional<FTPSession> getSession(SessionId id) {
        FTPSession ret = sessionMap.get(id);
        if (Objects.isNull(ret)) {
            return Optional.empty();
        }
        return Optional.of(ret);
    }

    public static void removeSession(String remoteAddress, int remotePort) {
        SessionId id = new SessionId(remoteAddress, remotePort);
        sessionMap.remove(id);
    }

    public static void updateSessionState(String remoteAddress, int remotePort, int futureState, Object extData) {
        SessionId id = new SessionId(remoteAddress, remotePort);
        updateSessionState(id,futureState,extData);
    }

    public static void updateSessionState(SessionId id, int futureState,Object extData) {
        Optional<FTPSession> sessionOp = getSession(id);
        FTPSession session = sessionOp.get();
        session.getState().setState(futureState);
        session.getState().setExtData(extData);
    }

    public static void updateSessionState(String remoteAddress, int remotePort, int futureState) {
        SessionId id = new SessionId(remoteAddress, remotePort);
        updateSessionState(id,futureState);
    }

    public static void updateSessionState(SessionId id, int futureState) {
        Optional<FTPSession> sessionOp = getSession(id);
        FTPSession session = sessionOp.get();
        session.getState().setState(futureState);
    }

}
