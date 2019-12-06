package com.diplant.blog.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diplant.blog.entity.Article;
import com.diplant.blog.service.ArticleService;

@RequestMapping(value = "/Article")
@Controller
public class ArticleController {

	private static Logger log = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public @ResponseBody Object articleList() {
		try {
			List<Article> articles = articleService.selectArticles();
			return articles;
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "获取文章列表",e);
			return null;
		}
	}
	
	@RequestMapping(value = "/articleById", method = RequestMethod.GET)
	public @ResponseBody Object articleById(@RequestParam String id) {
		try {
			Article article = articleService.selectArticleById(id);
			return article;
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "获取文章列表",e);
			return false;
		}
	}

	@RequestMapping(value = "/addArticle", method = RequestMethod.POST)
	public @ResponseBody Object articleById(@RequestBody Article article) {
		try {
			article.setCreateTime(new Date());
			article.setModifyTime(new Date());
			article.setReadCount(1L);
			if(1 == articleService.insertArticle(article)) {
				return "ok";
			} else {
				return "false";
			}
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "添加文章列表",e);
			return "false";
		}
	}
}
