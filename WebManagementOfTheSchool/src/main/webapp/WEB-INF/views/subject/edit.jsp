<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Sửa môn học</h1>

	<form action="update-subject" method="post">
		<div class="form-group">
			<label for="subjectName">Tên môn học <span
				class="text-danger"> (*)</span></label> <input type="text"
				class="form-control" id="subjectName" name="subjectName"
				value="${subjectInfo.subjectName }">

			<c:if test="${subjectInfo.subjectID != null}">
				<input type="hidden" id="subjectID" name="subjectID"
					value="${subjectInfo.subjectID }">
			</c:if>

			<%@ include file="/common/error-alert.jsp" %>
		</div>
		<div class="form-group mb-5">
			<label>Trạng thái</label> <select name="status" class="form-control">
				<option value="1"
					${subjectInfo.status == "Đang tồn tại" ? "selected" : "" }>Đang
					tồn tại</option>
				<option value="0"
					${subjectInfo.status == "Đã hủy" ? "selected" : "" }>Hủy</option>
			</select>
		</div>
		<div class="form-group text-center">
			<a href="list-of-subjects" class="btn btn-sm btn-secondary">Quay
				lại</a> <input type="submit" class="btn btn-sm btn-primary"
				value="Cập nhật">
		</div>
	</form>
</div>