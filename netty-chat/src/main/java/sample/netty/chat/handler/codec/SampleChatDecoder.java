package sample.netty.chat.handler.codec;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import sample.netty.chat.handler.SampleChatInfo;

/**
 * 处理粘包,数据解码
 * 报文头 + 数据长度 + 数据
 * byte[2] + byte[2] + bytes
 * 如字节流数据: 7572000141 (16进制,两位表示一个字节)
 * 报文头: 7572
 * 数据长度: 01
 * 数据内容: 41(
 */
public class SampleChatDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(SampleChatDecoder.class);

    private static final byte[] HEADER = { 0x75, 0x72 }; // 报文头

    private static final int MIN_LENGTH = 2 + 2; // 报文头 + 数据长度
    private static final int MAX_LENGTH = 2048; // 最大长度,防止暴力攻击

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 数据未全部到达
        if (in.readableBytes() < MIN_LENGTH) {
            logger.debug("数据未全部到达......");
            return;
        }

        // 数据包太大
        if (in.readableBytes() >= MAX_LENGTH) {
            logger.warn("数据包太大,数据包大小:{}", in.readableBytes());
            in.skipBytes(in.readableBytes());
            return;
        }

        // 记录包头开始的index
        int beginReader = -1;
        byte[] header = new byte[2];

        // 查找数据头
        while (true) {
            // 获取包头开始的index
            beginReader = in.readerIndex();
            // 标记包头开始的index
            in.markReaderIndex();

            // 读到了协议的开始标志，结束while循环
            in.readBytes(header);
            if (Arrays.equals(header, HEADER)) {
                break;
            }

            // 未读到包头，略过一个字节
            // 每次略过，一个字节，去读取，包头信息的开始标记
            in.resetReaderIndex();
            in.readByte();

            // 当略过，一个字节之后，数据包的长度不足,等待后续数据
            if (in.readableBytes() < MIN_LENGTH) {
                logger.warn("非完整的数据包");
                return;
            }

        }

        // 消息
        int length = in.readBytes(2).readShort();

        // 读取数据
        // 判断请求数据包数据是否到齐
        if (in.readableBytes() < length) {
            logger.info("数据未全部到达,等待数据到达");
            // 还原读指针
            in.readerIndex(beginReader);
            return;
        }
        byte[] datas = new byte[length];
        in.readBytes(datas);

        // 添加到out
        SampleChatInfo info = new SampleChatInfo();
        info.setDatas(datas);
        out.add(info);
    }

}
