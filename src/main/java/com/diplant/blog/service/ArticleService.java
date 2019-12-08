package com.diplant.blog.service;

import java.util.List;

import com.diplant.blog.entity.Article;

/**
 * 文章相关服务
 */
public interface ArticleService {

	public List<Article> selectArticles();

	public List<Article> selectArticles(Integer page);
	
	public String getPageSize();
	
	public Article selectArticleById(String id);

	public int insertArticle(Article article);

	public int updateArticle(Article article);
}
