<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Thêm mới môn học</h1>

	<form action="create-new-subject" method="post">
		<div class="form-group">
			<label for="subjectName">Tên môn học <span
				class="text-danger"> (*)</span></label> <input type="text"
				class="form-control mb-3" id="subjectName" name="subjectName"> <input
				type="hidden" id="subjectID" name="subjectID"
				value="${subjectIDRandom}"> <input type="hidden" id="status"
				name="status" value="1">
			
			<%@ include file="/common/error-alert.jsp" %>
		</div>
		<div class="form-group text-center">
			<a href="list-of-subjects" class="btn btn-sm btn-secondary">Quay
				lại</a> <input type="submit" class="btn btn-sm btn-success" value="Thêm">
		</div>
	</form>
</div>