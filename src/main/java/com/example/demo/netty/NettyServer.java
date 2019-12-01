package com.example.demo.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.netty.service.handler.HeartBeatHandler;
import com.example.demo.netty.service.handler.NettyMessageDecoder;
import com.example.demo.netty.service.handler.NettyMessageEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

@Component
public class NettyServer {

	private final int M = 1024 * 1024;

	/**
	 * 额外线程池
	 */
	private static final EventExecutorGroup EXECUTOR_GROUOP = new DefaultEventExecutorGroup(
			Runtime.getRuntime().availableProcessors() * 2);

	/**
	 * 调度线程组线程数
	 */
	@Value("${netty.bossGroupCount}")
	private String bossGroupCount;

	/**
	 * 工作线程组线程数
	 */
	@Value("${netty.workGroupCount}")
	private String workGroupCount;

	/**
	 * 接受连接请求队列长度 初始化服务端可连接队列,服务端处理客户端连接请求是顺序处理的
	 * 所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理 backlog参数指定了队列的大小
	 */
	@Value("${netty.nettyBackLog}")
	private String nettyBackLog;

	@Value("${netty.nettyServerPort}")
	private String nettyServerPort;

	/**
	 * 初始化服务端
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup(Integer.parseInt(bossGroupCount));
		EventLoopGroup workGroup = new NioEventLoopGroup(Integer.parseInt(workGroupCount));

		try {
			ServerBootstrap bs = new ServerBootstrap();
			// 配置线程组
			bs.group(bossGroup, workGroup)
					// 配置同步 or 异步
					.channel(NioServerSocketChannel.class)

					// option / handler/ attr 配置bossGroup线程池参数
					// 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128
					.option(ChannelOption.SO_BACKLOG, Integer.parseInt(nettyBackLog))
					// 允许重复使用本地地址和端口
					.option(ChannelOption.SO_REUSEADDR, true)
					// 操作接受缓冲区
					.option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 10)
					// 操作发送缓冲区
					.option(ChannelOption.SO_SNDBUF, 1024 * 1024 * 30)
					// Netty4使用对象池，重用缓冲区
					.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					// 高低水位设置
					.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64 * 1024))
					// 以指定级别打印日志 TODO 待了解
					.handler(new LoggingHandler(LogLevel.INFO))

					// childHandler / childOption / childAttr 配置workGroup线程池参数
					// Netty4使用对象池，重用缓冲区
					.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					// 设置工作线程组handler
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							// 流量整形設置 TODO 待了解
							GlobalChannelTrafficShapingHandler globalTrafficShapingHandler = new GlobalChannelTrafficShapingHandler(
									EXECUTOR_GROUOP, 200 * M, 200 * M, 10 * M, 10 * M, 60000, 60000);
							// 配置 事件链
							ChannelPipeline p = channel.pipeline();
							// 打印netty底层包
							p.addLast(new LoggingHandler(LogLevel.INFO));
							p.addLast(new NettyMessageEncoder());
							p.addLast(new NettyMessageDecoder(400 * M, 4, 4));
							p.addLast(globalTrafficShapingHandler);
							// 设置读取超时时间
							p.addLast(new ReadTimeoutHandler(240));
							p.addLast(new HeartBeatHandler());
						}
					});

			ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
			ChannelFuture future = bs.bind(Integer.valueOf(nettyServerPort)).sync();
			// 阻塞等待关闭
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO log
		} finally {
			bossGroup.shutdownGracefully().sync();
			workGroup.shutdownGracefully().sync();
		}
	}
}
