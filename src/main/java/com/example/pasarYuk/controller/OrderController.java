package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Order;
import com.example.pasarYuk.model.Orderitem;
import com.example.pasarYuk.services.OrderService;
import com.example.pasarYuk.services.OrderitemService;

import temp.ListItem;
import temp.OrderDTO;

@RestController
@RequestMapping("api/v1")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderitemService orderitemService;
	
	@GetMapping("/orders")
	public List<Order> getAllOrder(){
		return orderService.listOrder();
	}
	
	//get order on going for BUYER
	@GetMapping("/orders/list/ongoing/buyer/{buyerId}")
	public List<OrderDTO> getOnGoingOrderBuyer(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
		//harusnya pake session
		return orderService.listOnGoingOrderBuyer(buyerId);
	}

	//get order history for BUYER
	@GetMapping("/orders/list/history/buyer/{buyerId}")
	public List<OrderDTO> getHistoryOrderBuyer(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
		//harusnya pake session
		return orderService.listHistoryOrderBuyer(buyerId);
	}
	
	@GetMapping("/orders/list/ongoing/seller/{sellerId}")
	public List<OrderDTO> getOnGoingOrderSeller(@PathVariable(value = "sellerId") Long sellerId) throws ResourceNotFoundException{
		//harusnya pake session
		return orderService.listOnGoingOrderSeller(sellerId);
	}
	
	@GetMapping("/orders/list/history/seller/{sellerId}")
	public List<OrderDTO> getHistoryOrderSeller(@PathVariable(value = "sellerId") Long sellerId) throws ResourceNotFoundException{
		//harusnya pake session
		return orderService.listHistoryOrderSeller(sellerId);
	}
	
	//get on going list order for STAFF
	@GetMapping("/orders/list/ongoing/staff/{staffId}")
	public List<OrderDTO> getOnGoingOrderStaff(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException{
		return orderService.listOnGoingOrderStaff(staffId);
	}
	
	//get on going list order for STAFF
	@GetMapping("/orders/list/history/staff/{staffId}")
	public List<OrderDTO> getHistoryOrderStaff(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException{
		return orderService.listHistoryOrderStaff(staffId);
	}
	
	@GetMapping("/order4staff/{id}")
	public OrderDTO getListOrderStaff(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		OrderDTO temp = orderService.orderToConfirm(id);
		return temp;
	}
	
	@GetMapping("/order-list/{type}/{role}/{id}")
	public List<OrderDTO> getListOrder(@PathVariable(value = "type") String type, @PathVariable(value = "role") String role, @PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		List<OrderDTO> temp = orderService.getListOrder(type, role, id);
		return temp;
	}
	
	@PostMapping("/orders")
	public Order createOrder(@RequestBody Order order) {
		Order orderResp = orderService.createNewOrder(order);
		return orderResp;
	}
	
	//new order
	@PostMapping("/orders/new/{buyerId}")
	public Order newOrder(@PathVariable(value = "buyerId") Long buyerId, @RequestBody ListItem orderItem) throws ResourceNotFoundException {
		//sementara buyerId pake dari url dlu, kalo udah oke baru http session
		Order orderResp =  orderService.newOrder(buyerId, orderItem);
		return orderResp;
	}
	
	//accept order
	@PutMapping("/orders/accept/{orderId}/{staffId}")
	public ResponseEntity<Order> acceptOrder(@PathVariable(value = "orderId") Long orderId, @PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException{
		Order order = orderService.acceptOrder(orderId, staffId);
		return ResponseEntity.ok(order);
	}
	
	@PutMapping("orders/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "orderId") Long orderId, @Valid @RequestBody Order orderDetails) throws ResourceNotFoundException{
		Order order = orderService.updateOrder(orderId, orderDetails);
		return ResponseEntity.ok(order);
	}
	
	@DeleteMapping("orders/{orderId}")
	public Map<String, Boolean> deleteOrder(@PathVariable(value = "orderId") Long orderId) throws ResourceNotFoundException{
		Map<String, Boolean> response = orderService.deleteOrder(orderId);
		return response;
	}
	
	
}
