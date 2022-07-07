<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Sửa kết quả thi</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="update-result" method="post">
		<div class="form-row mb-5">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="studentName" class="font-weight-bolder">Tên sinh
					viên </label> <input type="text" class="form-control bg-white"
					id="studentName" name="studentName" disabled
					value="${studentInfo.studentName }"> <input type="hidden"
					id="studentID" name="studentID" value="${studentInfo.studentID }">
					<input type="hidden" name="classID" value="${studentInfo.classID}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="cardID" class="font-weight-bolder">Số CCCD </label> <input
					type="text" class="form-control bg-white" id="cardID" name="cardID"
					disabled value="${studentInfo.cardID }">
			</div>
		</div>
		<h3 class="text-center">Điểm các môn học</h3>
		<p class="text-center text-danger">
			<small>(Chỉ được dùng dấu <strong>(.)</strong> để ngăn cách
				phần thập phân)
			</small>
		</p>
		<c:forEach var="Data" items="${requestScope.resultDetails}">
			<div class="form-row mb-3">
				<div class="col-12 col-sm-12 col-md-6 col-lg-6">
					<label class="font-weight-bolder"> ${Data.subjectName} <span
						class="text-danger"> (*)</span>
					</label> <input type="text" class="form-control"
						name="point-${Data.resultID}" value="${Data.point}"> <input
						type="hidden" name="resultID-${Data.resultID}"
						value="${Data.resultID}"> <input type="hidden"
						name="startDay" value="${Data.startDay}"> <input
						type="hidden" name="endDate" value="${Data.endDate}">
				</div>
				<div class="col-12 col-sm-12 col-md-6 col-lg-6">
					<label class="font-weight-bolder">Trạng thái điểm</label> <select
						class="form-control" name="status-${Data.resultID}">
						<option value="1" ${Data.status == 1 ? "selected" : ""}>Có
							hiệu lực</option>
						<option value="0" ${Data.status == 0 ? "selected" : ""}>Hủy</option>
					</select>
				</div>
			</div>
		</c:forEach>
		<div class="form-group text-center">
			<a href="student-list?class-id=${studentInfo.classID}"
				class="btn btn-sm btn-secondary">Quay lại</a> <input type="submit"
				class="btn btn-sm btn-success" value="Cập nhật">
		</div>
	</form>
</div>