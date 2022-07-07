<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Bạn muốn sửa kết quả thi trong
		thời gian nào?</h1>
	<div class="row mb-5">
		<c:forEach var="Time" items="${requestScope.getTime}">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<a class="btn btn-info w-100"
					href="edit-result?student-id=${studentID}&start-day=${Time.startDay}&end-date=${Time.endDate}"><strong>Từ</strong>
					${Time.startDay} <strong>đến</strong> ${Time.endDate}</a>
			</div>
		</c:forEach>
	</div>
	<p class="text-center">
		<a href="student-list?class-id=${classID}" class="btn btn-secondary">Quay lại</a>
	</p>
</div>