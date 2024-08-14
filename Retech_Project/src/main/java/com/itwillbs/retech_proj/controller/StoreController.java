package com.itwillbs.retech_proj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.retech_proj.service.StoreService;
import com.itwillbs.retech_proj.vo.StoreVO;

@Controller
public class StoreController {
	@Autowired
	StoreService service;
	
	@GetMapping("Store")
	public String store() {
		return "store/store_list";
	}
	
	//상품 목록 출력 ajax
	@GetMapping("StoreProductList")
	public String productList() {
		
		List<Map<String, Object>> productList = service.selectProductList();
		
		return "";
	}
	
	@GetMapping("StoreDetail")
	public String storeDetail(StoreVO store, Model model) {
		//상품 정보 조회
		Map<String, Object> Product = service.selectProduct(store);
//		System.out.println("Product : " + Product);
		
		model.addAttribute("Product", Product);
		return "store/store_detail";
	}
	
	
	@GetMapping("StorePay")
	public String storePay(@RequestParam String order_store_item, @RequestParam int order_store_quantity, 
							@RequestParam int order_store_pay, Model model) {
		//상품 정보 조회
		/*int insertCount = service.insertPayProduct(order_store_item, order_store_quantity, order_store_pay);*/
		Map<String, Object> payProduct = service.selectPayProduct(order_store_item);
		model.addAttribute("payProduct", payProduct);
		return "store/store_pay";
	}
	
	
}
