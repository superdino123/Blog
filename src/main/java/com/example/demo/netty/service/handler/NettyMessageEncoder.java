package com.example.demo.netty.service.handler;

import com.example.demo.netty.entity.NettyMessage;
import com.example.demo.netty.service.codec.MarshallingEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Netty消息编码服务
 * @author diplant
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

	private MarshallingEncoder marshallingEncoder;

	public NettyMessageEncoder() throws Exception {
		this.marshallingEncoder = new MarshallingEncoder();
	}

	/**
	 * 编码
	 */
	@Override
	public void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
		if (msg == null || msg.getHeader() == null) {
			throw new Exception("The encode message is null");
		}
		// header
		out.writeInt((msg.getHeader().getLength()));
		out.writeLong((msg.getHeader().getSessionId()));
		out.writeByte((msg.getHeader().getType()));
		out.writeByte((msg.getHeader().getPriority()));
		// body
		if (msg.getBody() != null) {
			marshallingEncoder.encode(msg.getBody(), out);
		} else {
			out.writeInt(0);
		}
		out.setInt(4, out.readableBytes() - 8);
	}

}
