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
import com.example.pasarYuk.firebase.PushNotificationRequest;
import com.example.pasarYuk.firebase.PushNotificationService;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Cart;
import com.example.pasarYuk.model.Order;
import com.example.pasarYuk.model.Orderitem;
import com.example.pasarYuk.model.OrderitemCkey;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.repository.CartRepository;
import com.example.pasarYuk.repository.MarketRepository;
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.OrderitemRepository;
import com.example.pasarYuk.repository.ProductRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.CartProductDTO;
import temp.LapakSection;
import temp.OrderAdminDTO;
import temp.OrderDTO;
import temp.OrderStaffDTO;
import temp.OrderitemDTO;

@Service
public class OrderService {

	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private PushNotificationService pushNotificationService;
	
//	
//	@Autowired
//	private CartService cartService;
//	
//	@Autowired
//	private ProductService productService;
	
	@Autowired
	private BuyerRepository BuyerRepository;
	
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
	
	public List<OrderAdminDTO> listOrderAdmin(){
		List<OrderAdminDTO> list = new ArrayList<OrderAdminDTO>();
		List<Order> order = orderRepository.findAll();
		
		for (Order order2 : order) {
			OrderAdminDTO temp = new OrderAdminDTO();
			temp.setOrderId(order2.getOrderId());
			temp.setOrderTimestamp(order2.getOrderTimestamp());
			temp.setOrderStatus(order2.getOrderStatus());
			temp.setShippingAddress(order2.getShippingAddress());
			temp.setMarketName(order2.getMarketName());
			
			List<Product> orderItemList = new ArrayList<Product>();
			orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
			long subTotal = calculateSubTotal(orderItemList);
			long total = subTotal + (order2.getShippingFee() - order2.getDiscountShipFee());
			temp.setTotal(total);
		}
		
		return list;
	}
	
	public Order getOrder(Long orderId) throws ResourceNotFoundException {
		return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found "));
	}
	
