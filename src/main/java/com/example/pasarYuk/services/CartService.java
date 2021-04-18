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
	public List<CartDTO> listCart(Long buyerId) throws ResourceNotFoundException{
		//query cart dengan ID BUYER nya = buyerId dari session
		List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
		List<Cart> cart = cartRepository.findByBuyerId(buyerId);
		
		if(cart != null) {
			for(Cart cart2 : cart) {
				CartDTO temp = new CartDTO();
				Product tempProduct = productService.getProductById(cart2.getCartId().getProductId());
				temp.setProduct(tempProduct);
				temp.setQty(cart2.getQuantity());
				Market tempMarket = marketService.getMarketById(cart2.getMarketId());
				temp.setMarket(tempMarket);
				
				cartDTOList.add(temp);
			}
		}
		
		return cartDTOList;
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
			cart = new Cart(new CartCkey(buyerId, productId), 1, marketId);
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
	
	
	public Cart updateCart(Long buyerId, Long productId, int qty) throws ResourceNotFoundException{
		//Optional<Cart> cart = cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow();
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		cart.setQuantity(qty);
		return this.cartRepository.save(cart);
	}
	
	public Map<String, Boolean> deleteItemFromCart(Long buyerId, Long productId) throws ResourceNotFoundException{
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		
		this.cartRepository.delete(cart);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
	
	public Cart updateCart1(Long buyerId, Long productId, Cart cart) throws ResourceNotFoundException {
		Cart temp =  cartRepository.findById(new CartCkey(buyerId, productId)).orElseThrow(() -> new ResourceNotFoundException("Cart not found for this buyerId :: " + buyerId + " and this productId" + productId));
		temp.setQuantity(cart.getQuantity());
		temp.setMarketId(cart.getMarketId());
		
		return this.cartRepository.save(temp);
	}
	
	public void deleteItemFromCartForOrder(Long buyerId, Long productId) throws ResourceNotFoundException{
		Cart cart = cartRepository.findByCartId(new CartCkey(buyerId, productId));
		
		this.cartRepository.delete(cart);
	}
}
