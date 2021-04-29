package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	@Query(value=
			"SELECT count(st.staff_id) "
			+ "FROM staff st"
			, nativeQuery = true)
	int getTotalStaff();
}
