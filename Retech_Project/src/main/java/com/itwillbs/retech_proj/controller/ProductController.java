package com.itwillbs.retech_proj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.retech_proj.service.ChatService;
import com.itwillbs.retech_proj.mapper.RetechMapper;
import com.itwillbs.retech_proj.service.ProductService;
import com.itwillbs.retech_proj.service.RetechService;
import com.itwillbs.retech_proj.vo.LikeVO;
import com.itwillbs.retech_proj.vo.ProductVO;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private RetechService retechservice;


	// 리테크상품목록페이지
	// 최신순(날짜순)으로 기본적으로 정렬됨
	@GetMapping("ProductList")
	public String productList(@RequestParam(defaultValue = "1") int pageNum, 
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			ProductVO product, 
			Model model,
			HttpSession session) {
		
		System.out.println("=======================================================================");
		
		System.out.println("pageNum ========================" + pageNum);
		int listLimit = 12; // 한 페이지에서 표시할 목록 갯수 지정
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행(레코드) 번호
		System.out.println("startRow ============================= : " + startRow);
		System.out.println("searchKeyword!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : " + searchKeyword);
		// ProductService - getProductList() 호출하여 게시물 목록 조회 요청
		List<ProductVO> productList = service.getProductList(searchKeyword, startRow, listLimit);
		// 페이징 처리를 위한 계산작업
		int listCount = service.getProductListCount(searchKeyword); // 전체 게시물 수 조회
		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);

		model.addAttribute("productList", productList);
		model.addAttribute("maxPage", maxPage);
		System.out.println("maxPage ============================= : " + maxPage);
		model.addAttribute("listCount", listCount); // 전체 게시물 수

		return "product/product_list";
	}

	// 리테크상품목록페이지
	// 최신순(날짜순)으로 기본적으로 정렬됨
//	@GetMapping("ProductList")
//	public String productList(@RequestParam(defaultValue = "1") int pageNum, ProductVO product, Model model,
//			HttpSession session, @RequestParam (defaultValue ="") String searchKeyword) {
//		
//		System.out.println("ProductList 메서드 호출됨 파라미터 검색 keyword는 무엇일까요 : " + searchKeyword);
//		System.out.println("=======================================================================");
//		System.out.println("=======================================================================");
//		
//		
//		
//		
//		
//		System.out.println("pageNum ========================" + pageNum);
//		int listLimit = 12; // 한 페이지에서 표시할 목록 갯수 지정
//		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행(레코드) 번호
//		System.out.println("startRow ============================= : " + startRow);
//		// ProductService - getProductList() 호출하여 게시물 목록 조회 요청
//		List<ProductVO> productList = service.getProductList(startRow, listLimit);
//		
//		// 페이징 처리를 위한 계산작업
//		int listCount = service.getProductListCount(); // 전체 게시물 수 조회
//		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);
//		
//		model.addAttribute("productList", productList);
//		model.addAttribute("maxPage", maxPage);
//		System.out.println("maxPage ============================= : " + maxPage);
//		model.addAttribute("listCount", listCount); // 전체 게시물 수
//		
//		return "product/product_list";
//	}
//
	// 상품 목록 출력 ajax
	@ResponseBody
	@GetMapping("productListJson")
	public String changedProductList(@RequestParam String pd_category, ProductVO product, @RequestParam String pd_selectedManufacturer,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword, @RequestParam String pd_selectedPdStatus, @RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "최신순") String sort) {
//				System.out.println("pageNum : " + pageNum);
//				System.out.println("sort :  솔트솔트솔트솔트 으아아     :  " + sort);
//				System.out.println("pd_selectedPdStatus : " + pd_selectedPdStatus);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 검색한 keyword : " + searchKeyword);
		int listLimit = 12; // 한 페이지에서 표시할 목록 갯수 지정
		int endRow = pageNum * listLimit; // 조회 시작 행(레코드 번호)
		int startRow = (pageNum - 1) * listLimit;
		// 전달할 목록 값 받아오기 (거래중일 경우)
		String type = "거래중";
		System.out.println("pageNum :                 " + pageNum);
		
			List<String> relationKeyWord = retechservice.getRelationKeyWord(searchKeyword);
			
			List<HashMap<String, String>> changedProductList = service.getChangedProductList(searchKeyword, pageNum, pd_category, pd_selectedManufacturer,pd_selectedPdStatus, sort, endRow, startRow, listLimit);
			// 전체 게시물 갯수 계산
			int listCount = service.getChangedProductListCount(pageNum, pd_category, pd_selectedManufacturer, pd_selectedPdStatus, sort, type);
			System.out.println("!!!!!!!!!listCount 멀까요 : " + listCount);
			

		// 전체페이지 목록 개수 계산
		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);
		// => 이것도 리턴값으로 들고가고 싶다 => 객체로 넣기(boardList = XX, maxPage = xx) => JSONObject

		System.out.println("!!!!!!!!!maxpage 멀까요 : " + maxPage);
		// 최대 페이지번호(maxPage) 값도 JSON 데이터로 함께 넘기기
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("changedProductList", changedProductList);
		jsonObject.put("maxPage", maxPage);
		jsonObject.put("listCount", listCount);
