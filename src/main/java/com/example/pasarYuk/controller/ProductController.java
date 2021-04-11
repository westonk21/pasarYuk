package com.example.pasarYuk.controller;

//import java.util.HashMap;
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
import com.example.pasarYuk.model.Product;
//import com.example.pasarYuk.services.CartService;
//import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.services.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService productService;
//	@Autowired
//	private CartService cartService;
	
	//get product
	@GetMapping("products")
	public List<Product> getAllProduct(){
		return productService.listProduct();
	}
	
	//list product by sellerID
	@GetMapping("products/list-seller/{sellerId}")
	public List<Product> getProductBySellerId(@PathVariable(value = "sellerId") Long sellerId){
		return productService.getProductBySellerId(sellerId);
	}
	
	//get product with filter
	@GetMapping("product-list/market/{marketId}/ctgy/{category}/filter/{filter}")
	public List<Product> getProductWithFilter(@PathVariable(value = "marketId") Long marketId, @PathVariable(value = "category") String category, @PathVariable(value = "filter") String filter ) throws ResourceNotFoundException{
		return productService.getProductWithFilter(marketId, category, filter);
	}
	
	//get product by id
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "productId") Long productId) throws ResourceNotFoundException {
		Product product = productService.getProductById(productId);
		return ResponseEntity.ok().body(product);
	}
	
//	//add product to cart
//	@PostMapping("products/addToCart/{buyerId}/{productId}")
//	public Product addToCart(@PathVariable(value = "id") Long productId) {
//		Product productResp = productService.addToCart(productId);
//		return productResp;
//	}
	
	//new product
	@PostMapping("products/{sellerId}")
	public Product createProduct(@PathVariable(value = "sellerId") Long sellerId, @RequestBody Product product) {
		Product productResp = productService.addNewProduct(sellerId, product);
		return productResp;
	}
	
	//update product
	@PutMapping("products/{productId}/{sellerId}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "productId") Long productId, @PathVariable(value = "sellerId") Long sellerId, @Valid @RequestBody Product productDetails) throws ResourceNotFoundException{
		//sellerId harusny pake session login nya seller
		Product product = productService.updateProduct(productId, sellerId, productDetails);
		
		return ResponseEntity.ok(product);
	}
	
	//delete buyer
	@DeleteMapping("products/{productId}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "productId") Long productId) throws ResourceNotFoundException{
		Map<String, Boolean> response = productService.deleteProduct(productId);
		return response;
	}
	
}
