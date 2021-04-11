package com.example.pasarYuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.repository.OrderitemRepository;

@Service
public class OrderitemService {

	private OrderitemRepository orderitemRepository;
	@Autowired
	public OrderitemService(OrderitemRepository orderitemRepository) {
		this.orderitemRepository = orderitemRepository;
	}
	
}
