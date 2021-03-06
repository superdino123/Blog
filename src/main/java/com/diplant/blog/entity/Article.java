package com.diplant.blog.entity;

import java.util.Date;

/**
 * 文章结构
 * @author diplant
 */
public class Article {

	/**
	 * 文章ID
	 */
	private Integer id;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	private Date modifyTime;

	/**
	 * 文章正文
	 */
	private String content;

	/**
	 * 阅读数
	 */
	private Long readCount;
	
	/**
	 * 摘要
	 */
	private String articleDesc;

	@Override
	public String toString() {
		return "article={\"id\"=" + id + "," + "\"title\"=" + title + "," + "\"createTime\"=" + createTime + ","
				+ "\"modifyTime\"=" + modifyTime + "," + "\"content\"=" + content + "," + "\"readCount\"=" + readCount
				+ "}";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}
}
