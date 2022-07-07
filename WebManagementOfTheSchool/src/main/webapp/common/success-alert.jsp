<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<c:if test="${sessionScope.SUCCESS_MSG != null}">
	<div class="alert alert-success alert-dismissible fade show mb-5"
		role="alert">
		<strong>${sessionScope.SUCCESS_MSG}</strong>
		<%
		session.setAttribute("SUCCESS_MSG", null);
		%>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</c:if>