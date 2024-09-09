<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%-- <%-- 날짜 등의 출력 형식 변경을 위한 JSTL - format(fmt) 라이브러리 등록 --%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>차트</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/chart.css">
  <link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
  <link href="${pageContext.request.contextPath}/resources/css/admin_default.css" rel="stylesheet" type="text/css">
  <script src="${pageContext.request.servletContext.contextPath}/resources/js/jquery-3.7.1.js"></script>
</head>
<body>
  <header>
    <jsp:include page="/WEB-INF/views/inc/admin_top.jsp"></jsp:include>  
  </header>
  <div class="inner">
    <section class="wrapper">
      <jsp:include page="/WEB-INF/views/inc/admin_side_nav.jsp"></jsp:include>
      <article>
      </article>
    </section>
  </div>

  <!-- 차트 컨테이너 -->
  <div class="chart-container">
    <canvas id="line-chart"></canvas>
    <!-- 라인 차트 -->
  </div>

  <div class="chart-container">
    <canvas id="pie-chart"></canvas>
    <!-- 파이 차트 -->
  </div>
  
  <div class="chart-container">
    <canvas id="bar-chart"></canvas>
    <!-- 막대 차트 -->
  </div>

  <!-- Chart.js 라이브러리 -->
  <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>
  
  <!-- 서버에서 전달된 JSON 데이터 JavaScript로 사용 -->
  <script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        const memberData = JSON.parse('${memberData}');
        console.log('Member Data:', memberData);

        function getLast7Days() {
            const today = new Date();
            const dates = [];
            for (let i = 6; i >= 0; i--) {
                const date = new Date(today);
                date.setDate(today.getDate() - i);
                dates.push(date.toISOString().split('T')[0]);
            }
            return dates;
        }

        function formatDate(dateString) {
            const date = new Date(dateString);
            if (isNaN(date.getTime())) {
                console.error('Invalid date:', dateString);
                return null;
            }
            return date.toISOString().split('T')[0];
        }

        const labels = getLast7Days();
        console.log('Labels:', labels);

        const subscriptions = labels.map(date => {
            const count = memberData.reduce((acc, item) => {
                const itemDate = formatDate(item.member_subscription);
                console.log('Item Date:', itemDate, 'Comparing with:', date);
                return itemDate === date ? acc + 1 : acc;
            }, 0);
            console.log('Date:', date, 'Count:', count);
            return count;
        });

        const member_subscription = {
            label: '가입자 수',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 0, 0)',
            data: subscriptions,
        };

        const chartData = {
            labels: labels,
            datasets: [member_subscription]
        };

        const config = {
            type: 'line',
            data: chartData,
            options: {
                maintainAspectRatio: false,
                responsive: true,
            }
        };

        const lineCtx = document.getElementById('line-chart').getContext('2d');
        new Chart(lineCtx, config);

        // 파이 차트
        const productData = JSON.parse('${productData}');
        console.log('Product Data:', productData);
        
        const extractLastTwoChars = (str) => str.slice(-2); // 마지막 두글자만 추출
        
        const counts = productData.reduce((acc, item) => { //pd_category의 마지막 두글자만 계산
            const key = extractLastTwoChars(item.pd_category);
            acc[key] = (acc[key] || 0) + 1; // key 값을 가져오거나 없으면 0으로 초기화
            return acc; // 누적값 반환
        }, {});

        const pieLabels = Object.keys(counts);
        const pieData = Object.values(counts);
        const pieColors = [
            "rgb(0, 0, 255)",
            "rgb(255, 0, 0)",
            "rgb(0, 0, 0)",
            "rgb(128, 128, 128)",
        ].slice(0, pieLabels.length);

        const pieCtx = document.getElementById('pie-chart').getContext('2d');
        
        const pieChart = new Chart(pieCtx, {
            type: 'pie',
            data: {
                labels: pieLabels,
                datasets: [
                    {
                        data: pieData,
                        backgroundColor: pieColors
                    }
                ]
            },
            options: {
                plugins: {
                    legend: {
                        display: true,
                        labels: {
                            color: 'blue',
                            font: {
                                size: '20px',
                                style: 'italic'
                            }
                        }
                    }
                }
            }
        });
    });
    // 막대차트
    
    function getLast7Days() {
        const today = new Date();
        const dates = [];
        for (let i = 6; i >= 0; i--) {
            const date = new Date(today);
            date.setDate(today.getDate() - i);
            dates.push(date.toISOString().split('T')[0]);
        }
        return dates;
    }
    
    function formatDate(dateString) {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) {
            console.error('Invalid date:', dateString);
            return null;
        }
        return date.toISOString().split('T')[0];
    }
    
    
   	const labels = getLast7Days();
   	
      let visitor = {
          label: '거래완료 수',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgb(255, 0, 0)',
          data: [],
      };


      const data = {
        labels: labels,
        datasets: [visitor]
      };

      const config = {
        type: 'bar',
        data: data,
        options: {
          maintainAspectRatio:false,
          scales: {
            x: {
              stacked: true
            },
            y: {
              stacked: true
            }
          }
        }
      };

    const myChart = new Chart(
        document.getElementById('bar-chart'),
        config
      );    
  </script>
</body>
</html>
