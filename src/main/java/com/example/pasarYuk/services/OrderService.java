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
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.CartRepository;
import com.example.pasarYuk.repository.MarketRepository;
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.OrderitemRepository;
import com.example.pasarYuk.repository.ProductRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.CartProductDTO;
import temp.LapakSection;
import temp.ListItem;
import temp.OrderDTO;
import temp.OrderStaffDTO;

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
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
				//temp.setBuyerId(order2.getBuyerId());
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
			throw new ResourceNotFoundException("No data found for this ID : " + id);
		}
		
		//get list item
		
		if(order != null) {
			//int i=0;
			String marketName = "";
			Buyer buyerDetail = new Buyer();
			for (Order order2 : order) {
				//System.out.println(i);
				OrderDTO temp = new OrderDTO();
				temp.setOrderId(order2.getOrderId());
				//temp.setBuyerId(order2.getBuyerId());
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
//					System.out.println();
				}
//				System.out.println(orderItemList);
				temp.setListItem(orderItemList);
				
				temp.setShippingFee(order2.getShippingFee());
				temp.setDiscountShipFee(order2.getDiscountShipFee());
				long subTotal = calculateSubTotal(orderItemList);
				temp.setSubTotal(subTotal);
				long total = subTotal + (order2.getShippingFee() - order2.getDiscountShipFee());
				temp.setTotal(total);
				
				
				//if(i==0) {
					long sellerId;
					if(role.equals("seller")) {
						sellerId = id;
					}else {
						sellerId = orderItemList.get(0).getSellerId();
					}
					Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
					marketName = marketRepository.getMarketName(seller.getMarketId());
					buyerDetail = buyerService.getBuyerById(order2.getBuyerId());
				//}
				temp.setMarketName(marketName);
				temp.setBuyerDetail(buyerDetail);
				
				
				orderDTOList.add(temp);
				//i++;
			}
		}
		//System.out.println(orderDTOList.size());
//		if(orderDTOList.size() == 0) {
//			throw new ResourceNotFoundException("No data found for this Buyer");
//		}
		return orderDTOList;
	}	
	
