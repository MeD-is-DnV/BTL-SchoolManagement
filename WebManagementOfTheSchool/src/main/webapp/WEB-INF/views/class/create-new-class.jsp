<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Thêm mới lớp học</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="create-new-class" method="post">
		<div class="form-group">
			<label for="className" class="font-weight-bolder">Tên lớp học <span class="text-danger">
					(*)</span></label> <input type="text" class="form-control mb-3" id="className"
				name="className"> <input type="hidden" id="classID"
				name="classID" value="${classIDRandom}"> <input
				type="hidden" id="status" name="status" value="1">
		</div>
		<div class="form-group">
			<p class="font-weight-bolder">
				Chọn môn học<span class="text-danger"> (*)</span>
			</p>
			<c:if test="${numberOfSubjects <= 0}">
				<h5 class="text-danger">
					<span class="font-weight-bolder">Chú ý:</span> Bạn cần phải tạo môn
					học trước khi thêm lớp mới!
				</h5>
			</c:if>
			<c:if test="${numberOfSubjects > 0 }">
				<div class="form-group row px-3">
					<c:forEach var="SubjectInfo"
						items="${requestScope.activeSubjectList}">
						<div class="col-12 col-sm-12 col-md-4 col-lg-4 p-3 border">
							<div class="form-check mr-sm-2">
								<input class="form-check-input" type="checkbox" name="subjectID"
									value="${SubjectInfo.subjectID}"> <label
									class="form-check-label"> ${SubjectInfo.subjectName} </label>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
		<div class="form-group text-center">
			<a href="class-list" class="btn btn-sm btn-secondary">Quay lại</a> <input
				type="submit" class="btn btn-sm btn-success" value="Thêm">
		</div>
	</form>
</div>