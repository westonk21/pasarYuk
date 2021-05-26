package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.ListOTP;

@Repository
public interface ListOtpRepository extends  JpaRepository<ListOTP, Long>{

	ListOTP findByEmail();

}
