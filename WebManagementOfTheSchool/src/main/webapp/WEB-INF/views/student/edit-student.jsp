<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Sửa thông tin sinh viên</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="update-student" method="post">
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="studentName" class="font-weight-bolder">Tên sinh
					viên <span class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="studentName"
					name="studentName" value="${studentInfo.studentName}"><input
					type="hidden" id="studentID" name="studentID"
					value="${studentInfo.studentID}"> <input type="hidden"
					id="classID" name="classID" value="${studentInfo.classID}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="dob" class="font-weight-bolder">Ngày sinh <span
					class="text-danger"> (*)</span>
				</label> <input type="date" class="form-control" id="dob" name="dob"
					value="${studentInfo.dobForEdit}">
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="cardID" class="font-weight-bolder">Số CCCD <span
					class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="cardID" name="cardID"
					value="${studentInfo.cardID}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<p class="font-weight-bolder">Giới tính</p>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="male" value="1" ${studentInfo.gender == 1 ? "checked" : ""}>
					<label class="form-check-label" for="male">Nam</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="female" value="0" ${studentInfo.gender == 0 ? "checked" : ""}>
					<label class="form-check-label" for="female">Nữ</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="other" value="2" ${studentInfo.gender == 2 ? "checked" : ""}>
					<label class="form-check-label" for="other">Khác</label>
				</div>
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="phoneNumber" class="font-weight-bolder">Số điện
					thoại <span class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="phoneNumber"
					name="phoneNumber" value="${studentInfo.phoneNumber}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="email" class="font-weight-bolder">Email <span
					class="text-danger"> (*)</span>
				</label> <input type="email" class="form-control" id="email" name="email"
					value="${studentInfo.email}">
			</div>
		</div>
		<div class="form-group mb-3">
			<label class="font-weight-bolder">Địa chỉ</label>
			<textarea class="form-control" id="address" name="address" rows="4">${studentInfo.address}</textarea>
		</div>
		<div class="form-group mb-5">
			<label class="font-weight-bolder">Trạng thái</label> <select
				class="form-control" id="status" name="status">
				<option value="1" ${studentInfo.status == 1 ? "selected" : ""}>Đang
					theo học</option>
				<option value="0" ${studentInfo.status == 0 ? "selected" : ""}>Đã
					nghỉ học</option>
			</select>
		</div>
		<div class="form-group text-center">
			<a href="student-list?class-id=${studentInfo.classID}"
				class="btn btn-sm btn-secondary">Quay lại</a> <input type="submit"
				class="btn btn-sm btn-success" value="Cập nhật">
		</div>
	</form>
</div>