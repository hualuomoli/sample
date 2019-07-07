package sample.netty.chat.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import sample.netty.chat.check.handler.business.SampleChatCheckBusinessHandler;
import sample.netty.chat.check.handler.codec.SampleChatCheckDecoder;
import sample.netty.chat.check.handler.codec.SampleChatCheckEncoder;

public class SampleChatCheckBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(SampleChatCheckBootstrap.class);

    private static final EventLoopGroup boos = new NioEventLoopGroup();
    private static final EventLoopGroup worker = new NioEventLoopGroup();

    /**
     * 启动服务
     * @param port 绑定端口
     */
    public static void start(final int port) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boos, worker)//
                    .channel(NioServerSocketChannel.class)//
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline cp = ch.pipeline();
                            cp.addLast("decoder", new SampleChatCheckDecoder());
                            cp.addLast("encoder", new SampleChatCheckEncoder());
                            cp.addLast("server", new SampleChatCheckBusinessHandler());
                        }

                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("启动check的netty失败", e);
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
