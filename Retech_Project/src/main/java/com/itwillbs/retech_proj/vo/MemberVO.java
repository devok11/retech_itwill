package com.itwillbs.retech_proj.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberVO {
	private String member_id;
	private String member_email;
	private String member_nickname;
	private String member_passwd;
	private String member_name; 
	private Date member_birth; 
	private String member_phone; 
	private String member_status; 
	private String member_mail_auth; 
	private String member_postcode; 
	private String member_address1; 
	private String member_address2;  
	private String member_profile; 
	private String member_isAdmin;
}
