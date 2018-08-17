package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;
import org.felix.netty.ftp.inhandler.CommandDispatchHandler;
import org.felix.netty.ftp.inhandler.InboundHandlerContext;
import org.felix.netty.ftp.state.FTPSession;
import org.felix.netty.ftp.state.SessionHolder;
import org.felix.netty.ftp.state.SessionId;
import org.felix.netty.ftp.utils.CommandConstants;
import org.felix.netty.ftp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.felix.netty.ftp.utils.RetEnum.CLOSE_DATA;
import static org.felix.netty.ftp.utils.RetEnum.FILE_OK;
import static org.felix.netty.ftp.utils.Tools.LINE;

/**
 * Name List 服务
 */
public class NLISTService implements IFTPCommandService {

    private static final Logger LOG = LoggerFactory.getLogger(NLISTService.class);

    public void init() {
        CommandDispatchHandler.registed(CommandConstants.NLST, this);
    }

    @Override
    public void execute(ChannelHandlerContext context, InboundHandlerContext ftpContext) {
        SessionId sessionId = ftpContext.getSessionId();
        Optional<FTPSession> sessionOp = SessionHolder.getSession(sessionId);
        if (sessionOp.isPresent()) {
            FTPSession session = sessionOp.get();
            File presentFile = session.getPresentFile();
            String[] nameList = presentFile.list();
            StringBuilder retMsgBuilder = new StringBuilder();
            if (!Objects.isNull(nameList)) {
                for (String name : nameList) {
                    retMsgBuilder.append(name);
                    retMsgBuilder.append(LINE);
                }
            }
            context.writeAndFlush(Tools.enumToMessageString(FILE_OK));
            try {
                ChannelHandlerContext dataChannel = session.getDataChannel();
                dataChannel.writeAndFlush(Tools.strToMessage(retMsgBuilder.toString())).sync().get();
                dataChannel.close().sync().get();
            } catch (Exception e) {
                LOG.error("",e);
            }
            context.writeAndFlush(Tools.enumToMessageString(CLOSE_DATA));
        }
    }
}
