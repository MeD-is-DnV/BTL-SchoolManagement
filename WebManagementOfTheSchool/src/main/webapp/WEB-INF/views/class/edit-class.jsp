<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Sửa thông tin lớp học</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="update-class" method="post">
		<div class="form-group">
			<label for="className" class="font-weight-bolder">Tên lớp học
				<span class="text-danger"> (*)</span>
			</label> <input type="text" class="form-control mb-3" id="className"
				name="className" value="${classInfo.className}">

			<c:if test="${classInfo.classID != null}">
				<input type="hidden" id="classID" name="classID"
					value="${classInfo.classID }">
			</c:if>
		</div>
		<div class="form-group">
			<p class="font-weight-bolder">
				Chọn môn học<span class="text-danger"> (*)</span>
			</p>
			<div class="row px-3">
				<c:forEach var="SubjectInfo"
					items="${requestScope.activeSubjectList}">
					<div class="col-12 col-sm-12 col-md-4 col-lg-4 p-3 border">
						<div class="form-check mr-sm-2">
							<input class="form-check-input" type="checkbox" name="subjectID"
								value="${SubjectInfo.subjectID}"
								${subjectsListByClassID.contains(SubjectInfo.subjectID) == true ? "checked" : "" }>
							<label class="form-check-label">
								${SubjectInfo.subjectName} </label>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="form-group mb-5">
			<label class="font-weight-bolder">Trạng thái</label> <select
				name="status" class="form-control">
				<option value="1"
					${classInfo.status == "Đang hoạt động" ? "selected" : "" }>Đang
					hoạt động</option>
				<option value="0"
					${classInfo.status == "Đã ngưng hoạt động" ? "selected" : "" }>Ngưng
					hoạt động</option>
			</select>
		</div>
		<div class="form-group text-center">
			<a href="class-list" class="btn btn-sm btn-secondary">Quay lại</a> <input
				type="submit" class="btn btn-sm btn-success" value="Cập nhật">
		</div>
	</form>
</div>