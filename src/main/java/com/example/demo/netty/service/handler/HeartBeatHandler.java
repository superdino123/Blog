package com.example.demo.netty.service.handler;

import java.net.InetSocketAddress;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.demo.netty.entity.NettyMessage;
import com.example.demo.netty.service.constant.MessageTypeEnum;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 心跳处理服务
 */
@Sharable
@Component
public class HeartBeatHandler extends SimpleChannelInboundHandler<NettyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {

		// 判断标志位是否为心跳
		boolean isvalid = !ObjectUtils.isEmpty(msg) && !ObjectUtils.isEmpty(msg.getHeader())
				&& msg.getHeader().getType() == MessageTypeEnum.HEARTBEAT_REQ.getValue();
		if (isvalid) {
			InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

			// 如果channel可用，记录接受并发送心跳
			if (ctx.channel().isActive() && ctx.channel().isOpen()) {
				NettyMessage response = NettyMessage.buildHeartBeatMessage("username1");

				// TODO 写入心跳
				ChannelFuture channelFuture = ctx.writeAndFlush(response);
				channelFuture.addListener(new ChannelFutureListener() {

					@Override
					public void operationComplete(ChannelFuture future) {
						if (future.isSuccess()) {
							// TODO
							System.out.print("心跳反馈成功");
						} else {
							// TODO
							System.out.print("心跳反馈失败");
						}
					}
				});
			} else {
				// TODO
				System.out.print("channel不可用");
			}
		} else {
			// 沿事件链继续传递
			ctx.fireChannelRead(msg);
		}
	}

    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		try {
			// 获取客户端 IP, PORT
			InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
			// TODO
		} catch (Exception e) {
			// TODO
		}
	}
}
