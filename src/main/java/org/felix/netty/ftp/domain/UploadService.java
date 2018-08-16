package org.felix.netty.ftp.domain;

import io.netty.channel.ChannelHandlerContext;
import org.felix.netty.ftp.utils.Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UploadService {

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Tools.CORE_COUNT,Tools.CORE_COUNT,0L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024));

    public void submit(ChannelHandlerContext ctx){

    }

}
