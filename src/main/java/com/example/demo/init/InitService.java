package com.example.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.netty.NettyServer;

@Order(value = 1)
@Component
public class InitService implements ApplicationRunner {

	@Autowired
	private NettyServer nettyServer;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 开启Netty服务端 - 长连接
		nettyServer.init();
	}

}
