package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.News;
import com.example.pasarYuk.services.NewsService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class NewsController {

	@Autowired
	private NewsService newsService;
	
	@GetMapping("/news")
	public List<News> getAllNews(){
		return newsService.listNews();
	}
	
	@GetMapping("/news/{newsId}")
	public News getNews(@PathVariable(value = "newsId") Long newsId) throws ResourceNotFoundException {
		return newsService.getNews(newsId);
	}
	
	@PostMapping("/news/new")
	public News createNews(@Valid @RequestBody News newsDetails) {
		News news = newsService.addNewNews(newsDetails);
		return news;
	}
	
	@PutMapping("/news/{newsId}")
	public ResponseEntity<News> updateNews(@PathVariable(value = "newsId") Long newsId, @Valid @RequestBody News newsDetails) throws ResourceNotFoundException{
		News news = newsService.updateNews(newsId, newsDetails);
		return ResponseEntity.ok(news);
	}
	
	@DeleteMapping("/news/{newsId}")
	public Map<String, Boolean> deleteNews(@PathVariable(value = "newsId") Long newsId) throws ResourceNotFoundException{
		Map<String, Boolean> response = newsService.deleteNews(newsId);
		return response;
	}
}
