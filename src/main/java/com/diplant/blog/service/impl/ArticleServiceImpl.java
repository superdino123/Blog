package com.diplant.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplant.blog.dao.ArticleDao;
import com.diplant.blog.entity.Article;
import com.diplant.blog.service.ArticleService;

/**
 * 
 * @author diplant
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	private Integer pageSize = 10;
	
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

	@Override
	public List<Article> selectArticles() {
		return articleDao.selectArticles();
	}

	@Override
	public List<Article> selectArticles(Integer page) {
		Integer startId = (page - 1) * pageSize;
		Integer endId = page * pageSize;
		return articleDao.selectArticlesLimit(startId, endId);
	}

	@Override
	public String getPageSize() {
		Integer count = articleDao.selectArticleCount();
		return String.valueOf(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
	}

	@Override
	public int updateArticle(Article article) {
		return articleDao.updateArticle(article);
	}
}
