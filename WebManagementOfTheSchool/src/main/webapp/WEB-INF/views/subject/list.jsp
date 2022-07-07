<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Danh sách môn học</h1>

	<%@ include file="/common/success-alert.jsp"%>

	<div class="row mb-3">
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<a href="new-subject" class="btn btn-sm btn-success mb-sm-3 mb-2">Thêm
				môn học mới</a> <a href="home"
				class="btn btn-sm btn-secondary mb-sm-3 mb-2">Trang chủ</a>
		</div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4"></div>
		<div class="col-12 col-sm-12 col-md-4 col-lg-4">
			<form class="form-inline" action="list-of-subjects" method="get">
				<div class="form-group mx-sm-3 mb-2">
					<input type="text" class="form-control" id="keyword" name="keyword"
						placeholder="Nhập tên môn...">
				</div>
				<button type="submit" class="btn btn-primary mb-2">Tìm</button>
			</form>
		</div>
	</div>
	<div class="table-responsive">
		<table class="table text-center">
			<thead class="thead-light">
				<tr>
					<c:if test="${countOfSubject >= 0}">
						<th scope="col"><a
							href="list-of-subjects?sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<c:if test="${numberOfThreadsFound >= 0}">
						<th scope="col"><a
							href="list-of-subjects?keyword=${keyword}&sort-param=${sortParam}&page=${currentPage}">Tên</a></th>
					</c:if>
					<th scope="col">Trạng thái</th>
					<th scope="col">Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${countOfSubject > 0}">
					<c:forEach var="Data" items="${requestScope.listOfSubjects}">
						<tr>
							<td>${Data.subjectName}</td>
							<td><c:if test="${Data.status == 'Đang tồn tại' }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Data.status}</button>
								</c:if> <c:if test="${Data.status == 'Đã hủy' }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Data.status}</button>
								</c:if></td>
							<td><a href="edit-subject?subject-id=${Data.subjectID }"
								class="btn btn-sm btn-warning">Sửa</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${countOfSubject <= 0}">
					<tr>
						<td colspan="3" class="text-center">Chưa có môn học nào!</td>
					</tr>>
				</c:if>
				<c:if test="${numberOfThreadsFound > 0}">
					<c:forEach var="Subject" items="${requestScope.subjectListByName}">
						<tr>
							<td>${Subject.subjectName}</td>
							<td><c:if test="${Subject.status == 'Đang tồn tại' }">
									<button
										class="btn btn-sm btn-outline-success font-weight-bolder"
										disabled>${Subject.status}</button>
								</c:if> <c:if test="${Subject.status == 'Đã hủy' }">
									<button
										class="btn btn-sm btn-outline-danger font-weight-bolder"
										disabled>${Subject.status}</button>
								</c:if></td>
							<td><a href="edit-subject?subject-id=${Subject.subjectID }"
								class="btn btn-sm btn-warning">Sửa</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${numberOfThreadsFound <= 0}">
					<tr>
						<td colspan="3" class="text-center">Không tìm thấy môn học
							nào!</td>
					</tr>>
				</c:if>
			</tbody>
		</table>
	</div>

	<%@ include file="/common/pagination.jsp"%>
</div>