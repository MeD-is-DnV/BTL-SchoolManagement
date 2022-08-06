<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Danh sách lớp học</h1>

	<%@ include file="/common/success-alert.jsp"%>

	<div class="row mb-3">
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<a href="new-class" class="btn btn-sm btn-success mb-sm-3 mb-2">Thêm
				lớp mới</a> <a href="home" class="btn btn-sm btn-secondary mb-sm-3 mb-2">Trang
				chủ</a>
		</div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4"></div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<form class="form-inline" action="class-list" method="get">
				<div class="form-group mx-sm-3 mb-2">
					<input type="text" class="form-control" id="keyword" name="keyword"
						placeholder="Nhập tên lớp...">
				</div>
				<button type="submit" class="btn btn-primary mb-2">Tìm</button>
			</form>
		</div>
	</div>
	<div class="table-responsive">
		<table class="table text-center">
			<thead class="thead-light">
				<tr>
					<c:if test="${numberOfClasses >= 0}">
						<th scope="col"><a
							href="class-list?sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<c:if test="${numberOfThreadsFound >= 0}">
						<th scope="col"><a
							href="class-list?keyword=${keyword}&sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<th scope="col" class="align-middle">Trạng thái</th>
					<th scope="col" class="align-middle">Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${numberOfClasses > 0}">
					<c:forEach var="Data" items="${requestScope.classList}">
						<tr>
							<td>${Data.className}</td>
							<td><c:if test="${Data.status == 'Đang hoạt động' }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Data.status}</button>
								</c:if> <c:if test="${Data.status == 'Đã ngưng hoạt động' }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Data.status}</button>
								</c:if></td>
							<td><a href="edit-class?class-id=${Data.classID }"
								class="btn btn-sm btn-warning">Sửa</a> <a
								href="class-details?class-id=${Data.classID }"
								class="btn btn-sm btn-primary">Chi tiết</a> <a
								href="student-list?class-id=${Data.classID }"
								class="btn btn-sm btn-success">Danh sách sinh viên</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${numberOfClasses <= 0}">
					<tr>
						<td colspan="4" class="text-center">Chưa có lớp học nào!</td>
					</tr>>
				</c:if>
				<c:if test="${numberOfThreadsFound > 0}">
					<c:forEach var="Class" items="${requestScope.classListByNameAndPage}">
						<tr>
							<td>${Class.className}</td>
							<td><c:if test="${Class.status == 'Đang hoạt động' }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Class.status}</button>
								</c:if> <c:if test="${Class.status == 'Đã ngưng hoạt động' }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Class.status}</button>
								</c:if></td>
							<td><a href="edit-class?class-id=${Class.classID }"
								class="btn btn-sm btn-warning">Sửa</a> <a
								href="class-details?class-id=${Class.classID }"
								class="btn btn-sm btn-primary">Chi tiết</a> <a
								href="student-list?class-id=${Class.classID }"
								class="btn btn-sm btn-success">Danh sách sinh viên</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${numberOfThreadsFound <= 0}">
					<tr>
						<td colspan="4" class="text-center">Không tìm thấy lớp học
							nào!</td>
					</tr>>
				</c:if>
			</tbody>
		</table>
	</div>

	<%@ include file="/common/pagination.jsp"%>
</div>