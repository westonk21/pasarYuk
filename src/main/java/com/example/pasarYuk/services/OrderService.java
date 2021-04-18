package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Cart;
import com.example.pasarYuk.model.Order;
import com.example.pasarYuk.model.Orderitem;
import com.example.pasarYuk.model.OrderitemCkey;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.repository.MarketRepository;
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.OrderitemRepository;
import com.example.pasarYuk.repository.ProductRepository;
import com.example.pasarYuk.repository.SellerRepository;

import temp.ListItem;
import temp.OrderDTO;

@Service
public class OrderService {

	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private OrderitemRepository orderitemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private OrderRepository orderRepository;
	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
//======================================================================================================
//======================================================================================================
	public List<Order> listOrder(){
		return orderRepository.findAll();
	}
	
	//LIST ORDER FOR BUYER ongoing
	public List<OrderDTO> listOnGoingOrderBuyer(Long buyerId) throws ResourceNotFoundException{
		//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findOngoingOrderWithBuyerId(buyerId);
		
		if(order != null) {
			int i=0;
			String marketName = "";
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				temp.setListItem(orderItemList);
				
				if(i==0) {
					long sellerId = orderItemList.get(0).getSellerId();
					Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
					marketName = marketRepository.getMarketName(seller.getMarketId());
				}
				temp.setMarketName(marketName);
				
				
				orderDTOList.add(temp);
				i++;
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found for this Buyer");
		}
		return orderDTOList;
	}
	
	//LIST ORDER FOR BUYER history
	public List<OrderDTO> listHistoryOrderBuyer(Long buyerId) throws ResourceNotFoundException{
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findHistoryOrderWithBuyerId(buyerId);
	
		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				temp.setListItem(orderItemList);
				
				long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}
	
