package com.example.pasarYuk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Orderitem;
import com.example.pasarYuk.model.OrderitemCkey;

@Repository
public interface OrderitemRepository extends JpaRepository<Orderitem, OrderitemCkey>{

	
}
