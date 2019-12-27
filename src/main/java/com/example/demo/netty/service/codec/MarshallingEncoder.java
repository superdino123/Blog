package com.example.demo.netty.service.codec;

import org.jboss.marshalling.Marshaller;

import io.netty.buffer.ByteBuf;

/**
 * 编码服务类
 * @author diplant
 */
public class MarshallingEncoder {

	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	private Marshaller marshaller;

	public MarshallingEncoder() throws Exception {
		marshaller = MarshallingCodecFactory.buildMarshalling();
	}

	/**
	 * 解码
	 * 
	 * @param msg 业务数据结构体
	 * @param out
	 * @throws Exception
	 */
	public void encode(Object msg, ByteBuf out) throws Exception {
		try {
			int lengthPos = out.writerIndex();
			out.writeBytes(LENGTH_PLACEHOLDER);
			ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
			// 编码
			marshaller.start(output);
			marshaller.writeObject(msg);
			marshaller.finish();
			out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
		} finally {
			marshaller.close();
		}
	}
}
