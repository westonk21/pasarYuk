package com.example.pasarYuk.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Cart;
import com.example.pasarYuk.model.CartCkey;
import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.repository.CartRepository;
import com.example.pasarYuk.repository.MarketRepository;
import com.example.pasarYuk.repository.ProductRepository;

import temp.CartDTO;
import temp.CartMarketDTO;
import temp.CartProductDTO;
import temp.PaymentDTO;

@Service
public class CartService {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private CartRepository cartRepository;
	
	@Autowired
	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	//-----------------------------------------------------------------------------
	public List<CartDTO> viewCartByMarket(Long buyerId) throws ResourceNotFoundException{
		//query cart dengan ID BUYER nya = buyerId dari session
		List<Cart> cart = cartRepository.findByBuyerId(buyerId);
		
		List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
		CartDTO temp = new CartDTO();
		
		List<CartProductDTO> listProductDTO = new ArrayList<CartProductDTO>();
		long marketId=0;
		String marketCheck = null;
		int flag=1;
		int lengthList = cart.size();
//		System.out.println("jumlah item : " + lengthList);
		if(cart != null) {
			for(Cart cart2 : cart) {
				
				//marketId = cart2.getMarketId();
				if(flag == 1) {
					marketId = cart2.getMarketId();
					marketCheck = cart2.getCheckMarket();
				}
//				System.out.println(cart2.getCartId().getProductId() + " " + marketId);
				if(cart2.getMarketId() != marketId ) {
//					System.out.println("create 1 data");
					Market tempMarket = marketService.getMarketById(marketId);
					CartMarketDTO marketDTO = new CartMarketDTO();
					marketDTO.setMarketId(tempMarket.getMarketId());
					marketDTO.setMarketName(tempMarket.getMarketName());
					marketDTO.setCheckMarket(marketCheck);
					
					temp.setMarket(marketDTO);
					temp.setData(listProductDTO);
//					for(CartProductDTO tes : listProductDTO) {
//						System.out.println("list product : " + tes.getProductId());
//					}
					cartDTOList.add(temp);
					temp = new CartDTO();
					
					listProductDTO.clear();
					marketId = cart2.getMarketId();
					marketCheck = cart2.getCheckMarket();
//					System.out.println(marketId);
				}
				
				Product tempProduct = new Product();
				tempProduct = productService.getProductById(cart2.getCartId().getProductId());
//				System.out.println("product id nya : " + tempProduct.getProductId());
				CartProductDTO productDTO = new CartProductDTO();
				productDTO.setProductId(tempProduct.getProductId());
				productDTO.setProductName(tempProduct.getProductName());
				productDTO.setUrlProductImage(tempProduct.getUrlProductImage());
				productDTO.setPrice(tempProduct.getPrice());
				productDTO.setQuantity(cart2.getQuantity());
				productDTO.setCheckItem(cart2.getCheckItem());
				listProductDTO.add(productDTO);
				
				if(lengthList == flag) {
					Market tempMarket = marketService.getMarketById(marketId);
					CartMarketDTO marketDTO = new CartMarketDTO();
					marketDTO.setMarketId(tempMarket.getMarketId());
					marketDTO.setMarketName(tempMarket.getMarketName());
					marketDTO.setCheckMarket(cart2.getCheckMarket());
					
					temp.setMarket(marketDTO);
					temp.setData(listProductDTO);
					cartDTOList.add(temp);
				}
				
				flag++;
			}
			
		}
//		for(CartDTO tes : cartDTOList) {
//			System.out.println(tes.getProduct().size());
//		}
		
		return cartDTOList;
	}
	
	
	public Integer getTotal(Long buyerId) throws ResourceNotFoundException {
		Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("buyer id not found  in database : " + buyerId));
		List<Cart> cart = cartRepository.findCheckedItemByBuyerId(buyerId);
		int total=0;
		
		if(cart!=null) {
			
			for (Cart cart2 : cart) {
				if(cart2.getCheckItem().equals("1")) {
					Product prd = productRepository.findById(cart2.getCartId().getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product id not found  in database"));
					total += prd.getPrice();
				}
			}
		}
		return total;
	}
//	public List<CartDTO> viewCartByMarket(Long buyerId) {
//		List<CartDTO> temp = new ArrayList<CartDTO>();
//		
//		
//		return temp;
//	}
//	
	public String checkItem(Long buyerId, Long productId) throws ResourceNotFoundException {
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		if(cart != null) {
			long marketId = cart.getMarketId();
			int flagCheckAllMarket = 1;
			
			if(cart.getCheckItem().equals("1")) {
				cart.setCheckItem("0");
				cartRepository.save(cart);
				
				List<Cart> listCart = cartRepository.findByBuyerIdAndMarketId(buyerId, marketId);
				for(Cart cart2 : listCart) {
					cart2.setCheckMarket("0");
					cartRepository.save(cart2);
				}
			}else{
				cart.setCheckItem("1");
				cartRepository.save(cart);
				
				List<Cart> listCart = cartRepository.findByBuyerIdAndMarketId(buyerId, marketId);
				for(Cart cart2 : listCart) {
					if(cart2.getCheckItem().equals("1")) {
						continue;
					}
					flagCheckAllMarket = 0;
				}
				
				if(flagCheckAllMarket == 1) {
					for(Cart cart2 : listCart) {
						cart2.setCheckMarket("1");
						cartRepository.save(cart2);
					}
				}
				List<Cart> allCart = cartRepository.findByBuyerId(buyerId);
				for (Cart cart3 : allCart) {
					if(cart3.getMarketId() != marketId) {
						cart3.setCheckItem("0");
						cart3.setCheckMarket("0");
						cartRepository.save(cart3);
					}
				}
				//proses set selain market yg di cek jadi checked nya 0 semua
				
			}
		}
		else {
			throw new ResourceNotFoundException("No Product found for this ProductID : " + productId); 
		}
		return "success";
	}
	
