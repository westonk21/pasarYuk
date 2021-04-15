package com.example.pasarYuk.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.repository.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> listProduct(){
		return productRepository.findAll();
	}
	
	public Product getProductById(Long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
		return product;
	}
	
	public List<Product> getProductBySellerId(Long sellerId){
		List<Product> product = productRepository.findBySellerId(sellerId);
		return product;
	}
	
//	public Product addToCart(Long productId) {
//		return productRepository.save(productId);
//	}
	
	public Product addNewProduct(Product product) {
		//product.setSellerId(sellerId);
		return productRepository.save(product);
	}
	
	public Product updateProduct(Long productId, Long sellerId, Product productDetails) throws ResourceNotFoundException {
//		Product product = productRepository.findById(productId)
//				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
//		System.out.println(product.getSellerId());
//		if(product.getSellerId() != sellerId) {
//			throw new ResourceNotFoundException("Product not found for this sellerId :: " + sellerId);
//		}
		
		Product product = productRepository.findByProductIdAndSellerId(productId, sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
		
		//product.setSellerId(productDetails.getSellerId());
		//product.setReviewId(productDetails.getReviewId());
		product.setProductName(productDetails.getProductName());
		product.setProductDesc(productDetails.getProductDesc());
		product.setPrice(productDetails.getPrice());
		product.setAvgStar(productDetails.getAvgStar());
		product.setCategory(productDetails.getCategory());		
		product.setSatuanJual(productDetails.getSatuanJual());
		
		return this.productRepository.save(product); 
	}
	
	public Map<String, Boolean> deleteProduct(Long productId) throws ResourceNotFoundException{
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + productId));
		
		this.productRepository.delete(product);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
	
	public List<Product> getProductWithFilter(Long marketId, String category, String filter) throws ResourceNotFoundException{
		//System.out.println(marketId + "--" + category + "--" + filter);
		//Pageable pageable = PageRequest.of(page - 1, 6);
		List<Product> tmpProduct = null;
		if(category.equals("default") && filter.equals("default")) {
			//inquery by market aja
			tmpProduct = productRepository.findByMarket(marketId);
		}else if(!category.equals("default") && filter.equals("default")) {
			//inquery by market dan category 
			tmpProduct = productRepository.findByCategory(marketId, category);
		}else if(category.equals("default") && !filter.equals("default")) {
			//inquery by market dan di sort by filter
			if(filter.equals("HargaTerendah")) {
				System.out.println("masuk");
				tmpProduct = productRepository.findByFilterA(marketId);
			}else if(filter.equals("HargaTertinggi")) {
				tmpProduct = productRepository.findByFilterB(marketId);
			}else if(filter.equals("Terlaris")) {
				tmpProduct = productRepository.findByFilterC(marketId);
			}
		}else if(!category.equals("default") && !filter.equals("default")) {
			//inquery by market dan category dan di sort by filter
			if(filter.equals("HargaTerendah")) {
				tmpProduct = productRepository.findByCategoryAndFilterA(marketId, category);
			}else if(filter.equals("HargaTertinggi")) {
				tmpProduct = productRepository.findByCategoryAndFilterB(marketId, category);
			}else if(filter.equals("Terlaris")) {
				tmpProduct = productRepository.findByCategoryAndFilterC(marketId, category);
			}
		}
		if(tmpProduct == null) {
			throw new ResourceNotFoundException("No data found");
		}
		return tmpProduct;
	}
	
//	public List<Product> listProductOld(Long marketId, String category, String filter){
//		List<Product> tmpProduct = productRepository.findByFilter(marketId, category);
//		
//		//filter : ulasan, harga tertinggi, harga terendah
//		if (filter == "ulasan") {
//			Collections.sort(tmpProduct, new Comparator<Product>() {
//				@Override
//				public int compare(Product p1, Product p2) {
//					return Float.compare(p1.getAvgStar(), p2.getAvgStar());
//				}
//			});
//		}else if(filter == "harga tertinggi") {
//			Collections.sort(tmpProduct, new PriceSortDsc());
//		}else if(filter == "harga terendah") {
//			Collections.sort(tmpProduct, new PriceSortAsc());
//		}
//		
//		return tmpProduct;
//	}
	
}


