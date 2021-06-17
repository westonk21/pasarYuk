package com.example.pasarYuk.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.News;
import com.example.pasarYuk.repository.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;

	public List<News> listNews() {
		return newsRepository.findAll();
	}

	public News getNews(Long newsId) throws ResourceNotFoundException {
		return newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
	}

	public News addNewNews(News newsDetails) {
		// TODO Auto-generated method stub
		return newsRepository.save(newsDetails);
	}
	
	public News updateNews(Long newsId, News newsDtl) throws ResourceNotFoundException {
		News news = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
		
		news.setNewsName(newsDtl.getNewsName());
		news.setNewsDesc(newsDtl.getNewsDesc());
		news.setUrlImage(newsDtl.getUrlImage());
		news.setStartDate(newsDtl.getStartDate());
		news.setEndDate(newsDtl.getEndDate());
		
		return newsRepository.save(news);
	}

	public Map<String, Boolean> deleteNews(Long newsId) throws ResourceNotFoundException {
		News news = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
		newsRepository.delete(news);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}

	
}
