package com.example.demo.service;

import com.example.demo.entity.Article;

/**
 * 文章相关服务
 */
public interface ArticleService {

	public Article selectArticleById(Integer id);
}
