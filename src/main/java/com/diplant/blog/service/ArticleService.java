package com.diplant.blog.service;

import com.diplant.blog.entity.Article;

/**
 * 文章相关服务
 */
public interface ArticleService {

	public Article selectArticleById(String id);

	public int insertArticle(Article article);
}