//	public OrderDTO getOrderStaff(long staffId) throws ResourceNotFoundException {
//		Order order = orderRepository.findNewOrderWithIdStaff(staffId);
//		
//		OrderDTO temp = new OrderDTO();
//		String marketName = "";
//		Buyer buyerDetail = new Buyer();
////		int flag=0;
//		
////		for (Order order2 : order) {
//			if (order != null) {
//				temp.setOrderId(order.getOrderId());
//				//temp.setBuyerId(order2.getBuyerId());
//				temp.setStaffId(order.getStaffId());
//				temp.setOrderDate(order.getOrderDate());
//				temp.setOrderTime(order.getOrderTime());
//				temp.setOrderStatus(order.getOrderStatus());
//				temp.setShippingAddress(order.getShippingAddress());
//				
//				List<Product> orderItemList = new ArrayList<Product>();
//				orderItemList = productRepository.getListItemWithOrderId(order.getOrderId());
//				temp.setListItem(orderItemList);
//				
//				temp.setShippingFee(order.getShippingFee());
//				temp.setDiscountShipFee(order.getDiscountShipFee());
//				long subTotal = calculateSubTotal(orderItemList);
//				temp.setSubTotal(subTotal);
//				long total = subTotal - (order.getShippingFee() - order.getDiscountShipFee());
//				temp.setTotal(total);
//				
//				long sellerId = orderItemList.get(0).getSellerId();
//				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//				marketName = marketRepository.getMarketName(seller.getMarketId());
//				buyerDetail = buyerService.getBuyerById(order.getBuyerId());
//				
//				temp.setMarketName(marketName);
//				temp.setBuyerDetail(buyerDetail);
//			}else {
//				return null;
//			}
////			flag++;
////			if(flag == 1) {
////				break;
////			}
////		}
//			
//		return temp;
//	}
	public OrderStaffDTO getOrderStaff(long staffId) throws ResourceNotFoundException {
		Order order = orderRepository.findNewOrderWithIdStaff(staffId);
		
		OrderStaffDTO temp = new OrderStaffDTO();
		String marketName = "";
		Buyer buyerDetail = new Buyer();
		
		
//		int flag=0;
		
//		for (Order order2 : order) {
			if (order != null) {
				temp.setOrderId(order.getOrderId());
				//temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order.getStaffId());
				temp.setOrderDate(order.getOrderDate());
				temp.setOrderTime(order.getOrderTime());
				temp.setOrderStatus(order.getOrderStatus());
				temp.setShippingAddress(order.getShippingAddress());
				
				List<Product> orderItemList = new ArrayList<Product>();
				orderItemList = productRepository.getListItemWithOrderIdSortName(order.getOrderId());
				
				System.out.println(orderItemList.size());
				if(orderItemList.size() > 0) {
//					temp.setListItem(orderItemList);
				
					List<LapakSection> listLapak = new ArrayList<LapakSection>();
					List<CartProductDTO> listProduct = new ArrayList<CartProductDTO>();
					int i=1;
					int lengthList = orderItemList.size();
					Long sellerId = null;
					String lapakNameTmp = null;
					for (Product product : orderItemList) {
						if(i==1) {
							Seller tempSl = sellerRepository.findById(product.getSellerId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + product.getSellerId()));
							sellerId = product.getSellerId();
							lapakNameTmp = tempSl.getLapakName();
//							LapakSection lapak = new LapakSection();
//							
//							lapak.setLapakName(tempSl.getLapakName());
//							
//							List<CartProductDTO> listProduct = new ArrayList<CartProductDTO>();
//							i++;
//							continue;
						}
						if(product.getSellerId() != sellerId) {
							//System.out.println(listProduct.get(0));
							LapakSection lapak = new LapakSection();
							lapak.setLapakName(lapakNameTmp);
							lapak.setData(listProduct);
							listLapak.add(lapak);
							
							Seller tempSl = sellerRepository.findById(product.getSellerId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + product.getSellerId()));
							sellerId = product.getSellerId();
							lapakNameTmp = tempSl.getLapakName();
						}
						
						CartProductDTO res = new CartProductDTO();
						res.setProductId(product.getProductId());
						res.setPrice(product.getPrice());
						res.setProductName(product.getProductName());
						res.setUrlProductImage(product.getUrlProductImage());
						Orderitem oiTemp = orderitemRepository.findById(new OrderitemCkey(order.getOrderId(), product.getProductId())).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: "));
						res.setQuantity(oiTemp.getQuantity());
						listProduct.add(res);	
						
						if(i == lengthList) {
							LapakSection lapak = new LapakSection();
							lapak.setLapakName(lapakNameTmp);
							lapak.setData(listProduct);
							listLapak.add(lapak);
						}
												
						i++;
					}
					temp.setListItem(listLapak);
				}else {
					throw new ResourceNotFoundException("Item list is empty for orderId : "+ order.getOrderId());
				}
				
				temp.setShippingFee(order.getShippingFee());
				temp.setDiscountShipFee(order.getDiscountShipFee());
				long subTotal = calculateSubTotal(orderItemList);
				temp.setSubTotal(subTotal);
				long total = subTotal - (order.getShippingFee() - order.getDiscountShipFee());
				temp.setTotal(total);
				
				long sellerId = orderItemList.get(0).getSellerId();
				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
				marketName = marketRepository.getMarketName(seller.getMarketId());
				buyerDetail = buyerService.getBuyerById(order.getBuyerId());
				
				temp.setMarketName(marketName);
				temp.setBuyerDetail(buyerDetail);
			}else {
				return null;
			}