	public List<OrderitemDTO> getListOrderitem(Long orderId) throws ResourceNotFoundException{
		List<Orderitem> listItem = orderitemRepository.findByOrderId(orderId);
		List<OrderitemDTO> listOrderDTO = new ArrayList<OrderitemDTO>();
		
		for (Orderitem item : listItem) {
			OrderitemDTO temp = new OrderitemDTO();
			
			temp.setQuantity(item.getQuantity());
			
			long productId = item.getOrderitemId().getProductId();
			Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found "));
			temp.setProductId(product.getProductId());
			temp.setProductName(product.getProductName());
			temp.setSellerId(product.getSellerId());
			if(product.getPricePromo() != 0) {
				temp.setPrice(product.getPricePromo());
			}else {
				temp.setPrice(product.getPrice());
			}
			
			listOrderDTO.add(temp);
		}
		
		return listOrderDTO;
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
//				temp.setStaffId(order2.getStaffId());
				Staff staff = staffRepository.findById(order2.getStaffId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found "));
				temp.setStaff(staff);
//				temp.setOrderDate(order2.getOrderDate());
//				temp.setOrderTime(order2.getOrderTime());
				temp.setOrderTimestamp(order2.getOrderTimestamp());
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
	public OrderStaffDTO getOrderStaff(long staffId, String type) throws ResourceNotFoundException {
		Order order = new Order();
		String maxDate = orderRepository.findStaffLastOrderTimestamp(staffId, type);
		if(maxDate!=null) {
			order = orderRepository.findNewOrderWithIdStaff(staffId, maxDate, type);
		}else { 
			throw new ResourceNotFoundException("No Order For Now");
		}
		
		OrderStaffDTO temp = new OrderStaffDTO();
		String marketName = "";
		Buyer buyerDetail = new Buyer();
		
		
//		int flag=0;
		
//		for (Order order2 : order) {
			if (order != null) {
				temp.setOrderId(order.getOrderId());
				//temp.setBuyerId(order2.getBuyerId());
				temp.setStaffId(order.getStaffId());
//				temp.setOrderDate(order.getOrderDate());
//				temp.setOrderTime(order.getOrderTime());
				temp.setOrderTime(order.getOrderTimestamp());
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
//				return null;
				throw new ResourceNotFoundException("No Order For Now");
			}
//			flag++;
//			if(flag == 1) {
//				break;
//			}
//		}
			
		return temp;
	}
	public List<OrderStaffDTO> getListOrderPoStaff(long staffId, String type) throws ResourceNotFoundException {
		List<Order> listOrder = orderRepository.findPoOngoingOrderWithIdStaff(staffId);
		List<OrderStaffDTO> listTemp = new ArrayList<OrderStaffDTO>();
		String marketName = "";
		Buyer buyerDetail = new Buyer();
		
		OrderStaffDTO temp = new OrderStaffDTO();
//		int flag=0;
		
		if (listOrder != null) {
			for (Order order : listOrder) {
					temp.setOrderId(order.getOrderId());
					//temp.setBuyerId(order2.getBuyerId());
					temp.setStaffId(order.getStaffId());
	//				temp.setOrderDate(order.getOrderDate());
	//				temp.setOrderTime(order.getOrderTime());
					temp.setOrderTime(order.getOrderTimestamp());
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
				
					listTemp.add(temp);
					temp = new OrderStaffDTO();
	//			flag++;
	//			if(flag == 1) {
	//				break;
	//			}
			}
		}
		else {
			return null;
//			throw new ResourceNotFoundException("No Order For Now");
		}
			
		return listTemp;
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
	
	public Order newOrder(Long buyerId, String type) throws ResourceNotFoundException {
		Long marketIdTemp;
		Long staffIdNew = null;
		Order order = new Order();
		
//		Long[] listItem = orderItem.getList();
		
		
		order.setBuyerId(buyerId);
		Buyer tempBuyer = buyerService.getBuyerById(buyerId);
		order.setShippingAddress(tempBuyer.getAddress());
		order.setDiscountShipFee(0);
		order.setShippingFee(10000);
		order.setOrderStatus("01");
		order.setOrderType(type);
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		order.setOrderTimestamp(timeStamp);
		
		//FIND STAFF, CHECK IF STAFF HAVE A ONGOING ORDER OR NOT, IF NOT FIND ANOTHER STAFF
		List<Cart> listCart = cartRepository.findCheckedMarketByBuyerId(buyerId);
//		System.out.println(listCart);
		if(listCart!=null) {
			Cart temp = listCart.get(0);
			marketIdTemp = temp.getMarketId();
			String marketName = marketRepository.getMarketName(temp.getMarketId());
			order.setMarketName(marketName);
		}else {
			throw new ResourceNotFoundException("Buyer not have data in Cart");
		}
		//harusnya perlu sort ke staff yg last order ny paling lama
//		System.out.println(marketIdTemp);
		List<Staff> staff = staffRepository.findAllByMarketId(marketIdTemp);
//		System.out.println(staff); 
		String sendToken=null;
		if(staff!=null) {
			for (Staff staff2 : staff) {
//				System.out.println("masuk");
				if(type.equals("LIVE")) {
					if(staff2.getActive().equals("1") && staff2.getWorking().equals("0")) {
//						System.out.println("oke");
						staffIdNew = staff2.getStaffId();
						
						order.setStaffId(staffIdNew);
//						Staff updWorkingStaff = staffRepository.findById(staffIdNew).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
						staff2.setWorking("1");
						staff2.setLastorderTimestamp(timeStamp);
						staffRepository.save(staff2);
						sendToken = staff2.getToken();
						break;
					}
				}else if(type.equals("PO")) {
					if(staff2.getWorkingPo() >= 0 && staff2.getWorkingPo() < 2 && staff2.getActive().equals("1")) {
//						System.out.println("oke");
						staffIdNew = staff2.getStaffId();
						
						order.setStaffId(staffIdNew);
//						Staff updWorkingStaff = staffRepository.findById(staffIdNew).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
						staff2.setWorkingPo(staff2.getWorkingPo() + 1);
						staff2.setLastorderTimestamp(timeStamp);
						staffRepository.save(staff2);
						sendToken = staff2.getToken();
						break;
					}
				}
			}
		}else {
			throw new ResourceNotFoundException("No Staff Available for this Market");
		}
		if(staffIdNew==null) {
			throw new ResourceNotFoundException("No active staff for now");
		}
		
		
		//order.setShippingAddress(orderItem.getAddress());
		//int lengthList = listItem.length;
		
//		SimpleDateFormat time_format = new SimpleDateFormat("HHmmss");
//		String current_date = date_format.format(dateTemp);
//		order.setOrderDate(current_date);
//		String current_time = time_format.format(dateTemp);
//		order.setOrderTime(current_time);
		
		orderRepository.save(order);
		//return order;
		long orderIdTemp = order.getOrderId();
		List<Cart> cart = cartRepository.findCheckedItemByBuyerId(buyerId);
//		int i;
		if(cart!=null) {
//			i=0;
			for (Cart cart2 : cart) {
				int qtyTemp = cart2.getQuantity();
				long prodId = cart2.getCartId().getProductId();
				
				//cartService.deleteItemFromCartForOrder(buyerId, prodId);
				/////Product productTemp = productService.getProductById(listItem[i]);
				orderitemRepository.save(new Orderitem(new OrderitemCkey(orderIdTemp, prodId), qtyTemp));
//				if(i==0) {
//					String marketName = marketRepository.getMarketName(cart2.getMarketId());
//					order.setMarketName(marketName);
//				}
//				i++;
			}
		}else {
			throw new ResourceNotFoundException("Item not found in Cart");
		}
		
//		for (int i = 0; i < listItem.length; i++) {
//			//System.out.println(listItem[i]);
//			Cart cartTemp = cartService.findCart(buyerId, listItem[i]);
//			if(cartTemp != null) {
//				int qtyTemp = cartTemp.getQuantity();
//				
//				//cartService.deleteItemFromCartForOrder(buyerId, listItem[i]);
//				/////Product productTemp = productService.getProductById(listItem[i]);
//				orderitemRepository.save(new Orderitem(new OrderitemCkey(orderIdTemp, listItem[i]), qtyTemp));
//				if(i==0) {
//					String marketName = marketRepository.getMarketName(cartTemp.getMarketId());
//					order.setMarketName(marketName);
//				}
//			}else {
//				throw new ResourceNotFoundException("Item not found in Cart");
//			}
//		}
		//save market harusnya bisa dapet dari list cart sebelum save order di atas
//		orderRepository.save(order);
		
		if(sendToken != null) {
			PushNotificationRequest request = new PushNotificationRequest();
			request.setTitle("Orderan Baru !!");
			request.setMessage("Ada orderan nih, segera pastikan anda bisa memprosesnya!!");
			request.setToken(sendToken);
			request.setTopic("");
			pushNotificationService.sendPushNotificationToToken(request);
		}
		
		return order;
	}
	
	public Order updateOngoingStaffOrder(Long staffId, String type, Long orderId) throws ResourceNotFoundException {
		Long newStaffId = null;
		Long marketId=null;
		Long lastStaffId=null;
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		
		String sendToken=null;
		Buyer buyer = BuyerRepository.findById(order.getBuyerId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found "));
		sendToken = buyer.getToken();
		//		if(order.getStaffId() != 0) {
//			throw new ResourceNotFoundException("Order Already Assign to another Staff");
//		}
		//order.setStaffId(staffId);
		
		if(order!=null) {
			if(order.getOrderType().equals("LIVE")) {
				if(type.equals("accept")) {
					if(order.getOrderStatus().equals("01")) {
						order.setOrderStatus("02");
					}else {
						throw new ResourceNotFoundException("Order Status is not 01");
					}
				}else if(type.equals("decline")) {
					if(!order.getOrderStatus().equals("01")) {
						throw new ResourceNotFoundException("Order Status is not 01");
					}
					//update working staff yg decline jadi No
					Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
					//marketId = updWorkingStaff.getMarketId();
					//lastStaffId = updWorkingStaff.getStaffId();
					updWorkingStaff.setWorking("0");
					staffRepository.save(updWorkingStaff);
					order.setOrderStatus("05");
					
					//FIND FOW NEW STAFF
					//harusnya perlu sort ke staff yg last order ny paling lama
	//				List<Staff> staff = staffRepository.findAllByMarketId(marketId);
	//				if(staff != null) {
	//					for (Staff staff2 : staff) {
	//						if(staff2.getActive().equals("1") && staff2.getWorking().equals("0")) {
	//							newStaffId = staff2.getStaffId();
	//							break;
	//						}
	//					}
	//				}else {
	//					//IN CASE STAFF NY CUMAN 1 DAN DATANYA HILANG DARI MARKET ID INI, ORDER TTP JADI CANCEL
	//					order.setOrderStatus("05");
	////					throw new ResourceNotFoundException("No Staff Available for this Market");
	//				}
	//				//KALO ga ketemu STAFF lain, ORDER NY STATUS CANCELED
	//				if(newStaffId==null) {
	//					order.setOrderStatus("05");
	////					throw new ResourceNotFoundException("Cannot find staff for now");
	//				}else {
	//					//kalo staff yg ketemu sama lagi
	//					if(newStaffId == lastStaffId) {
	//						order.setOrderStatus("05");
	////						throw new ResourceNotFoundException("Cannot find staff for now");
	//					}else {
	//						order.setStaffId(newStaffId);
	//					}
	//				} 
				}else if(type.equals("update")) {
					String status = order.getOrderStatus();
					if(status.equals("04") || status.equals("05")) {
						throw new ResourceNotFoundException("Order already final status");
					}else if(status.equals("03")) {
						Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Cannot found staffId"));
						updWorkingStaff.setWorking("0");
						staffRepository.save(updWorkingStaff);
						order.setOrderStatus("04");
					}else if(status.equals("01")) { 
						throw new ResourceNotFoundException("Order is not accepted yet");
					}else{
						if(status.equals("02")) {
							order.setOrderStatus("03");
						}
	//					int statusInt = Integer.parseInt(status);
	//					statusInt++;
	//					String newStatus = String.valueOf(statusInt);
	//					order.setOrderStatus(newStatus);
					}
				}
			}else if(order.getOrderType().equals("PO")){
				if(type.equals("update")) {
					if(order.getOrderStatus().equals("01")) {
						throw new ResourceNotFoundException("Order is not accepted yet");
					}else if(order.getOrderStatus().equals("02")) {
						order.setOrderStatus("03");
					}else if(order.getOrderStatus().equals("03")) {
						Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
						updWorkingStaff.setWorkingPo(updWorkingStaff.getWorkingPo() - 1);
						staffRepository.save(updWorkingStaff);
						order.setOrderStatus("04");
					}else {
						throw new ResourceNotFoundException("Order is not valid to update");
					}
				}else if(type.equals("accept")) {
					if(order.getOrderStatus().equals("01")) {
						order.setOrderStatus("02");
					}else {
						throw new ResourceNotFoundException("Order Status is not 01");
					}
				}else if(type.equals("decline")) {
					if(!order.getOrderStatus().equals("01")) {
						throw new ResourceNotFoundException("Order Status is not 01");
					}
					Staff updWorkingStaff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Fail update Staff Working status"));
					updWorkingStaff.setWorkingPo(updWorkingStaff.getWorkingPo() - 1);
					staffRepository.save(updWorkingStaff);
					order.setOrderStatus("05");
				}
			}
		} 
		
		
		//hit api send notif, dengan message yg di isi dari if else di atas
		if(sendToken != null) {
			PushNotificationRequest request = new PushNotificationRequest();
			request.setTitle("Orderan Baru !!");
			request.setMessage("Ada orderan nih, segera pastikan anda bisa memprosesnya!!");
			request.setToken(sendToken);
			request.setTopic("");
			pushNotificationService.sendPushNotificationToToken(request);
		}
		
		return this.orderRepository.save(order);
	}
	
	public Order updateOrder(Long orderId, Order orderDetails) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		
		order.setBuyerId(orderDetails.getBuyerId());
		order.setStaffId(orderDetails.getStaffId());
//		order.setOrderDate(orderDetails.getOrderDate());
//		order.setOrderTime(orderDetails.getOrderTime());
		order.setOrderTimestamp(orderDetails.getOrderTimestamp());
		order.setOrderStatus(orderDetails.getOrderStatus());
		order.setShippingAddress(orderDetails.getShippingAddress());
		order.setShippingFee(orderDetails.getShippingFee());
		order.setDiscountShipFee(orderDetails.getDiscountShipFee());
		order.setMarketName(orderDetails.getMarketName());
		order.setOrderType(orderDetails.getOrderType());
		order.setOrderAcceptTime(orderDetails.getOrderAcceptTime());
		order.setOrderCollectTime(orderDetails.getOrderCollectTime());
		order.setOrderShipTime(orderDetails.getOrderShipTime());
		order.setOrderFinishTime(orderDetails.getOrderFinishTime());
		order.setOrderCancelTime(orderDetails.getOrderCancelTime());
		
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







////LIST ORDER FOR BUYER ongoing
//public List<OrderDTO> listOnGoingOrderBuyer(Long buyerId) throws ResourceNotFoundException{
//	//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findOngoingOrderWithBuyerId(buyerId);
//	
//	if(order != null) {
//		int i=0;
//		String marketName = "";
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
//			temp.setListItem(orderItemList);
//			
//			if(i==0) {
//				long sellerId = orderItemList.get(0).getSellerId();
//				Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//				marketName = marketRepository.getMarketName(seller.getMarketId());
//			}
//			temp.setMarketName(marketName);
//			
//			
//			orderDTOList.add(temp);
//			i++;
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found for this Buyer");
//	}
//	return orderDTOList;
//}
//
////LIST ORDER FOR BUYER history
//public List<OrderDTO> listHistoryOrderBuyer(Long buyerId) throws ResourceNotFoundException{
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findHistoryOrderWithBuyerId(buyerId);
//
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
//			temp.setListItem(orderItemList);
//			
//			long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
//
////LIST ORDER FOR SELLER ongoing
//public List<OrderDTO> listOnGoingOrderSeller(Long sellerId) throws ResourceNotFoundException{
//	//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findOngoingOrderWithSellerId(sellerId);
//	
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderIdForSellerId(order2.getOrderId(), sellerId);
//			temp.setListItem(orderItemList);
//			
//			//long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
//
////LIST ORDER FOR SELLER history
//public List<OrderDTO> listHistoryOrderSeller(Long sellerId) throws ResourceNotFoundException{
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findHistoryOrderWithSellerId(sellerId);
//
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderIdForSellerId(order2.getOrderId(), sellerId);
//			temp.setListItem(orderItemList);
//			
//			//long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
//
////LIST ORDER FOR STAFF ongoing
//public List<OrderDTO> listOnGoingOrderStaff(Long staffId) throws ResourceNotFoundException{
//	//Order order = (Order) orderRepository.findOngoingOrderWithBuyerId(buyerId);
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findOngoingOrderWithStaffId(staffId);
//	
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
//			temp.setListItem(orderItemList);
//			
//			long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//		//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
//
////LIST ORDER FOR STAFF history
//public List<OrderDTO> listHistoryOrderStaff(Long staffId) throws ResourceNotFoundException{
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	List<Order> order = orderRepository.findHistoryOrderWithStaffId(staffId);
//
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
//			temp.setListItem(orderItemList);
//			
//			long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
//
//public List<OrderDTO> getListOrder2(String type, String role, Long id) throws ResourceNotFoundException{
//	List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
//	if(role.equals("buyer")) {
//		if(type.equals("ongoing")) {
//			
//		}else {
//			
//		}
//		List<Order> order = orderRepository.findOngoingOrderWithBuyerId(id);
//	}
//	List<Order> order = orderRepository.findHistoryOrderWithStaffId(id);
//
//	if(order != null) {
//		for (Order order2 : order) {
//			OrderDTO temp = new OrderDTO();
//			temp.setOrderId(order2.getOrderId());
//			//temp.setBuyerId(order2.getBuyerId());
//			temp.setStaffId(order2.getStaffId());
//			temp.setOrderDate(order2.getOrderDate());
//			temp.setOrderTime(order2.getOrderTime());
//			temp.setOrderStatus(order2.getOrderStatus());
//			temp.setShippingAddress(order2.getShippingAddress());
//			List<Product> orderItemList = productRepository.getListItemWithOrderId(order2.getOrderId());
//			temp.setListItem(orderItemList);
//			
//			long sellerId = orderItemList.get(0).getSellerId();
//			Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//			String marketName = marketRepository.getMarketName(seller.getMarketId());
//			temp.setMarketName(marketName);
//			
//			orderDTOList.add(temp);
//		}
//	}
//	//System.out.println(orderDTOList.size());
//	if(orderDTOList.size() == 0) {
//		throw new ResourceNotFoundException("No data found");
//	}
//	return orderDTOList;
//}
