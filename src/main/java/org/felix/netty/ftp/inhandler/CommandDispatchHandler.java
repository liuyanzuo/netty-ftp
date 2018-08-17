package org.felix.netty.ftp.inhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.felix.netty.ftp.domain.FTPCommand;
import org.felix.netty.ftp.domain.IFTPCommandService;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandDispatchHandler extends ChannelInboundHandlerAdapter {

    private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();

    private static HashMap<String, IFTPCommandService> serviceTable = new HashMap<>();

    private static ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(CORE_COUNT, CORE_COUNT, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));

    public static void registed(String command, IFTPCommandService service) {
        serviceTable.put(command, service);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        InboundHandlerContext ftpContext = (InboundHandlerContext) msg;
        String command = ftpContext.getCommand().getCommand();
        bizThreadPool.submit(() -> serviceTable.get(command).execute(ctx,ftpContext));
    }
}
