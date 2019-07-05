package sample.netty.chat.handler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sample.netty.chat.handler.SampleChatInfo;

public class SampleChatServerHandler extends SimpleChannelInboundHandler<SampleChatInfo> {

	private static final Logger logger = LoggerFactory.getLogger(SampleChatServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SampleChatInfo msg) throws Exception {
		logger.info("receive message:{}", new String(msg.getDatas()));
		
		SampleChatInfo info = new SampleChatInfo();
		info.setDatas(String.format("data already received in %d", System.currentTimeMillis()).getBytes());
		ctx.writeAndFlush(info);
	}

}
