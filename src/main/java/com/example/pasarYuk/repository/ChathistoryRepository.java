package com.example.pasarYuk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Chathistory;

@Repository
public interface ChathistoryRepository extends JpaRepository<Chathistory, Long> {

	@Query(value = 
			"SELECT * "
			+ "FROM chathistory chs "
			+ "WHERE chs.chat_id_history=?1 "
			+ "ORDER BY chs.timestamp DESC"
			, nativeQuery = true)
	List<Chathistory> getHistoryBuyerId(long chatId);

}
