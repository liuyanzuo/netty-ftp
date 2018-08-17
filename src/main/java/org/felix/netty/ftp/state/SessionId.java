package org.felix.netty.ftp.state;


import lombok.Data;

import java.util.Objects;

@Data
public class SessionId {

    private String remoteAddress;

    private int remotePort;

    public SessionId() {
    }

    public SessionId(String remoteAddress, int remotePort) {
        this.remoteAddress = remoteAddress;
        this.remotePort = remotePort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId = (SessionId) o;
        return remotePort == sessionId.remotePort &&
                Objects.equals(remoteAddress, sessionId.remoteAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteAddress, remotePort);
    }
}
