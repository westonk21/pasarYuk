package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long newsId;

	@Column(name = "news_name")
	private String newsName;
	
	@Column(name = "news_desc")
	private String newsDesc;
	
	@Column(name = "start_date")
	private int startDate;
	
	@Column(name = "end_date")
	private int endDate;
	
	@Column(name = "url_image")
	private String urlImage;

	public News() {
		super();
	}

	public News(String newsName, String newsDesc, int startDate, int endDate, String urlImage) {
		super();
		this.newsName = newsName;
		this.newsDesc = newsDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.urlImage = urlImage;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public String getNewsDesc() {
		return newsDesc;
	}

	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public String getImageUrl() {
		return urlImage;
	}

	public void setImageUrl(String urlImage) {
		this.urlImage = urlImage;
	}
	
	
}
