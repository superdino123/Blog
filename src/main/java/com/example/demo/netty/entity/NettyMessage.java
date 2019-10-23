package com.example.demo.netty.entity;

import java.io.Serializable;

import com.example.demo.netty.service.constant.MessageTypeEnum;

/**
 * Netty消息编解码
 * @author Administrator
 *
 */
public final class NettyMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户相关信息， 标识结构体属于哪一用户，不做为二进制流在管道中传递
	 */
	private TabCuRegist user;
	
	/**
	 * 业务无关的标识数据
	 */
	private Header header;
	
	/**
	 * 业务数据
	 */
	private Object body;

	/**
	 * 封装心跳数据包 
	 * @param userName
	 * @return
	 */
	public static NettyMessage buildHeartBeatMessage(String userName) {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(MessageTypeEnum.HEARTBEAT_RESP.getValue());
		msg.setHeader(header);
		TabCuRegist user = new TabCuRegist();
		user.setUserName(userName);
		msg.setUser(user);
		return msg;
	}
	
	public TabCuRegist getUser() {
		return user;
	}

	public void setUser(TabCuRegist user) {
		this.user = user;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
}
