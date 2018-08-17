package org.felix.netty.ftp.inhandler;

import lombok.Data;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.state.SessionId;

/**
 * IN 方向的上下文
 */
@Data
public class InboundHandlerContext {

    private FTPCommand command;

    private SessionId sessionId;
}
