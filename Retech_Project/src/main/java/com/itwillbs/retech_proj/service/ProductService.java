package com.itwillbs.retech_proj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.retech_proj.mapper.ProductMapper;
import com.itwillbs.retech_proj.mapper.RetechMapper;
import com.itwillbs.retech_proj.vo.LikeVO;
import com.itwillbs.retech_proj.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	 private ProductMapper mapper;
	@Autowired
	 private RetechMapper retechmapper;
	
	 // 상품 목록 페이지
	public List<ProductVO> getProductList(String searchKeyword, int startRow, int listLimit) {
		// TODO Auto-generated method stub
		return mapper.selectProductList(searchKeyword, startRow, listLimit);
	}
	//상품 갯수 조회
	public int getProductListCount(String searchKeyword) {
		// TODO Auto-generated method stub
		return mapper.selectProductListCount(searchKeyword);
	}

	//리텍트 상품 등록 처리
	public int registBoard(ProductVO product) {
		return mapper.insertProduct(product);
	}	
	//리테크 상품수정작업
	//	public int modifyProduct(ProductVO product) {
	//		return mapper.updateProduct(product);
	//	}
	
	//선택한 카테고리와 거래상태에 해당하는 상품리스트 가져오기
	public List<ProductVO> getSelectedProductList(String pd_category, String pd_status) {
		return mapper.selectSelectedProductList(pd_category, pd_status);
	}
	//거래중 게시물 목록 조회
	public List<HashMap<String, String>> getChangedProductList(String searchKeyword, int pageNum, String pd_category, String pd_selectedManufacturer, String pd_selectedPdStatus,
			String sort, int endRow, int startRow, int listLimit) {
		return mapper.selectChangedProductList(searchKeyword, pageNum, pd_category, pd_selectedManufacturer,
				pd_selectedPdStatus, sort, endRow, startRow, listLimit);
	}
	//전체 게시물 갯수 계산
	public int getChangedProductListCount(int pageNum, String pd_category, String pd_selectedManufacturer ,String pd_selectedPdStatus, String sort, String type) {
		return mapper.selectChangedProductListCount(pageNum, pd_category, pd_selectedManufacturer,
				pd_selectedPdStatus, sort, type);
	}
	
	//상품 카테고리 목록
	public List<HashMap<String, String>> getCategorylist() {
		return mapper.selectCategoryList();
	}

	// 거래 상태 업데이트
    public int updateProductStatus(int pd_idx, String pd_status) {
        return mapper.updateProductStatus(pd_idx, pd_status);
    }
    
    // 상품 IDX로 상품 정보를 조회
    public ProductVO getProductById(int pd_Idx) {
        return mapper.selectProductById(pd_Idx);
    }
  //상품번호에 해당하는 상품의 정보조회작업
	public ProductVO getProduct(int pd_idx) {
		ProductVO product = mapper.selectProduct(pd_idx);
		
		//조회결과 있을경우(조회성공시 -> 조회수 증가작업요청 -> updateReadCount()
		if(product != null) {
			// secondhandVO에 secondhand_idx 포함되어있으므로
			// 파라미터로 secondahndVO객체 전달시 -> 값변경되면 별도리턴없어도 주소값변경O
			//=> 사용된VO객체의 변경된 값 함께 공유!
			mapper.updateReadCount(product);
		}
		
		return product;
	}
	
	//상세페이지(판매자영역) - 판매자 정보 조회---------------------------------------------------
	// 판매자페이지 - 판매자 정보 조회요청(selectSellerInfo()재사용-파라미터 member_id만 사용)
	public HashMap<String, String> getSellerInfo(int pd_idx, String member_id) {
		System.out.println("Serviece+++++++++++++++++++++++" + pd_idx + " ," + member_id);
		return mapper.selectSellerInfo(pd_idx, member_id);
	}
	
	//상세페이지(판매자영역) - 판매자 판매물품 개수조회
	public int getSellerProductCount(String member_id) {
		return mapper.selectSellerProductCount(member_id);
	}
	
	// 판매자의 판매목록 조회요청
	public List<HashMap<String, String>> getSellerProductList(String member_id) {

		return mapper.selectSellerProductList(member_id);
	}
	
	//상품수정 작업
	public int modifyProduct(ProductVO product) {
		
		return mapper.updateProduct(product);
	}
	//끌어올리기 작업
	public int updateRegistdate(int pd_idx) {
		return mapper.updateDate(pd_idx);
	}
	//상품 게시물 삭제 -1. 작성자(판매자) 확인 작업
	public boolean isBoardWriter(int pd_idx, String member_id) {
		//글번호에 해당하는 작성자확인 -> 상세정보조회의 selectProduct()재사용
		//-조회된결과의 작성자id(product.getMember_id()) 와, 전달받은 세션아이디 비교결과 리턴
		ProductVO product = mapper.selectProduct(pd_idx);
		return product.getMember_id().equals(member_id);
	}
	//상품 게시물 삭제 -2. 글 삭제 작업
	public int removeProduct(int pd_idx) {
		return mapper.deleteProduct(pd_idx);
	}
	//진성민
	
	//찜목록 조회 (select)
	public List<HashMap<String, String>> getLikeProduct(String member_id) {
		return mapper.selectLikeProduct(member_id);
	}
	
	//찜하기 기능 (insert)
	public int checkLikeProduct(LikeVO productLike) {
		return mapper.insertLikeProduct(productLike);
	}
	//찜하기 취소 기능 (delete)
	public int unCheckLikeProduct(LikeVO productLike) {
		return mapper.deleteLikeProduct(productLike);
	}
	//찜 불러오기
	public int likeChecked(String member_id, int pd_idx) {
		return mapper.selectLikeChecked(member_id, pd_idx);
	}
	
	//판매자 판매내역 불러오기
	public List<ProductVO> getSellerMyPage(int startRow, int listLimit, String member_id) {
		return mapper.selectSellerMyPage(startRow, listLimit, member_id);
	}
	
    // 아이디에 해당하는 구매내역 리스트 조회	
	public List<Map<String, String>> getBuyList(String id) {
		return mapper.selectBuyList(id);
	}
	
	// 아이디에 해당하는 판매내역 리스트 조회
	public List<Map<String, String>> getSaleList(String loggedInUserId) {
		return mapper.selectSaleList(loggedInUserId);
	}
	
	// 거래상태 업데이트
	public int updateProductStatus2(int trade_pd_idx, int trade_status) {
		return mapper.updateProductStatus2(trade_pd_idx, trade_status);
	}
	
	// 상품상태 업데이트
	public int updateProductStatus3(int id, String status) {
		return mapper.updateProductStatus3(id, status);
	}
	


	
	
		
	
}
