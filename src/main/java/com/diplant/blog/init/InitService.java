package com.diplant.blog.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * @author diplant
 *
 */
@Order(value = 1)
@Component
public class InitService implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}

}