//				System.out.println(jsonObject);

		// 선택한 카테고리와 거래상태에 해당하는 상품리스트 가져오기
//				List<ProductVO> selectedProductList = service.getSelectedProductList(pd_category, pd_status);
//				System.out.println("selectedProductList : " + selectedProductList);

//				jsonObject.put("selectedProductList", selectedProductList);

		return jsonObject.toString();
	}

	// 판매하기 클릭시 상품 등록 페이지로 이동
	@GetMapping("ProductRegistForm")
	public String productRegistForm(HttpSession session, Model model) {
		// 미로그인시 "로그인이 필요합니다." 문구 출력 후 이전 페이지로 돌아감
		// 임시로 주석 처리
		String member_id = (String) session.getAttribute("sId");
		System.out.println("member_id : " + member_id);
		if (member_id == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
			return "result/fail";
		}
		// 중고 카테고리 값 전달
		List<HashMap<String, String>> categorylist = service.getCategorylist();
		model.addAttribute("categorylist", categorylist);
		System.out.println("카테고리 리스트!!!!!!!!!!!!!!!!! : " + categorylist);

		return "product/product_regist_form";
	}

	// 상품 등록 처리
	@ResponseBody
	@PostMapping("ProductRegistPro")
	public String ProductRegistPro(ProductVO product, HttpSession session, Model model, HttpServletResponse request) {
	    System.out.println("넘어온 product : " + product);

	    // JsonConverter 사용하기 위한 Map 생성
	    Map<String, String> map = new HashMap<>();
	    // 기본 리턴값 false
	    String rResult = "false";
	    // 리테크 상품 설명란 줄바꿈 하기
	    // product.setPd_content(product.getPd_content().replaceAll("\r\n", "<br>"));

	    // 판매자 아이디 저장
	    String member_id = (String) session.getAttribute("sId");
	    // 세션에 값들이 잘 넘어오는지 확인
	    for (String attrName : Collections.list(session.getAttributeNames())) {
	        System.out.println("Session Attribute - Name: " + attrName + ", Value: " + session.getAttribute(attrName));
	    }
	    // 아이디가 null 값일 경우 페이징 처리
	    if (member_id == null) {
	        model.addAttribute("msg", "잘못된 접근입니다!");
	        return "result/fail";
	    }
	    session.setAttribute("sId", member_id);
	    System.out.println("판매자 아이디 : " + member_id);

	    // 이미지 파일 업로드 처리
	    String uploadDir = "/resources/upload";
	    String saveDir = session.getServletContext().getRealPath(uploadDir);
	    System.out.println("실제 업로드 경로 : " + saveDir);

	    // 서브디렉토리(날짜 구분)
	    String subDir = "";
	    try {
	        Date date = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        subDir = sdf.format(date);
	        saveDir += "/" + subDir;
	        Path path = Paths.get(saveDir);
	        Files.createDirectories(path);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // VO 객체에 전달된 MultipartFile 객체 꺼내기
	    MultipartFile[] files = { product.getFile1(), product.getFile2(), product.getFile3(), product.getFile4(), product.getFile5() };

	    // 파일명 중복 방지
	    String uuid = UUID.randomUUID().toString();
	    System.out.println("uuid : " + uuid);

	    // 생성된 UUID 값을 원본 파일명 앞에 결합(파일명과 구분을 위해 _ 기호 추가)
	    String[] fileNames = new String[5];
	    String[] filePaths = new String[5];
	    int fileIndex = 0;

	    // 업로드 된 파일 존재 시 fileNames 및 filePaths 변수에 저장
	    for (MultipartFile file : files) {
	        if (file != null && !file.isEmpty()) {
	            String fileName = uuid.substring(0, 8) + "_" + file.getOriginalFilename();
	            fileNames[fileIndex] = fileName;
	            filePaths[fileIndex] = subDir + "/" + fileName;
	            fileIndex++;
	        }
	    }

	    // 파일명 및 파일 경로 설정
	    if (fileNames[0] != null) product.setPd_image1(filePaths[0]);
	    if (fileNames[1] != null) product.setPd_image2(filePaths[1]);
	    if (fileNames[2] != null) product.setPd_image3(filePaths[2]);
	    if (fileNames[3] != null) product.setPd_image4(filePaths[3]);
	    if (fileNames[4] != null) product.setPd_image5(filePaths[4]);

	    System.out.println("실제 업로드 파일명1 : " + product.getPd_image1());
	    System.out.println("실제 업로드 파일명2 : " + product.getPd_image2());
	    System.out.println("실제 업로드 파일명3 : " + product.getPd_image3());
	    System.out.println("실제 업로드 파일명4 : " + product.getPd_image4());
	    System.out.println("실제 업로드 파일명5 : " + product.getPd_image5());

	    // 판매할 상품 등록 작업
	    System.out.println(product);
	    System.out.println("pd_price : " + product.getPd_price());

	    int insertCount = service.registBoard(product);
	    
	    //등록한 상품 번호 들고오기
	    int productIdx = chatService.getPdIDX(member_id);
	    System.out.println("!!!!!!!!!!!!!!!!!!!! 등록한 상품 번호 들고오기 : " + productIdx);
	    
	    //거래 테이블에 입력하기
	    chatService.insertTrade(productIdx, member_id);
	    
	    // 등록 결과를 판별
	    // 성공 : 업로드 파일 - 실제 디렉토리에 이동시킨 후, productList 서블릿 리다이렉트
	    // 실패 : "상품 등록 실패" 출력 후 이전 페이지 돌아가기 처리
	    if (insertCount > 0) {// 성공
	        // 업로드 파일 실제 디렉토리 이동 작업
	    	//--------------------거래 테이블에 추가 ---------------------------------
	    	//상품 등록 시 거래 테이블에도 상품 번호랑 거래상태를 insert함
	        try {
	            for (int i = 0; i < fileIndex; i++) {
	                MultipartFile file = files[i];
	                if (file != null && !file.isEmpty()) {
	                    file.transferTo(new File(saveDir, fileNames[i]));
	                }
	            }
	            rResult = "true";
	            model.addAttribute("res", rResult);
	        } catch (IllegalStateException | IOException e) {
	            e.printStackTrace();
	        }
	        return rResult;
	    } else {// 실패
	        model.addAttribute("msg", "상품 등록 실패!");
	        return "result/fail";
	    }
	}

	//리테크 상품 상세정보페이지
	//조회성공시 조회수 증가
	@GetMapping("product_detail")
	public String product_detail(@RequestParam int pd_idx,
								@RequestParam String member_id,
								Model model, 
								HttpSession session) {
		//파라미터로 전달받은 상품번호 및 판매자 아이디 확인
		System.out.println(" 상품번호오오오오오오 : " + pd_idx);
		System.out.println(" 판매자아이디이이이이이이 : " + member_id);
	
		//상품번호에 해당하는 상품의 정보조회작업
		ProductVO product = service.getProduct(pd_idx);
		//조회결과 저장
		model.addAttribute("product", product);
		

		//상세페이지의 판매자정보조회 
		//- 파라미터로 전달받은 pd_idx의 member_id와 동일한 member정보 얻어옴
		//  멤버테이블 필요정보 : member_profile, member_nickname, member_address1, member_address_2
		//- 리턴타입 : ,파라미터:상품번호, 멤버아이디
		
		//주의!!!!!-> 파라미터 두개이상일경우 매퍼-(@Param)어노테이션필요!

		
		HashMap<String,String> sellerInfo = service.getSellerInfo(pd_idx, member_id);
		System.out.println("&&&&&&&&&&&&&&&& 판매자정보 : " + sellerInfo);
		model.addAttribute("seller",sellerInfo);
		
		//판매자의 판매상품 개수 조회
		int sellerProduct = service.getSellerProductCount(member_id);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+ sellerProduct);
		model.addAttribute("sellerProduct", sellerProduct);
		System.out.println("판매자의 판매상품 갯수 조회ㅇ에에에에에에에 : " + sellerProduct);
		//판매자의 판매목록조회
		List<HashMap<String, String>> sellerProductList = service.getSellerProductList(member_id);
		model.addAttribute("sellerProductList",sellerProductList);
		System.out.println("내가 바로 판매자이다아아아아아!!!!!! : " + sellerProductList);
		
		return"product/product_detail";
	}
	//상품 수정 페이지
	@GetMapping("productModifyForm")
	public String productModifyForm(
				ProductVO product, 
				HttpSession session, 
				@RequestParam(defaultValue = "1") int pageNum,
				Model model,
				@RequestParam int pd_idx) {
		
		String member_id = (String) session.getAttribute("sId");
		// 세션 아이디가 존재하지 않으면(미로그인) "잘못된 접근입니다!" 출력 후 이전 페이지 돌아가기 처리
	
		System.out.println("상품 번호오오오오오 : " + pd_idx);
		System.out.println("판매자 이름으으으으음 : " + member_id);
		if(member_id == null) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		}
