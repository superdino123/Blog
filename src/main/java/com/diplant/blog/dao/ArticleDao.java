package com.diplant.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.diplant.blog.entity.Article;

/**
 * 
 * @author diplant
 *
 */
@Mapper
public interface ArticleDao {

	/**
	 * 获取所有文章
	 * @return
	 */
	@Select(value="select * from di_article")
	public List<Article> selectArticles();

	/**
	 * 获取文章总数
	 * @return
	 */
	@Select(value="select count(1) from di_article")
	public Integer selectArticleCount();

	/**
	 * 获取文章（带分页）
	 * @param startId
	 * @param endId
	 * @return
	 */
	@Select(value="select * from di_article order by id desc limit #{startId}, #{endId}")
	public List<Article> selectArticlesLimit(Integer startId, Integer endId);
	
	/**
	 * 获取某篇文章
	 * @param id
	 * @return
	 */
	@Select(value="select * from di_article where id = #{id}")
	public Article selectArticleById(String id);
	
	/**
	 * 插入文章
	 * @param article
	 * @return
	 */
	public int insertArticle(@Param("article") Article article);

	/**
	 * 更新文章
	 * @param article
	 * @return
	 */
	public int updateArticle(@Param("article") Article article);
}
