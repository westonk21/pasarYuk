package com.example.pasarYuk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{

	@Query(value=
			"SELECT * "
			+ "FROM guest ge "
			+ "WHERE ge.type=?1 AND ge.status NOT LIKE 'DECLINE'"
			, nativeQuery = true)
	List<Guest> listGuest(String type);
	
	@Query(value=
			"SELECT count(gu.guest_id) "
			+ "FROM guest gu "
			+ "WHERE gu.type='seller'"
			, nativeQuery = true)
	int getTotalGuestSeller();
	
	@Query(value=
			"SELECT count(gu.guest_id) "
			+ "FROM guest gu "
			+ "WHERE gu.type='staff'"
			, nativeQuery = true)
	int getTotalGuestStaff();
	
	@Query(value=
			"SELECT * "
			+ "FROM guest gu "
			+ "WHERE gu.email=?1 AND gu.type=?2"
			, nativeQuery = true)
	Guest findByEmail(String email, String type);
}
