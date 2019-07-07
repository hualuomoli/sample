package sample.netty.chat.check.handler.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sample.netty.chat.check.handler.entity.SampleChatCheckInfo;
import sample.netty.chat.sticky.handler.entity.SampleChatStickyInfo;

/**
 * 业务处理handler
 */
public class SampleChatCheckBusinessHandler extends SimpleChannelInboundHandler<SampleChatStickyInfo> {

    private static final Logger logger = LoggerFactory.getLogger(SampleChatCheckBusinessHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SampleChatStickyInfo msg) throws Exception {
        logger.info("receive message:{}", new String(msg.getDatas()));

        SampleChatCheckInfo info = new SampleChatCheckInfo();
        info.setDatas(String.format("data already received in %d", System.currentTimeMillis()).getBytes());
        ctx.writeAndFlush(info);
    }

}
