package com.diplant.blog.service;

import java.util.List;

import com.diplant.blog.entity.Article;

/**
 * 文章相关服务
 * @author diplant
 */
public interface ArticleService {

	/**
	 * 查找所有文章
	 * @return
	 */
	public List<Article> selectArticles();

	/**
	 * 查找某页的文章列表
	 * @param page
	 * @return
	 */
	public List<Article> selectArticles(Integer page);
	
	/**
	 * 获取总页数
	 * @return
	 */
	public String getPageSize();
	
	/**
	 * 根据ID搜索文章
	 * @param id
	 * @return
	 */
	public Article selectArticleById(String id);

	/**
	 * 插入文章
	 * @param article
	 * @return
	 */
	public int insertArticle(Article article);

	/**
	 * 更新文章
	 * @param article
	 * @return
	 */
	public int updateArticle(Article article);
}
