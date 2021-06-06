package com.example.pasarYuk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	@Query(value = 
			"SELECT * "
			+ "FROM chat ch "
			+ "WHERE ch.sender_id=?1 AND ch.type='SELLER' "
			+ "ORDER BY ch.last_timestamp DESC"
			, nativeQuery = true)
	public List<Chat> getChatListForBuyerId(Long buyerId);

	@Query(value = 
			"SELECT * "
			+ "FROM chat ch "
			+ "WHERE ch.sender_id=?1 AND ch.receiver_id=?2 AND ch.type=?3 "
			+ "ORDER BY ch.last_timestamp DESC"
			, nativeQuery = true)
	public Chat findByBuyerIdAndReceiverId(Long buyerId, Long rcvId, String type);
}
