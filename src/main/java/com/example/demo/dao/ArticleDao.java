package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Article;

@Mapper
public interface ArticleDao {

	public Article selectArticleById(Integer id);
}
