package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diplant.blog.entity.Article;
import com.diplant.blog.init.InitService;
import com.diplant.blog.service.ArticleService;

/**
 * 
 * @author diplant
 *
 */
@SpringBootTest
public class DemoApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(InitService.class);

	@Autowired
	private ArticleService articleService;
}
