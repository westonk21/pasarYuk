package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.ListOTP;

@Repository
public interface ListOtpRepository extends  JpaRepository<ListOTP, Long>{

	@Query(value="SELECT * FROM listotp WHERE email=?1 AND type=?2", nativeQuery = true)
	ListOTP findByEmailAndType(String email, String type);

}
