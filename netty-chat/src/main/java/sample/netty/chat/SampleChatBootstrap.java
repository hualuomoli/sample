package sample.netty.chat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import sample.netty.chat.handler.codec.SampleChatDecoder;
import sample.netty.chat.handler.codec.SampleChatEncoder;
import sample.netty.chat.handler.server.SampleChatServerHandler;

@Component
public class SampleChatBootstrap {

	EventLoopGroup boos = new NioEventLoopGroup();
	EventLoopGroup worker = new NioEventLoopGroup();

	@PostConstruct
	public void init() throws Exception {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boos, worker)//
				.channel(NioServerSocketChannel.class)//
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline cp = ch.pipeline();
						cp.addLast("decoder", new SampleChatDecoder());
						cp.addLast("encoder", new SampleChatEncoder());
						cp.addLast("server", new SampleChatServerHandler());
					}

				});

		ChannelFuture future = bootstrap.bind(8888).sync();
		future.channel().closeFuture().sync();
	}

	@PreDestroy
	public void destroy() {
		boos.shutdownGracefully();
		worker.shutdownGracefully();
	}

}
