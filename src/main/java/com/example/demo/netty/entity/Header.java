package com.example.demo.netty.entity;

import java.io.Serializable;

/**
 * Netty消息体头部信息
 */
public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息长度
	 */
    private int length;

	/**
	 * 会话ID
	 */
    private long sessionID;

    /**
     * 消息类型
     */
    private byte type;
    
    /**
     * 消息优先级
     */
    private byte priority;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}
}