//			flag++;
//			if(flag == 1) {
//				break;
//			}
//		}
			
		return temp;
	}
	
	public long calculateSubTotal(List<Product> listItem) {
		Iterator<Product> iterator = listItem.iterator();
		long total=0;
	    while(iterator.hasNext()) {
//	    	System.out.println(iterator.next());
	    	total = total + iterator.next().getPrice();
	    }
		return total;
	}
	
	public Order getOrderById(Long orderId) throws ResourceNotFoundException{
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		return order;
	}
	
	public Order createNewOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order newOrder(Long buyerId, ListItem orderItem) throws ResourceNotFoundException {
		Long marketIdTemp;
		Long staffIdNew = null;
		Order order = new Order();
		
		Long[] listItem = orderItem.getList();
		
		
		order.setBuyerId(buyerId);
		Buyer tempBuyer = buyerService.getBuyerById(buyerId);
		order.setShippingAddress(tempBuyer.getAddress());
		order.setDiscountShipFee(0);
		order.setShippingFee(10000);
		order.setOrderStatus("01");
		
		//FIND STAFF, CHECK IF STAFF HAVE A ONGOING ORDER OR NOT, IF NOT FIND ANOTHER STAFF
		List<Cart> listCart = cartRepository.findCheckedMarketByBuyerId(buyerId);
		System.out.println(listCart);
		if(listCart!=null) {
			Cart temp = listCart.get(0);
			marketIdTemp = temp.getMarketId();
		}else {
			throw new ResourceNotFoundException("Buyer not have data in Cart");
		}
		//harusnya perlu sort ke staff yg last order ny paling lama
		System.out.println(marketIdTemp);
		List<Staff> staff = staffRepository.findAllByMarketId(marketIdTemp);
		System.out.println(staff);
		if(staff!=null) {
			for (Staff staff2 : staff) {
				System.out.println("masuk");
				if(staff2.getActive().equals("1") && staff2.getWorking().equals("0")) {
					System.out.println("oke");
					staffIdNew = staff2.getStaffId();
					break;
				}
			}
		}else {
			throw new ResourceNotFoundException("No Staff Available for this Market");
		}
		if(staffIdNew==null) {
			throw new ResourceNotFoundException("No active staff for now");
		}else {
			order.setStaffId(staffIdNew);
			//update working ny staff = yes
			Staff updWorkingStaff = staffRepository.findById(staffIdNew).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
			updWorkingStaff.setWorking("1");
			staffRepository.save(updWorkingStaff);
		}
		
		
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
		orderRepository.save(order);
		return order;
	}
	
	public Order updateOngoingStaffOrder(Long staffId, String type, Long orderId) throws ResourceNotFoundException {
		Long newStaffId = null;
		Long marketId=null;
		Long lastStaffId=null;
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		
//		if(order.getStaffId() != 0) {
//			throw new ResourceNotFoundException("Order Already Assign to another Staff");
//		}
		//order.setStaffId(staffId);
		
		if(order!=null) {
			if(type.equals("accept") && order.getOrderStatus().equals("01")) {
				order.setOrderStatus("02");
			}else if(type.equals("decline") && order.getOrderStatus().equals("01")) {
				//update working staff yg decline jadi No
				Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
				marketId = updWorkingStaff.getMarketId();
				lastStaffId = updWorkingStaff.getStaffId();
				updWorkingStaff.setWorking("0");
				staffRepository.save(updWorkingStaff);
				
				//FIND FOW NEW STAFF
				//harusnya perlu sort ke staff yg last order ny paling lama
				List<Staff> staff = staffRepository.findAllByMarketId(marketId);
				if(staff != null) {
					for (Staff staff2 : staff) {
						if(staff2.getActive().equals("1") && staff2.getWorking().equals("0")) {
							newStaffId = staff2.getStaffId();
							break;
						}
					}
				}else {
					//IN CASE STAFF NY CUMAN 1 DAN DATANYA HILANG DARI MARKET ID INI, ORDER TTP JADI CANCEL
					order.setOrderStatus("05");
//					throw new ResourceNotFoundException("No Staff Available for this Market");
				}
				//KALO ga ketemu STAFF lain, ORDER NY STATUS CANCELED
				if(newStaffId==null) {
					order.setOrderStatus("05");
//					throw new ResourceNotFoundException("Cannot find staff for now");
				}else {
					//kalo staff yg ketemu sama lagi
					if(newStaffId == lastStaffId) {
						order.setOrderStatus("05");
//						throw new ResourceNotFoundException("Cannot find staff for now");
					}else {
						order.setStaffId(newStaffId);
					}
				}
			}else if(type.equals("update")) {
				String status = order.getOrderStatus();
				if(status.equals("04") || status.equals("05")) {
					throw new ResourceNotFoundException("Order already final status");
				}else if(status.equals("03")) {
					Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Cannot found staffId"));
					updWorkingStaff.setWorking("0");
					staffRepository.save(updWorkingStaff);
					order.setOrderStatus("04");
				}else {
					int statusInt = Integer.parseInt(status);
					statusInt++;
					String newStatus = String.valueOf(statusInt);
					order.setOrderStatus(newStatus);
				}
			}
		}
		
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
		order.setShippingFee(orderDetails.getShippingFee());
		order.setDiscountShipFee(orderDetails.getDiscountShipFee());
		order.setMarketName(orderDetails.getMarketName());
		
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
