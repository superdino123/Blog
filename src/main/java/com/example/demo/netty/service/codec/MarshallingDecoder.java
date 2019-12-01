package com.example.demo.netty.service.codec;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

/**
 * 解码服务类
 */
public class MarshallingDecoder {

	private final Unmarshaller unmarshaller;

	public MarshallingDecoder() throws Exception {
		unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
	}

	/**
	 * 解码
	 * 
	 * @param in 二进制数据流
	 * @return
	 * @throws Exception
	 */
	public Object decode(ByteBuf in) throws Exception {
		// 获取二进制流的大小
		int objectSize = in.readInt();
		// 获取全部二进制流，不移动readerIndex
		ByteBuf buf = in.slice(in.readerIndex(), objectSize);
		
		ByteInput input = new ChannelBufferByteInput(buf);
		try {
			// 解码
			unmarshaller.start(input);
			Object obj = unmarshaller.readObject();
			unmarshaller.finish();
			// 设置readerIndex到二进制流末尾，表示已读完
			in.readerIndex(in.readerIndex() + objectSize);
			return obj;
		} finally {
			unmarshaller.close();
		}
	}
}
