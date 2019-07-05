package sample.netty.chat.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import sample.netty.chat.handler.SampleChatInfo;

public class SampleChatEncoder extends MessageToByteEncoder<SampleChatInfo> {

	private static final byte[] HEADER = { 0x75, 0x72 }; // 报文头

	@Override
	protected void encode(ChannelHandlerContext ctx, SampleChatInfo msg, ByteBuf out) throws Exception {
		if (msg == null) {
			return;
		}

		out.writeBytes(HEADER);
		out.writeShort(msg.getDatas().length);
		out.writeBytes(msg.getDatas());
	}

}
