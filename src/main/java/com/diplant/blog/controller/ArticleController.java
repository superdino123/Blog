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
	public @ResponseBody Object articleList(String page) {
		try {
			List<Article> articles = null;
			if (page != null) {
				Integer pageInt = Integer.valueOf(page);
				articles = articleService.selectArticles(pageInt);
			} else {
				articles = articleService.selectArticles();
			}
			return articles;
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "获取文章列表",e);
			return null;
		}
	}

	@RequestMapping(value = "/articlePageSize", method = RequestMethod.GET)
	public @ResponseBody Object articlePageSize() {
		try {
			return articleService.getPageSize();
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "获取文章总页数",e);
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

	@RequestMapping(value = "/putArticle", method = RequestMethod.POST)
	public @ResponseBody Object articleById(@RequestBody Article article) {
		try {
			if (article.getId() == null) {
				article.setCreateTime(new Date());
				article.setModifyTime(new Date());
				article.setReadCount(1L);
				if(1 == articleService.insertArticle(article)) {
					return "insert ok";
				} else {
					return "insert false";
				}				
			} else {
				article.setModifyTime(new Date());
				if(1 == articleService.updateArticle(article)) {
					return "update ok";
				} else {
					return "update false";
				}
			}
		} catch (Throwable e) {
			log.error("[{}]: unknowed error", "添加/更新文章列表",e);
			return "false";
		}
	}
}
