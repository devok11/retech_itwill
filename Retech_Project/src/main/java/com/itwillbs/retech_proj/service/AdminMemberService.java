package com.itwillbs.retech_proj.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.itwillbs.retech_proj.mapper.AdminMemberMapper;
import com.itwillbs.retech_proj.mapper.MemberMapper;
import com.itwillbs.retech_proj.vo.MemberVO;
import com.itwillbs.retech_proj.vo.ProductVO;
@Service
public class AdminMemberService {
   @Autowired
   private AdminMemberMapper mapper;
   // 회원 총 개수
   public int getMemberListCount(String searchKeyword) {
      return mapper.selectMemberListCount(searchKeyword);
   }
   // 회원 목록
   public List<MemberVO> getMemberList(int startRow, int listLimit, String searchKeyword) {
      return mapper.selectMemberList(startRow, listLimit, searchKeyword);
   }
   // 관리자 권한 부여/해제
   public int changeAdminAuth(String member_isAdmin, String member_id) {
      return mapper.updateAdminAuth(member_isAdmin, member_id);
   }
public int changePoliceAuth(String member_status, String member_id) {
	return mapper.updateStatusAuth(member_status, member_id);
}
public List<MemberVO> getMemberList2() {
	// TODO Auto-generated method stub
	return mapper.selectMemberList2();
}
public List<ProductVO> getProductList() {
	// TODO Auto-generated method stub
	return mapper.selectProductList2();
}
	@Autowired
    private MemberMapper memberMapper;

    public MemberVO getMemberById(String memberId) {
        return memberMapper.selectMemberById(memberId);
    }
	public List<MemberVO> getMemberList22(int startRow, int listLimit, String searchKeyword) {
		return mapper.selectMemberList22(startRow, listLimit, searchKeyword);
	}
   
}