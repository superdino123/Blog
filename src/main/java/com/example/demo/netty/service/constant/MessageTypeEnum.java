package com.example.demo.netty.service.constant;

/**
 * 消息类别
 * @author diplant
 */
public enum MessageTypeEnum {

	/**
	 * 心跳请求
	 */
	HEARTBEAT_REQ((byte) 1),
	/**
	 * 心跳反馈
	 */
	HEARTBEAT_RESP((byte) 2);

	private byte value;

	private MessageTypeEnum(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
}
