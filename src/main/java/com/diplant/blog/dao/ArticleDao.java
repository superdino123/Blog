package com.diplant.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.diplant.blog.entity.Article;

@Mapper
public interface ArticleDao {

	@Select(value="select * from di_article")
	public List<Article> selectArticles();

	@Select(value="select count(1) from di_article")
	public Integer selectArticleCount();

	@Select(value="select * from di_article order by id desc limit #{startId}, #{endId}")
	public List<Article> selectArticlesLimit(Integer startId, Integer endId);
	
	@Select(value="select * from di_article where id = #{id}")
	public Article selectArticleById(String id);
	
	public int insertArticle(@Param("article") Article article);

	public int updateArticle(@Param("article") Article article);
}
