package org.felix.netty.ftp.outhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import static org.felix.netty.ftp.utils.Tools.LINE;

/**
 * FTP以换行符作为分割，并且以ASCII码编码
 */
public class LineDelimiterOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(Unpooled.copiedBuffer((String) msg, CharsetUtil.US_ASCII));
        ctx.write(Unpooled.copiedBuffer(LINE, CharsetUtil.US_ASCII));
        super.write(ctx, msg, promise);
    }

}
