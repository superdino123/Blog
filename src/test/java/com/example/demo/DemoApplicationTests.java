package com.example.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Article;
import com.example.demo.init.InitService;
import com.example.demo.service.ArticleService;

@SpringBootTest
public class DemoApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(InitService.class);

	@Autowired
	private ArticleService articleService;

	@Test
	void contextLoads() {
	}

	@Test
	void articleTest() {
		Article a = articleService.selectArticleById(1);
		logger.info(a.toString());
	}
}
