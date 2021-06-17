package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{

}
