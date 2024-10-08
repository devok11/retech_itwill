<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구매내역</title>
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
        
		.store-info img {
            border-radius: 50%;
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-right: 20px;
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
        }

        .store-info {
            background-color: #f5f5f5;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
        }

        .store-info h2 {
            margin-top: 0;
        }

        .tabs {
            margin-bottom: 20px;
            list-style-type: none;
            padding: 0;
            display: flex;
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

        .product-image {
            width: 100px;
            height: 100px;
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

        .cancel-request {
            background-color: #f44336; 
        }

        .confirm-request {
            background-color: #4caf50; 
        }

        .review-request {
            background-color: #2196F3; 
        }

        .cancel-request:hover {
            background-color: #d32f2f;
        }

        .confirm-request:hover {
            background-color: #388e3c;
        }

        .review-request:hover {
            background-color: #1976D2;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.cancel-request').on('click', function() {
                var trade_idx = $(this).data('id');
                if (confirm('거래취소를 요청하시겠습니까?')) {
                    $.ajax({
                        url: 'updateTradeStatusAjax',
                        type: 'POST',
                        data: {
                            trade_idx: trade_idx,
                            trade_status: '4'
                        },
                        success: function(response) {
                            if(response.success) {
                                alert('거래가 취소되었습니다.');
                                location.reload(); // 페이지 새로고침
                            } else {
                                alert('거래 취소 중 오류가 발생했습니다.');
                            }
                        }
                    });
                }
            });

            $('.confirm-request').on('click', function() {
                var productId = $(this).data('id');
                if (confirm('거래 확정하시겠습니까?')) {
                    $.ajax({
                        url: 'updateTransactionStatus',
                        type: 'POST',
                        data: {
                            id: productId,
                            status: '거래확정'
                        },
                        success: function(response) {
                            if(response.success) {
                                alert('거래가 확정되었습니다.');
                                location.reload(); // 페이지 새로고침
                            } else {
                                alert('거래 확정 중 오류가 발생했습니다.');
                            }
                        }
                    });
                }
            });

            $('.review-request').on('click', function() {
                var productId = $(this).data('id');
                window.location.href = '${pageContext.request.contextPath}/writeReview?id=' + productId;
            });
        });
    </script>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
    </header>

    <div class="main-content">
        <div class="sidebar">
            <a href="SaleHistory">판매내역</a>
            <a href="PurchaseHistory">구매내역</a>
            <a href="PurchaseStoreHistory" class="selected">스토어 구매내역</a>
            <a href="Wishlist">찜한상품</a>
            <a href="CsHistory">문의내역</a>
            <a href="MemberInfo">회원정보수정</a>
        </div>

        <div class="content-area">
             <div class="store-info">
                <div>
                	<img src="https://cdn.litt.ly/images/U0UQOgi7NRuOXgn6LHSikIDTy1TWh688?s=1200x630&m=inside">
                	
                                   	
                    <h2>상점 정보</h2>
                    <p>상점명: ${member.member_nickname}</p>
                    <p>지역: ${member.member_address1}</p>
                   <c:choose>
                    	<c:when test="${member.member_starRate eq 0.0}">
                    <p>신뢰지수: -     (<a href="ProductRegistForm"> !!이곳을 클릭해 판매를 시작해주세요!! )</a></p>
                    	</c:when>
                    	<c:otherwise>
                    <p>신뢰지수: ${member.member_starRate} / 5.0</p>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <ul class="tabs">
                <li><a href="#" class="selected">구매내역</a></li>
                <li><a href="#">리뷰</a></li>
            </ul>

            <div class="content">
                <c:if test="${not empty buyList}">
                    <table>
                        <thead>
                            <tr>
                                <th>상품사진</th>
                                <th>상품명</th>
                                <th>상품가격</th>
                                <th>등록날짜</th>
                                <th>거래상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${buyList}">
<%--                                 <c:if test="${product.pd_status == '결제완료' ||  --%>
<%--                                              product.pd_status == '거래취소 요청' ||  --%>
<%--                                              product.pd_status == '거래취소 확정' ||  --%>
<%--                                              product.pd_status == '거래확정'}"> --%>
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty product.pd_image1}">
                                                    <img src="${pageContext.request.contextPath}/resources/img/main/${product.pd_image1}" class="product-image"/>
                                                </c:when>
                                                <c:otherwise>
                                                    No Image
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><a href="${pageContext.request.contextPath}/productDetail?pd_idx=${product.pd_idx}">${product.pd_subject}</a></td>
<%--                                         <td>${product.pd_content}</td> --%>
										<td>${product.pd_price}</td>
                                        <td>${product.pd_first_date}</td>
                                        <td>
                                            <div class="status-buttons">
                                                <c:choose>
                                                    <c:when test="${product.trade_status == 1}">
                                                        예약중
                                                    </c:when>
                                                    <c:when test="${product.trade_status == 2}">
                                                        결제완료
                                                        <button class="status-button cancel-request" data-id="${product.pd_idx}">거래취소요청</button>
                                                        <button class="status-button confirm-request" data-id="${product.pd_idx}">거래확정</button>
                                                    </c:when>
                                                    <c:when test="${product.trade_status == 3}">
                                                        거래완료
                                                        <button class="status-button review-request" data-id="${product.pd_idx}">리뷰쓰기</button>
                                                    </c:when>
                                                    <c:when test="${product.trade_status == 4}">
                                                        거래취소대기
                                                    </c:when>
                                                    <c:when test="${product.trade_status == 5}">
                                                        거래취소승인
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </td>
                                    </tr>
<%--                                 </c:if> --%>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty buyList}">
                    <table class="mypage">
                        <tr>
                            <td align="center" colspan="6">검색결과가 없습니다.</td>
                        </tr>
                    </table>
                </c:if>
            </div>
        </div>
    </div>

</body>
</html>
 