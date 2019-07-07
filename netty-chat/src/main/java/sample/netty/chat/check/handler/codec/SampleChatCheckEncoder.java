package sample.netty.chat.check.handler.codec;

import org.apache.commons.codec.digest.DigestUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import sample.netty.chat.check.handler.entity.SampleChatCheckInfo;

/**
 * 数据编码
 */
public class SampleChatCheckEncoder extends MessageToByteEncoder<SampleChatCheckInfo> {

    private static final byte[] HEADER = { 0x75, 0x72 }; // 报文头

    @Override
    protected void encode(ChannelHandlerContext ctx, SampleChatCheckInfo msg, ByteBuf out) throws Exception {
        if (msg == null) {
            return;
        }

        out.writeBytes(HEADER);
        out.writeShort(msg.getDatas().length);
        out.writeBytes(msg.getDatas());
        out.writeBytes(DigestUtils.md5(msg.getDatas()));
    }

}
