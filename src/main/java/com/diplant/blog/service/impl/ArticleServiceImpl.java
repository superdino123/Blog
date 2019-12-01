package com.diplant.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplant.blog.dao.ArticleDao;
import com.diplant.blog.entity.Article;
import com.diplant.blog.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public Article selectArticleById(String id) {
		return articleDao.selectArticleById(id);
	}

	@Override
	public int insertArticle(Article article) {
		return articleDao.insertArticle(article);
	}
}