	//LIST ORDER FOR SELLER ongoing
	public List<OrderDTO> listOnGoingOrderSeller(Long sellerId) throws ResourceNotFoundException{
		//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findOngoingOrderWithSellerId(sellerId);
		
		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderIdForSellerId(order2.getOrderId(), sellerId);
				temp.setListItem(orderItemList);
				
				//long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}
	
	//LIST ORDER FOR SELLER history
	public List<OrderDTO> listHistoryOrderSeller(Long sellerId) throws ResourceNotFoundException{
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findHistoryOrderWithSellerId(sellerId);

		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderIdForSellerId(order2.getOrderId(), sellerId);
				temp.setListItem(orderItemList);
				
				//long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}

	//LIST ORDER FOR STAFF ongoing
	public List<OrderDTO> listOnGoingOrderStaff(Long staffId) throws ResourceNotFoundException{
		//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findOngoingOrderWithStaffId(staffId);
		
		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				temp.setListItem(orderItemList);
				
				long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
			//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}
	
	//LIST ORDER FOR STAFF history
	public List<OrderDTO> listHistoryOrderStaff(Long staffId) throws ResourceNotFoundException{
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = orderRepository.findHistoryOrderWithStaffId(staffId);
	
		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				temp.setListItem(orderItemList);
				
				long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}
	
	public List<OrderDTO> getListOrder2(String type, String role, Long id) throws ResourceNotFoundException{
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		if(role.equals("buyer")) {
			if(type.equals("ongoing")) {
				
			}else {
				
			}
			List<Order> order = orderRepository.findOngoingOrderWithBuyerId(id);
		}
		List<Order> order = orderRepository.findHistoryOrderWithStaffId(id);
	
		if(order != null) {
			for (Order order2 : order) {
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				temp.setListItem(orderItemList);
				
				long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				String marketName = marketRepository.getMarketName(seller.getMarketId());
				temp.setMarketName(marketName);
				
				orderDTOList.add(temp);
			}
		}
		//System.out.println(orderDTOList.size());
		if(orderDTOList.size() == 0) {
			throw new ResourceNotFoundException("No data found");
		}
		return orderDTOList;
	}
	
	public List<OrderDTO> getListOrder(String type, String role, Long id) throws ResourceNotFoundException{
		//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		List<Order> order = new ArrayList<Order>();
		
		if(type.equals("ongoing")) {
			if(role.equals("buyer")) {
				System.out.println("a");
				order = orderRepository.findOngoingOrderWithIdBuyer(id);
			}else if(role.equals("seller")) {
				System.out.println("b");
				order = orderRepository.findOngoingOrderWithIdSeller(id);
			}else if(role.equals("staff")) {
				System.out.println("c");
				order = orderRepository.findOngoingOrderWithIdStaff(id);
			}
		}else if(type.equals("history")) {
			if(role.equals("buyer")) {
				System.out.println("d");
				order = orderRepository.findHistoryOrderWithIdBuyer(id);
			}else if(role.equals("seller")) {
				System.out.println("e");
				order = orderRepository.findHistoryOrderWithIdSeller(id);
			}else if(role.equals("staff")) {
				System.out.println("f");
				order = orderRepository.findHistoryOrderWithIdStaff(id);
			}
		}
		if(order.size() == 0) {
			throw new ResourceNotFoundException("No data found for this ID");
		}
		
		//get list item
		if(order != null) {
			int i=0;
			String marketName = "";
			for (Order order2 : order) {
				System.out.println(i);
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order2.getStaffId());
				temp.setOrderDate(order2.getOrderDate());
				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderStatus(order2.getOrderStatus());
				temp.setShippingAddress(order2.getShippingAddress());
				List<Product> orderItemList = new ArrayList<Product>();
				if(role.equals("seller")) {
					orderItemList = productRepository.getListItemWithOrderIdForSellerId(order2.getOrderId(), id);
				}else {
					orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
				}

				temp.setListItem(orderItemList);
				
				if(i==0) {
					long sellerId;
					if(role.equals("seller")) {
						sellerId = id;
					}else {
						sellerId = orderItemList.get(0).getSellerId();
					}
					Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
					marketName = marketRepository.getMarketName(seller.getMarketId());
				}
				temp.setMarketName(marketName);
				
				
				orderDTOList.add(temp);
				i++;
			}
		}
		//System.out.println(orderDTOList.size());
//		if(orderDTOList.size() == 0) {
//			throw new ResourceNotFoundException("No data found for this Buyer");
//		}
		return orderDTOList;
	}	
	
	public Order getOrderById(Long orderId) throws ResourceNotFoundException{
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		return order;
	}
	
	public Order createNewOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order newOrder(Long buyerId, ListItem orderItem) throws ResourceNotFoundException {
		Order order = new Order();
		
		Long[] listItem = orderItem.getList();
		
		
		order.setBuyerId(buyerId);
		Buyer tempBuyer = buyerService.getBuyerById(buyerId);
		order.setShippingAddress(tempBuyer.getAddress());
		
		order.setOrderStatus("01");
		//order.setShippingAddress(orderItem.getAddress());
		//int lengthList = listItem.length;
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
		String current_date = date_format.format(dateTemp);
		order.setOrderDate(current_date);
		String current_time = time_format.format(dateTemp);
		order.setOrderTime(current_time);
		
		orderRepository.save(order);
		//return order;
		long orderIdTemp = order.getOrderId();
		
		for (int i = 0; i < listItem.length; i++) {
			//System.out.println(listItem[i]);
			Cart cartTemp = cartService.findCart(buyerId, listItem[i]);
			if(cartTemp != null) {
				int qtyTemp = cartTemp.getQuantity();
				
				//cartService.deleteItemFromCartForOrder(buyerId, listItem[i]);
				/////Product productTemp = productService.getProductById(listItem[i]);
				orderitemRepository.save(new Orderitem(new OrderitemCkey(orderIdTemp, listItem[i]), qtyTemp));
				if(i==0) {
					String marketName = marketRepository.getMarketName(cartTemp.getMarketId());
					order.setMarketName(marketName);
				}
			}else {
				throw new ResourceNotFoundException("Item not found in Cart");
			}
		}
		return order;
	}
	
	public Order acceptOrder(Long orderId, Long staffId) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		
		if(order.getStaffId() != 0) {
			throw new ResourceNotFoundException("Order Already Assign to another Staff");
		}
		order.setStaffId(staffId);
		
		return this.orderRepository.save(order);
	}
	
	public Order updateOrder(Long orderId, Order orderDetails) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		
		order.setBuyerId(orderDetails.getBuyerId());
		order.setStaffId(orderDetails.getStaffId());
		order.setOrderDate(orderDetails.getOrderDate());
		order.setOrderTime(orderDetails.getOrderTime());
		order.setOrderStatus(orderDetails.getOrderStatus());
		order.setShippingAddress(orderDetails.getShippingAddress());
		
		return this.orderRepository.save(order);
	}
	
	public Map<String, Boolean> deleteOrder(Long orderId) throws ResourceNotFoundException{
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + orderId));
		
		this.orderRepository.delete(order);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
}
