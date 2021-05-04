package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.example.pasarYuk.model.Cart;
import com.example.pasarYuk.services.CartService;

import temp.CartDTO;
import temp.PaymentDTO;

@RestController
@RequestMapping("api/v1")
public class CartController {

	@Autowired
	private CartService cartService;
	//-----------------------------------------------------------------------------
//	@GetMapping("/cart/{buyerId}")
//	public List<CartDTO> viewCart(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
//		//many to one, or apa biar pas inquery data product nya sudah ada
//		//Long buyerId = (Long) session.getAttribute("buyerId");
//		return cartService.listCart(buyerId);
//	}
	
	//cart punya -----------------------------------------------------------------
	
	@GetMapping("/cart-view/{buyerId}")
	public List<CartDTO> viewCartByMarket(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
		List<CartDTO> temp = cartService.viewCartByMarket(buyerId);
		return temp;
	}
	@GetMapping("/cart-total/{buyerId}")
	public Integer getTotal(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException {
		return cartService.getTotal(buyerId);
	}
	
	
	@PutMapping("/cart-check-item/{buyerId}/{productId}")
	public String checkItem(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException{
		return cartService.checkItem(buyerId, productId);
	}
	
	@PutMapping("/cart-check-market/{buyerId}/{marketId}")
	public String checkMarket(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "marketId") Long marketId){
		return cartService.checkMarket(buyerId, marketId);
	}
	
	//------------------------------------------------------------------------------
	
	@GetMapping("/cart-payment/{buyerId}")
	public PaymentDTO getDetailPayment(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException {
		return cartService.getDetailPayment(buyerId);
	}
	
	//------------------------------------------------------------------------------
	
	//add to cart
	@PostMapping("/cart/add/{buyerId}/{productId}")
	public Cart addToCart(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException {
		//sementara dari url buyerId nya
		//Long buyerId = (Long) session.getAttribute("buyerId");
		return cartService.addToCart(buyerId, productId);
	}
	
	//add qty
	@PutMapping("/cart/addQty/{buyerId}/{productId}")
	public ResponseEntity<Cart> addQtyCart(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException{
		//Long buyerId = (Long) session.getAttribute("buyerId");
		Cart cart = cartService.addQtyCart(buyerId, productId);
		
		return ResponseEntity.ok(cart);
	}
	//min qty
	@PutMapping("/cart/minQty/{buyerId}/{productId}")
	public ResponseEntity<Cart> minQtyCart(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException{
		//Long buyerId = (Long) session.getAttribute("buyerId");
		Cart cart = cartService.minQtyCart(buyerId, productId);
		
		return ResponseEntity.ok(cart);
	}
	
	//update with body
	@PutMapping("/cart/update/{buyerId}/{productId}")
	public ResponseEntity<Cart> updateCart1(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId, @RequestBody Cart cart) throws ResourceNotFoundException{
		Cart temp = cartService.updateCart1(buyerId, productId, cart);
		return ResponseEntity.ok(temp);
	}
	
	//update quantity aja
	@PutMapping("/cart/updQTY/{buyerId}/{productId}/{qty}")
	public ResponseEntity<Cart> updateCart(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId, @PathVariable(value = "qty") int qty) throws ResourceNotFoundException{
		//Long buyerId = (Long) session.getAttribute("buyerId");
		
		Cart cart = cartService.updateQty(buyerId, productId, qty);
		
		return ResponseEntity.ok(cart);
	}
	
	//delete item from cart
	@DeleteMapping("/cart/del/{buyerId}/{productId}")
	public Map<String, Boolean> deleteBuyer(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException{
		//Long buyerId = (Long) session.getAttribute("buyerId");
		Map<String, Boolean> response = cartService.deleteItemFromCart(buyerId, productId);
		return response;
	}
}
