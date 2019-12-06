package com.diplant.blog.service;

import java.util.List;

import com.diplant.blog.entity.Article;

/**
 * 文章相关服务
 */
public interface ArticleService {

	public List<Article> selectArticles();
	
	public Article selectArticleById(String id);

	public int insertArticle(Article article);
}
