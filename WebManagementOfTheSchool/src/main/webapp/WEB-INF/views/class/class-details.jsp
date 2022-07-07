<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<div class="container">
	<h1 class="text-center mb-5 mt-3">Thông tin lớp học</h1>

	<form action="update-class" method="post">
		<div class="form-group">
			<label for="className" class="font-weight-bolder">Tên lớp học</label> <input type="text"
				class="form-control mb-3 bg-white" id="className" name="className"
				value="${classInfo.className}" disabled>
		</div>
		<div class="form-group">
			<p class="font-weight-bolder">Danh sách môn học</p>
			<div class="row px-3">
				<c:forEach var="SubjectInfo"
					items="${requestScope.listOfSelectedSubjects}">
					<div class="col-12 col-sm-12 col-md-4 col-lg-4 p-3 border">
						<label> ${SubjectInfo.subjectName} </label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<label class="font-weight-bolder">Trạng thái</label> <input type="text" class="form-control bg-white"
				disabled value="${classInfo.status}">
		</div>
		<div class="form-group text-center">
			<a href="class-list" class="btn btn-sm btn-secondary">Quay lại</a>
		</div>
	</form>
</div>