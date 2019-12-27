package com.example.demo.netty.service.codec;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**
 * 编解码工厂
 * @author diplant
 */
public class MarshallingCodecFactory {

	/**
	 * 创建Jboss Marshaller 编码
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Marshaller buildMarshalling() throws Exception {
		// 获取编解码工厂
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
		return marshaller;
	}

	/**
	 * 创建Jboss Unmarshaller 解码
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Unmarshaller buildUnMarshalling() throws Exception {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
		return unmarshaller;
	}
}
