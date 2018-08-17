package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;
import org.felix.netty.ftp.inhandler.CommandDispatchHandler;
import org.felix.netty.ftp.state.SessionStateMachine;
import org.felix.netty.ftp.utils.CommandConstants;
import org.felix.netty.ftp.utils.Tools;

import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

import static org.felix.netty.ftp.utils.RetEnum.FILE_NOT_VALID;
import static org.felix.netty.ftp.utils.RetEnum.IVALID_COMMAND;

/**
 * key
 * get xxx
 */
public class DownloadService implements IFTPCommandService {

    public DownloadService() {
        init();
    }

    public void init() {
        CommandDispatchHandler.registed(CommandConstants.GET, this);
    }

    @Override
    public void execute(FTPCommand ftpCommand, ChannelHandlerContext context) {
        String filePath = ftpCommand.getParam();
        URL resource = Tools.getClassLoader().getResource(filePath);
        try {

//            String client = Tools.addressToStr(context.channel().remoteAddress());
//
//            SessionStateMachine stateMachine = StateHolder.getStateMachine(client);
//            ChannelHandlerContext clientChannel = null;
//            if (!Objects.isNull(stateMachine) && SessionStateMachine.READY_TRANSFORM == stateMachine.getState()) {
//                clientChannel = (ChannelHandlerContext) stateMachine.getExtData();
//            }
//            //illegal request for present state
//            if (Objects.isNull(clientChannel)) {
//                context.write(Tools.enumToMessageString(IVALID_COMMAND));
//                return;
//            }
//
//            RandomAccessFile randomAccessFile = new RandomAccessFile(resource.getPath(), "r");
//            FileChannel channel = randomAccessFile.getChannel();
//            long fileSize = channel.size();
//            int intFileSize = Long.valueOf(fileSize).intValue();
//            //FIXME:out of range
//            ByteBuffer byteBuffer = ByteBuffer.allocate(intFileSize);
//            int readCount = channel.read(byteBuffer);
//            if (readCount != intFileSize) {
//                context.write(Tools.enumToMessageString(FILE_NOT_VALID));
//                return;
//            }
//            clientChannel.write(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