	public String checkMarket(Long buyerId, Long marketId) {
		List<Cart> listCart = cartRepository.findByBuyerIdAndMarketId(buyerId, marketId);
		int flagCheck = 0;
		for(Cart cart2 : listCart) {
			if(cart2.getCheckMarket().equals("1")) {
				flagCheck = 1;
			}
			break;
		}
		
		if(flagCheck == 1) {
			for(Cart cart2 : listCart) {
				cart2.setCheckItem("0");
				cart2.setCheckMarket("0");
				cartRepository.save(cart2);
			}
		}else {
			List<Cart> allCart = cartRepository.findByBuyerId(buyerId);
			for (Cart cart : allCart) {
				cart.setCheckItem("0");
				cart.setCheckMarket("0");
				cartRepository.save(cart);
			}
			for(Cart cart2 : listCart) {
				cart2.setCheckItem("1");
				cart2.setCheckMarket("1");
				cartRepository.save(cart2);
			}
		}
		
		return "success";
	}
	
	public PaymentDTO getDetailPayment(Long buyerId) throws ResourceNotFoundException {
		PaymentDTO temp = new PaymentDTO();
		Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("buyer id not found  in database : " + buyerId));
		List<Cart> cart = cartRepository.findCheckedItemByBuyerId(buyerId);
		
		List<Product> listProduct = new ArrayList<Product>();
		if(cart!=null) {
			System.out.println(cart);
			for (Cart cart2 : cart) {
				if(cart2.getCheckItem().equals("1")) {
					Product prd = productRepository.findById(cart2.getCartId().getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product id not found  in database"));
					listProduct.add(prd);
				}
			}
		}
		temp.setBuyerDetail(buyer);
		temp.setListItem(listProduct);
		
		return temp;
	}
	
	public Cart findCart(Long buyerId, Long productId) {
		try {
			Cart cart1 = cartRepository.findByCartId(new CartCkey(buyerId, productId));
			return cart1;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Cart addToCart(Long buyerId, Long productId) throws ResourceNotFoundException {
		Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("buyer id not found  in database"));
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found  in database"));
		
		Cart cart = this.findCart(buyerId, productId);
		if ( cart == null) {
			Long marketId = productRepository.getMarketId(productId);
			if(marketId == null) {
				throw new ResourceNotFoundException("No Market found for this Product");
			}
			cart = new Cart(new CartCkey(buyerId, productId), 1, marketId, "0", "0");
		}else {
			cart.setQuantity(cart.getQuantity() + 1);
		}
		return cartRepository.save(cart);
	}
	
	public Cart addQtyCart(Long buyerId, Long productId) throws ResourceNotFoundException{
		//Optional<Cart> cart = cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow();
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		cart.setQuantity(cart.getQuantity()+1);
		return this.cartRepository.save(cart);
	}
	
	public Cart minQtyCart(Long buyerId, Long productId) throws ResourceNotFoundException{
		//Optional<Cart> cart = cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow();
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		cart.setQuantity(cart.getQuantity()-1);
		return this.cartRepository.save(cart);
	}
	
	public Cart updateCart1(Long buyerId, Long productId, Cart cart) throws ResourceNotFoundException {
		Cart temp =  cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow(() -> new ResourceNotFoundException("Cart not found for this buyerId :: " + buyerId + " and this productId" + productId));
		temp.setQuantity(cart.getQuantity());
		temp.setMarketId(cart.getMarketId());
		temp.setCheckItem(cart.getCheckItem());
		temp.setCheckMarket(cart.getCheckMarket());
		
		return this.cartRepository.save(temp);
	}
	
	public Cart updateQty(Long buyerId, Long productId, int qty) throws ResourceNotFoundException{
		//Optional<Cart> cart = cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow();
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		if(cart!=null) {
			cart.setQuantity(qty);
		}else {
			throw new ResourceNotFoundException("No Product found for this ProductID : " + productId); 
		}
		
		return this.cartRepository.save(cart);
	}
	
	public Map<String, Boolean> deleteItemFromCart(Long buyerId, Long productId) throws ResourceNotFoundException{
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		
		this.cartRepository.delete(cart);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
	
	
	
	public void deleteItemFromCartForOrder(Long buyerId, Long productId) throws ResourceNotFoundException{
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		
		this.cartRepository.delete(cart);
	}
}
