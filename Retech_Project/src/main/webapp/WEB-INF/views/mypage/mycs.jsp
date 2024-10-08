<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문의내역</title>
    <link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            display: flex;
            flex-direction: column;
            font-family: Arial, sans-serif;
        }

        .main-content {
            display: flex;
            flex: 1;
            overflow: hidden;
        }

        .sidebar {
            width: 250px;
            background-color: #f4f4f4;
            padding: 20px;
            box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
            height: calc(100vh - 150px);
            overflow-y: auto;
        }

        .sidebar a {
            display: block;
            padding: 10px;
            text-decoration: none;
            color: #333;
            border-radius: 5px;
            margin-bottom: 10px;
            transition: background-color 0.3s ease;
        }

        .sidebar a:hover {
            background-color: #ddd;
        }

        .sidebar a.selected {
            background-color: #34495e;
            color: #fff;
        }

        .content-area {
            flex: 1;
            padding: 20px;
            background-color: #f9f9f9;
            overflow-y: auto;
            justify-content: center;
        }

        .store-info {  
            background-color: #f5f5f5;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
/*             display: flex; */
            align-items: center;
            width: 80%;
            margin: 0 auto;
        }

        .store-info img {
            border-radius: 50%;
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-right: 20px;
        }

        .store-info h2 {
            margin-top: 0;
            font-size: 24px;
        }

        .tabs {
            margin-bottom: 20px;
            list-style-type: none;
            padding: 0;
            display: flex;
            margin-left: 140px;
        }

        .tabs li {
            margin-right: 10px;
        }

        .tabs a {
            display: block;
            padding: 10px 20px;
            text-decoration: none;
            color: #000;
            background-color: #eee;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .tabs a.selected {
            background-color: #34495e;
            color: #fff;
        }

        .content {
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 10px;
            width: 80%;
            margin: 0 auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f4f4f4;
        }

        .status-buttons {
            display: flex;
            gap: 10px;
        }

        .status-buttons button {
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            color: #fff;
            cursor: pointer;
            font-size: 14px;
        }

        .view-detail {
            background-color: #2196F3; 
        }

        .view-detail:hover {
            background-color: #1976D2;
        }
        
         .store-info .progress-container {
/*          display: inline-block */
/*     float: right; /* 오른쪽으로 떠 있게 설정합니다 */ */
    margin-left: 800px; /* 왼쪽 여백을 추가하여 내용과 간격을 둡니다 */
}     
        
         #progress {
    appearance: none;
}
#progress::-webkit-progress-bar {
    background:#f0f0f0;
    border-radius:10px;
    box-shadow: inset 3px 3px 10px #ccc;
     height: 20px;
        width: 400px;
    
}
#progress::-webkit-progress-value {
    border-radius:10px;
    background: #34495E; /* 베이스 색상 */
    background: -webkit-linear-gradient(to right, #BDC3C7, #34495E); /* WebKit 브라우저용 그라디언트 */
    background: linear-gradient(to right, #BDC3C7, #34495E); /* 모든 브라우저용 그라디언트 */

}
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
    </header>

    <div class="main-content">
        <div class="sidebar">
            <a href="SaleHistory">판매내역</a>
            <a href="PurchaseHistory">구매내역</a>
             <a href="PurchaseStoreHistory">스토어 구매내역</a>
            <a href="Wishlist">찜한상품</a>
            <a href="CsHistory" class="selected">문의내역</a>
            <a href="MemberInfo">회원정보수정</a>
        </div>

        <div class="content-area">
            <div class="store-info">
                <div>
                <c:choose>
                	<c:when test="${empty member.member_profile}"><img src="https://cdn.litt.ly/images/U0UQOgi7NRuOXgn6LHSikIDTy1TWh688?s=1200x630&m=inside"></c:when>
                	<c:otherwise><img src="${pageContext.request.contextPath}/resources/images/${member.member_profile}"></c:otherwise>
                </c:choose>
                    <h2>상점 정보</h2>
                    <p>상점명: ${member.member_nickname}</p>
                    <p>지역: ${member.member_address1}</p>
                      <c:choose>
            <c:when test="${member.member_starRate eq 0.0}">
                <p>신뢰지수: -     (<a href="ProductRegistForm"> !!이곳을 클릭해 판매를 시작해주세요!! </a>)</p>
            </c:when>
            <c:otherwise>
                <p>신뢰지수: ${member.member_starRate} / 5.0</p>
            </c:otherwise>
        </c:choose>
                </div>
                <div class="progress-container">
                    <progress id="progress" value="${member.member_starRate}" min="0" max="5.0"></progress>
                </div>
            </div>

            <ul class="tabs">
                <li><a href="#" class="selected">문의내역</a></li>
            </ul>

            <div class="content">
                <c:if test="${not empty csList}">
                    <table>
                        <thead>
                            <tr>
                                <th>문의번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>확인여부</th>
                                <th>상세보기</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cs" items="${csList}">
                                <tr>
                                    <td>${cs.cs_idx}</td>
                                    <td>${cs.cs_subject}</td>
                                    <td>${cs.cs_member_id}</td>
                                    <td><fmt:formatDate value="${cs.cs_date}" pattern="yyyy-MM-dd"/></td>
                                    <td>${cs.cs_check}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/cs/csContent?cs_idx=${cs.cs_idx}" class="view-detail">상세보기</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty csList}">
                    <p>문의내역이 없습니다.</p>
                </c:if>
            </div>
        </div>
    </div>

</body>
</html>
