package sample.netty.chat.sticky.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import sample.netty.chat.sticky.handler.entity.SampleChatStickyInfo;

/**
 * 数据编码
 */
public class SampleChatStickyEncoder extends MessageToByteEncoder<SampleChatStickyInfo> {

    private static final byte[] HEADER = { 0x75, 0x72 }; // 报文头

    @Override
    protected void encode(ChannelHandlerContext ctx, SampleChatStickyInfo msg, ByteBuf out) throws Exception {
        if (msg == null) {
            return;
        }

        out.writeBytes(HEADER);
        out.writeShort(msg.getDatas().length);
        out.writeBytes(msg.getDatas());
    }

}
