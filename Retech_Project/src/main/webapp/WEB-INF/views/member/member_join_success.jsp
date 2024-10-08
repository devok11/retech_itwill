<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입 완료</title>
    <link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.1.js"></script>
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }

        body {
            font-family: 'Noto Sans', sans-serif;
            background-color: #f4f7f6;
            display: flex;
            flex-direction: column;
        }

        header, footer {
            background-color: #fff;
            border-bottom: 1px solid #ddd;
            padding: 0px 0;
        }

        .content {
            padding: 50px 0;
            text-align: center;
            flex: 1; 
            margin-top: 0px; 
        }

        .tab {
            width: 720px;
            margin: 0 auto;
            text-align: center;
            margin-top: 0px; 
        }

        .tab > ul {
            display: flex;
            justify-content: space-between;
            height: 40px;
            padding: 0;
            margin: 0;
            list-style: none;
            border-bottom: 2px solid #4CAF50;
            background-color: #eee;
        }

        .tab > ul > li {
            width: 33.33%;
            background-color: #eee;
            border-radius: 10px 10px 0 0;
            display: flex;
            align-items: center; 
            justify-content: center; 
            height: 40px;
            line-height: 40px; 
        }

        .tab > ul > li a {
            display: block;
            width: 100%;
            padding: 15px;
            text-decoration: none;
            color: #555;
            transition: background-color 0.3s, color 0.3s;
        }

        .tab > ul > li.on {
            background-color: #34495E;
            color: white;
            font-weight: bold;
        }

        .tab > ul > li.on a {
            color: white;
        }

        h1 {
            color: #4CAF50;
            font-size: 24px;
            font-weight: bold;
            margin: 20px auto;
            text-align: center;
        }

        .btn-group {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        .btn-group input {
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-home {
            background-color: #34495e;
            color: white;
        }

        .btn-home:hover {
            background-color: #34495e;
        }

        .btn-login {
            background-color: #34495e;
            color: white;
        }

        .btn-login:hover {
            background-color: #34495e;
        }
        
        footer {
		    margin-top: auto;
		    width: 100%;
		}
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
    </header>
    <article class="content">
        <div class="tab">
            <ul>
                <li class="tabMenu">이메일 입력</li>
                <li class="tabMenu">회원정보 입력</li>
                <li class="tabMenu on">가입 완료</li>
            </ul>
        </div>
        
        <h1>회원 가입이 완료되었습니다.</h1>
        <div class="btn-group">
            <input type="button" class="btn-home" value="홈으로" onclick="location.href='/'">
            <input type="button" class="btn-login" value="로그인" onclick="location.href='MemberLogin'">
        </div>
    </article>
</body>
</html>
