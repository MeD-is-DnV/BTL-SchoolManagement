<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Thông tin chi tiết sinh viên</h1>

	<form>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-4 col-lg-4">
				<label class="font-weight-bolder">Tên sinh viên </label> <input
					type="text" class="form-control bg-white" disabled
					value="${studentInfo.studentName}">
			</div>
			<div class="col-12 col-sm-12 col-md-4 col-lg-4">
				<label class="font-weight-bolder">Ngày sinh </label> <input
					type="text" class="form-control bg-white" disabled
					value="${studentInfo.dob}">
			</div>
			<div class="col-12 col-sm-12 col-md-4 col-lg-4">
				<label class="font-weight-bolder">Lớp </label> <input
					type="text" class="form-control bg-white" disabled
					value="${studentInfo.className}">
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label class="font-weight-bolder">Số CCCD </label> <input
					type="text" class="form-control bg-white" disabled
					value="${studentInfo.cardID}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label class="font-weight-bolder">Giới tính</label>
				<c:if test="${studentInfo.gender == 1 }">
					<input type="text" class="form-control bg-white" disabled
						value="Nam">
				</c:if>
				<c:if test="${studentInfo.gender == 0 }">
					<input type="text" class="form-control bg-white" disabled
						value="Nữ">
				</c:if>
				<c:if test="${studentInfo.gender == 2 }">
					<input type="text" class="form-control bg-white" disabled
						value="Khác">
				</c:if>
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label class="font-weight-bolder">Số điện thoại </label> <input
					type="text" class="form-control bg-white" disabled
					value="${studentInfo.phoneNumber}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label class="font-weight-bolder">Email </label> <input type="text"
					class="form-control bg-white" disabled value="${studentInfo.email}">
			</div>
		</div>
		<div class="form-group mb-3">
			<label class="font-weight-bolder">Địa chỉ</label>
			<textarea class="form-control bg-white" disabled rows="4">${studentInfo.address}</textarea>
		</div>
		<div class="form-group mb-3">
			<label class="font-weight-bolder">Trạng thái </label> <input type="text"
					class="form-control bg-white" disabled value="${studentInfo.status == 1 ? "Đang theo học" : "Đã nghỉ học"}">
		</div>
		<div class="form-group text-center">
			<a href="student-list?class-id=${studentInfo.classID}"
				class="btn btn-sm btn-secondary">Quay lại</a>
		</div>
	</form>
</div>