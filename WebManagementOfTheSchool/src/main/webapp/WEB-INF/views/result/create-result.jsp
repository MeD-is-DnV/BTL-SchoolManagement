<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<div class="container">
	<h1 class="text-center mb-5 mt-3">Tạo kết quả thi</h1>

	<%@ include file="/common/error-alert.jsp" %>

	<form action="create-new-result" method="post">
		<div class="form-row mb-5">
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="startDay" class="font-weight-bolder">Ngày bắt
					đầu thi <span class="text-danger"> (*)</span>
				</label> <input type="date" class="form-control" id="startDay"
					name="startDay">
			</div>
			<div class="col-12 col-sm-12 col-md-6 col-lg-6">
				<label for="endDate" class="font-weight-bolder">Ngày kết
					thúc <span class="text-danger"> (*)</span>
				</label> <input type="date" class="form-control" id="endDate" name="endDate">
			</div>
		</div>
		<h3 class="text-center">Điểm các môn học</h3>
		<p class="text-center text-danger">
			<small>(Chỉ được dùng dấu <strong>(.)</strong> để ngăn cách
				phần thập phân)
			</small>
		</p>
		<div class="form-row mb-3">
			<div class="table-responsive">
				<table class="table text-center">
					<thead class="thead-light">
						<tr>
							<th scope="col">#</th>
							<th scope="col">Tên sinh viên</th>
							<th scope="col">Số CCCD</th>
							<c:forEach var="SubjectInfo"
								items="${requestScope.listOfSelectedSubjects}">
								<th scope="col">${SubjectInfo.subjectName}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="StudentInfo"
							items="${requestScope.studentListByClassID}" varStatus="i"
							begin="0" step="1">
							<tr>
								<th scope="row"><c:out value="${i.count}" /></th>
								<td>${StudentInfo.studentName}<input type="hidden"
									name="studentID-${StudentInfo.studentID}"
									value="${StudentInfo.studentID}"><input type="hidden"
									name="status" value="1"> <input type="hidden"
									name="classID" value="${StudentInfo.classID}"></td>
								<td>${StudentInfo.cardID}</td>
								<c:forEach var="SubjectInfo"
									items="${requestScope.listOfSelectedSubjects}">
									<td><input type="text" class="form-control"
										name="point-${StudentInfo.studentID}-${SubjectInfo.subjectID}"></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="form-group text-center">
			<a href="student-list?class-id=${classID}"
				class="btn btn-sm btn-secondary">Quay lại</a> <input type="submit"
				class="btn btn-sm btn-success" value="Tạo">
		</div>
	</form>
</div>