//		System.out.println("%&%&%&%&%&%& 수정 - 판매자아이디 : " + member_id);
//		System.out.println("%&%&%&%&%&%& 수정 - 상품번호 : " + secondhand_idx);
		
		
		//중고 카테고리 값 전달(재사용) 
		List<HashMap<String, String>> categorylist = service.getCategorylist();
		model.addAttribute("categorylist", categorylist);
		
		//파라미터로 넘어온 상품번호의 상품정보 가져오기
		//디테일조회작업시 사용한 getSecondhandProduct() 재사용
		product = service.getProduct(pd_idx);
		System.out.println("%&%&%&%&%&%& 수정 - 상품정보 : " + product);
		
		
		// 상품설명 줄바꿈처리
		product.setPd_content(product.getPd_content().replaceAll("<br>", "\r\n"));
		model.addAttribute("product",product);
		String image1Url = product.getPd_image1();
		String image2Url = product.getPd_image2();
		String image3Url = product.getPd_image3();
		String image4Url = product.getPd_image4();
		String image5Url = product.getPd_image5();
		String jsonData = "{\"image1Url\": \"" + image1Url + "\", " +
				"\"image2Url\": \"" + image2Url + "\", " +
				"\"image3Url\": \"" + image3Url + "\", " +
                "\"image4Url\": \"" + image4Url + "\", " +
                "\"image5Url\": \"" + image5Url + "\"}";
		model.addAttribute("jsonData",jsonData);
		return "product/product_modify_form";
	}
	//상품수정 처리(UPDATE)
	@RequestMapping(value = "productModifyPro", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, String> productModifyPro(
	        ProductVO product,
	        HttpSession session, 
	        Model model,
//	        @RequestParam int pd_idx,
			@RequestParam(defaultValue = "1") int pageNum) {
	    System.out.println("넘어온 product 객체 : " + product);
		Map<String, String> response = new HashMap<>();
	    response.put("result", "false"); // 기본값 false
	    
	    // 상품 설명 줄바꿈 처리
	    product.setPd_content(product.getPd_content().replaceAll("<br>", "\r\n"));
	    
	    // 파일 저장 작업
	    String uploadDir = "/resources/upload"; 
	    String saveDir = session.getServletContext().getRealPath(uploadDir);
	    String subDir = "";
	    
	    try {
	        Date date = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        subDir = sdf.format(date);
	        saveDir += "/" + subDir;
	        Path path = Paths.get(saveDir);
	        Files.createDirectories(path);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // VO 객체에 전달된 MultipartFile 객체 꺼내기
	    MultipartFile pFile1 = product.getFile1();
	    MultipartFile pFile2 = product.getFile2();
	    MultipartFile pFile3 = product.getFile3();
	    MultipartFile pFile4 = product.getFile4();
	    MultipartFile pFile5 = product.getFile5();
	    
	    String uuid = UUID.randomUUID().toString();
	    
	    // 파일명 설정
	    String fileName1 = null;
	    String fileName2 = null;
	    String fileName3 = null;
	    String fileName4 = null;
	    String fileName5 = null;
	    
	    if (pFile1 != null && !pFile1.isEmpty()) {
	        fileName1 = uuid.substring(0, 8) + "_" + pFile1.getOriginalFilename();
	        product.setPd_image1(subDir + "/" + fileName1);
	    }
	    if (pFile2 != null && !pFile2.isEmpty()) {
	        fileName2 = uuid.substring(0, 8) + "_" + pFile2.getOriginalFilename();
	        product.setPd_image2(subDir + "/" + fileName2);
	    }
	    if (pFile3 != null && !pFile3.isEmpty()) {
	        fileName3 = uuid.substring(0, 8) + "_" + pFile3.getOriginalFilename();
	        product.setPd_image3(subDir + "/" + fileName3);
	    }
	    if (pFile4 != null && !pFile4.isEmpty()) {
	        fileName4 = uuid.substring(0, 8) + "_" + pFile4.getOriginalFilename();
	        product.setPd_image4(subDir + "/" + fileName4);
	    }
	    if (pFile5 != null && !pFile5.isEmpty()) {
	        fileName5 = uuid.substring(0, 8) + "_" + pFile5.getOriginalFilename();
	        product.setPd_image5(subDir + "/" + fileName5);
	    }
		//=====================================================================================
		// 글 수정 작업 요청
	    
	    int updateCount = service.modifyProduct(product);
	    //상품 수정 작업
		//성공시 업로드 파일을 실제 디렉토리로 이동 => BoardList 서블릿으로 리다이렉트
		//실패시 "글쓰기 실패" 메시지 출력 후 이전페이지 돌아가기 처리
	    if (updateCount > 0) { // 성공
	        try {
	            if (fileName1 != null) {
	                pFile1.transferTo(new File(saveDir, fileName1));
	            }
	            if (fileName2 != null) {
	                pFile2.transferTo(new File(saveDir, fileName2));
	            }
	            if (fileName3 != null) {
	                pFile3.transferTo(new File(saveDir, fileName3));
	            }
	            if (fileName4 != null) {
	                pFile4.transferTo(new File(saveDir, fileName4));
	            }
	            if (fileName5 != null) {
	                pFile5.transferTo(new File(saveDir, fileName5));
	            }
	            response.put("result", "true");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else { // 실패
	        response.put("result", "false");
	    }
	    
	    return response;
	}


		//끌어올리기(UPDATE - DATE)
		//DB의 registdate날짜 업데이트하기
		//끌어올리기버튼-
		@GetMapping("productUpdateDate")
		public String productUpdateDate(
							@RequestParam int pd_idx,
							Model model,
							@RequestParam String member_id
							) {
			//날짜(등록일)업데이트후 secondhand_list페이지로 리다이렉트(서블릿-서블릿)
			System.out.println("*&*&&********업데이트 상품번호" + pd_idx);
			System.out.println("*&*&&********업데이트 멤버아이디" + member_id);
			
			
			
			int updateCount = service.updateRegistdate(pd_idx);
			
			if(updateCount > 0) {
				model.addAttribute("msg", "끌어올리기가 정상적으로 처리되었습니다");
				model.addAttribute("targetURL", "ProductList");
				return "result/success";	
			} else {
				model.addAttribute("msg", "끌어올리기 실패");
				return "result/fail";
			}
			
		}
		
		//상품삭제 처리(DELETE)
		@GetMapping("productDelete")
		public String productDelete(
				@RequestParam int pd_idx, 
				@RequestParam(defaultValue = "1") int pageNum,
				ProductVO product,
				HttpSession session, 
				Model model) {
			//미로그인시 "로그인필수"출력 후 이전페이지 돌아감
			String member_id = (String) session.getAttribute("sId");
			System.out.println("member_id : +++++++++++++++++++++++++++++++++++"+ member_id);
			System.out.println("pd_idx : +++++++++++++++++++++++++++++++++++"+ pd_idx);

			if(member_id == null) {
				model.addAttribute("msg", "로그인이 필요한 작업입니다!");
				return "fail_back";
			}
			
			// 작성자 확인 작업 ---------------------------------------------
			 //SecondhandService - isSeller()작성자 판별요청
			// 파라미터:상품번호(secondhand_idx), 세션아이디 리턴타입:boolean(isProductRegister)
			// 단, 세션아이디가 "admin@gmail.com" 아닐 경우에만 수행
			if(!member_id.equals("admin@gmail.com")) {
				boolean isBoardWriter = service.isBoardWriter(pd_idx, member_id);
				
				if(!isBoardWriter) {
					model.addAttribute("msg", "권한이 없습니다!");
					return "result/fail";
				}
			}
			
			// 글삭제작업 ----------------------------------------------------
			int deleteCount = service.removeProduct(pd_idx);
			
			// 삭제 실패 시 "삭제 실패!" 처리 후 이전페이지 이동
			// 아니면(삭제성공시), product_list 서블릿 요청(파라미터 : 페이지번호)
			if(deleteCount == 0) {
				model.addAttribute("msg", "삭제 실패!");
				return "result/fail";	
			}
			
			return "redirect:/ProductList?pageNum=" + pageNum;
			
		}
//		// 페이지 - 찜보여주기
		@RequestMapping(value = "likeProductShow", method = {RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public List<HashMap<String, String>> likeProductShow(HttpSession session) {
			//찜한 상품이 있을 경우 찜하기 표시하기(비회원이 아닐 때)
			String member_id = (String) session.getAttribute("sId");
			

			if (member_id != null) {
//				System.out.println("어디서 문제니 : member_id " + member_id);
//				System.out.println("어디서 문제니 : member_type " + session.getAttribute("member_type"));
				List<HashMap<String, String>> likeList = service.getLikeProduct(member_id); 
				System.out.println(likeList);
//				}
				return likeList;
			}
			return null; //진성민2
		}
		
//		찜기능
		@RequestMapping(value = "likeProduct", method = {RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public Integer likeProduct(
		        @RequestParam(defaultValue = "0") int like_idx,
		        @RequestParam(defaultValue = "") String member_id,
		        @RequestParam(defaultValue = "0") int pd_idx,
		        boolean isLike, LikeVO likes) {

		    // 넘어온 파라미터 확인
		    System.out.println("member_id : " + member_id + ", pd_idx : " + pd_idx + ", isLike : " + isLike + ", like_idx : " + like_idx);
		    
		    // likes 객체에 찜 관련 데이터 설정
		    likes.setLike_idx(like_idx);
		    likes.setMember_id(member_id);
		    likes.setPd_idx(pd_idx);

		    if (!isLike) {
		        // 찜 기능 실행 (Insert)
		        int insertCount = service.checkLikeProduct(likes);
		        System.out.println("찜 된 상품 갯수 : " + insertCount);
		        return insertCount;
		    } else {
		        // 찜 취소 실행 (Delete)
		        int deleteCount = service.unCheckLikeProduct(likes);
		        System.out.println("취소된 상품 갯수 : " + deleteCount);
		        return deleteCount;
		    }
		}

		@ResponseBody
		@GetMapping("checkLikeStatus")
		public int checkLikeStuats(@RequestParam("member_id") String member_id,
	            @RequestParam("pd_idx") int pd_idx) {
			System.out.println("찜 불러오기!!!!!!!!");
			System.out.println("넘어온 데이터 확인  : " + member_id + ", " + pd_idx);
			int isLiked = service.likeChecked(member_id, pd_idx);
			System.out.println("아이디랑 상품번호 넣었을 때 조회된 좋아요 번호 : " + isLiked);
			
			// 최대 페이지번호(maxPage) 값도 JSON 데이터로 함께 넘기기
//			JSONObject jsonObject = new JSONObject();

//			jsonObject.put("isLiked", isLiked);
			return isLiked;
		}
}
		
				
	
	
	
	


