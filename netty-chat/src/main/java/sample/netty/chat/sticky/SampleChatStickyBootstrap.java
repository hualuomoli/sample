package sample.netty.chat.sticky;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import sample.netty.chat.sticky.handler.business.SampleChatStickyBusinessHandler;
import sample.netty.chat.sticky.handler.codec.SampleChatStickyDecoder;
import sample.netty.chat.sticky.handler.codec.SampleChatStickyEncoder;

public class SampleChatStickyBootstrap {

    private static final EventLoopGroup boos = new NioEventLoopGroup();
    private static final EventLoopGroup worker = new NioEventLoopGroup();

    private int port;

    public SampleChatStickyBootstrap(int port) {
        this.port = port;
    }

    // init
    public void init() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boos, worker)//
                .channel(NioServerSocketChannel.class)//
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline cp = ch.pipeline();
                        cp.addLast("decoder", new SampleChatStickyDecoder());
                        cp.addLast("encoder", new SampleChatStickyEncoder());
                        cp.addLast("server", new SampleChatStickyBusinessHandler());
                    }

                });

        ChannelFuture future = bootstrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    // destroy
    public void destroy() {
        boos.shutdownGracefully();
        worker.shutdownGracefully();
    }

}
