<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Danh sách sinh viên</h1>

	<%@ include file="/common/success-alert.jsp"%>

	<div class="row mb-3">
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<a href="home" class="btn btn-sm btn-secondary mb-sm-3 mb-2">Trang
				chủ</a>
		</div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4"></div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<form class="form-inline" action="all-students" method="get">
				<div class="form-group mx-sm-3 mb-2">
					<input type="text" class="form-control" id="keyword" name="keyword"
						placeholder="Nhập tên sinh viên...">
				</div>
				<button type="submit" class="btn btn-primary mb-2">Tìm</button>
			</form>
		</div>
	</div>
	<div class="table-responsive">
		<table class="table text-center">
			<thead class="thead-light">
				<tr>
					<c:if test="${numberOfStudents >= 0}">
						<th scope="col"><a
							href="all-students?sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<c:if test="${numberOfThreadsFound >= 0}">
						<th scope="col"><a
							href="all-students?keyword=${keyword}&sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<th scope="col">Số CCCD</th>
					<th scope="col">Số điện thoại</th>
					<th scope="col">Giới tính</th>
					<th scope="col">Trạng thái</th>
					<th scope="col">Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${numberOfStudents > 0}">
					<c:forEach var="Data" items="${requestScope.allStudents}">
						<tr>
							<td>${Data.studentName}</td>
							<td>${Data.cardID}</td>
							<td>${Data.phoneNumber}</td>
							<td>${Data.gender == 1 ? "Nam" : Data.gender == 0 ? "Nữ" : "Khác"}</td>
							<td><c:if test="${Data.status == 1 }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Data.status == 1 ? "Đang theo học" : "Đã nghỉ"}</button>
								</c:if> <c:if test="${Data.status == 0 }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Data.status == 0 ? "Đã nghỉ" : "Đang theo học"}</button>
								</c:if></td>
							<td><a href="edit-student?student-id=${Data.studentID }"
								class="btn btn-sm btn-warning">Sửa</a> <a
								href="student-details?student-id=${Data.studentID }"
								class="btn btn-sm btn-primary">Chi tiết</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${numberOfStudents <= 0}">
					<tr>
						<td colspan="6" class="text-center">Chưa có sinh viên nào!</td>
					</tr>>
				</c:if>
				<c:if test="${numberOfThreadsFound > 0}">
					<c:forEach var="Data" items="${requestScope.allStudentsByName}">
						<tr>
							<td>${Data.studentName}</td>
							<td>${Data.cardID}</td>
							<td>${Data.phoneNumber}</td>
							<td>${Data.gender == 1 ? "Nam" : Data.gender == 0 ? "Nữ" : "Khác"}</td>
							<td><c:if test="${Data.status == 1 }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Data.status == 1 ? "Đang theo học" : "Đã nghỉ"}</button>
								</c:if> <c:if test="${Data.status == 0 }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Data.status == 0 ? "Đã nghỉ" : "Đang theo học"}</button>
								</c:if></td>
							<td><a href="edit-student?student-id=${Data.studentID }"
								class="btn btn-sm btn-warning">Sửa</a> <a
								href="student-details?student-id=${Data.studentID }"
								class="btn btn-sm btn-primary">Chi tiết</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${numberOfThreadsFound <= 0}">
					<tr>
						<td colspan="6" class="text-center">Không tìm thấy sinh viên
							nào!</td>
					</tr>>
				</c:if>
			</tbody>
		</table>
	</div>

	<%@ include file="/common/pagination.jsp"%>
</div>