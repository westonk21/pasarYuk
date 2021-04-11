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
}
