<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Thêm mới sinh viên</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="create-new-student" method="post">
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="studentName" class="font-weight-bolder">Tên sinh
					viên <span class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="studentName"
					name="studentName"><input type="hidden" id="status"
					name="status" value="1"> <input type="hidden"
					id="studentID" name="studentID" value="${studentIDRandom}">
				<input type="hidden" id="classID" name="classID" value="${classID}">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="dob" class="font-weight-bolder">Ngày sinh <span
					class="text-danger"> (*)</span>
				</label> <input type="date" class="form-control" id="dob" name="dob">
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="cardID" class="font-weight-bolder">Số CCCD <span
					class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="cardID" name="cardID">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<p class="font-weight-bolder">Giới tính</p>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="male" value="1" checked> <label
						class="form-check-label" for="male">Nam</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="female" value="0"> <label class="form-check-label"
						for="female">Nữ</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="other" value="2"> <label class="form-check-label"
						for="other">Khác</label>
				</div>
			</div>
		</div>
		<div class="form-row mb-3">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="phoneNumber" class="font-weight-bolder">Số điện
					thoại <span class="text-danger"> (*)</span>
				</label> <input type="text" class="form-control" id="phoneNumber"
					name="phoneNumber">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="email" class="font-weight-bolder">Email <span
					class="text-danger"> (*)</span>
				</label> <input type="email" class="form-control" id="email" name="email">
			</div>
		</div>
		<div class="form-group mb-3">
			<label class="font-weight-bolder">Địa chỉ</label>
			<textarea class="form-control" id="address" name="address" rows="4"></textarea>
		</div>
		<div class="form-group text-center">
			<a href="student-list?class-id=${classID}" class="btn btn-sm btn-secondary">Quay lại</a> <input
				type="submit" class="btn btn-sm btn-success" value="Thêm">
		</div>
	</form>
</div>