package com.diplant.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.diplant.blog.entity.Article;

@Mapper
public interface ArticleDao {

	@Select(value="SELECT * FROM di_article WHERE id = #{id}")
	public Article selectArticleById(String id);
	
	public int insertArticle(@Param("article") Article article);
}
