package com.example.demo.netty.service.handler;

import com.example.demo.netty.entity.Header;
import com.example.demo.netty.entity.NettyMessage;
import com.example.demo.netty.service.codec.MarshallingDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ReferenceCountUtil;

/**
 * Netty消息解码服务
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

	private MarshallingDecoder marshallingDecoder;
	
	/**
	 * Creates a new instance.
	 *
	 * @param maxFrameLength    消息的最大长度限制，如果长度超过这个限制，抛出{@link TooLongFrameException}错误
	 * @param lengthFieldOffset 字段的最大偏移量
	 * @param lengthFieldLength 字段的最大长度
	 * @throws Exception
	 */
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws Exception {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		marshallingDecoder = new MarshallingDecoder();
	}

	/**
	 * 解码
	 */
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// 解码
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
		    return null;
		}
		// 封装数据
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setLength(frame.readInt());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());

		if (frame.readableBytes() > 4) {
		    message.setBody(marshallingDecoder.decode(frame));
		}
		message.setHeader(header);
		ReferenceCountUtil.release(in);
		return message;
    }
}
