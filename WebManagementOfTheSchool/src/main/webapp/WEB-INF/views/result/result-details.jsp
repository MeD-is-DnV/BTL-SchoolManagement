<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<div class="container">
	<c:if test="${numberOfExams == 0}">
		<h1 class="text-center mb-5 mt-3">Chưa có thống kê kết quả thi
			của lớp này!</h1>
	</c:if>

	<c:if test="${numberOfExams > 0}">
		<h1 class="text-center mb-5 mt-3">Chi tiết kết quả thi của lớp
			${className}</h1>

		<div class="accordion" id="accordionExample">
			<c:forEach var="Time" items="${requestScope.resultTimeList}"
				varStatus="j" begin="0" step="1">
				<div class="card">
					<div class="card-header" id="heading-${Time.startDay}">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block text-left" type="button"
								data-toggle="collapse" data-target="#collapse-${Time.startDay}"
								aria-expanded="true" aria-controls="collapse-${Time.startDay}">
								Thời gian thi: Từ <strong>${Time.startDay}</strong> Đến <strong>${Time.endDate}</strong>
							</button>
						</h2>
					</div>

					<div id="collapse-${Time.startDay}" class="collapse show"
						aria-labelledby="heading-${Time.startDay}"
						data-parent="#accordionExample">
						<div class="card-body">
							<div class="table-responsive">
								<table class="table text-center">
									<thead class="thead-light">
										<tr>
											<th scope="col">Tên sinh viên</th>
											<th scope="col">Số CCCD</th>
											<th scope="col">Ngày sinh</th>
											<c:forEach var="SubjectInfo"
												items="${requestScope.listOfSubjects}">
												<th scope="col">${SubjectInfo.subjectName}</th>
											</c:forEach>
											<th scope="col">Thao tác</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="Data" items="${requestScope.resultDetails}">
											<c:if
												test="${Data[Time.startDay].equalsIgnoreCase(Time.startDay)}">
												<tr>
													<td>${Data.studentName}</td>
													<td>${Data.cardID}</td>
													<td>${Data.dob}</td>
													<c:forEach var="SubjectInfo"
														items="${requestScope.listOfSubjects}">
														<td>${Data[SubjectInfo.subjectName]}</td>
													</c:forEach>
													<td><a
														href="edit-result?student-id=${Data.studentID}&start-day=${Data.startDayForURL}&end-date=${Data.endDateForURL}"
														class="btn btn-sm btn-success">Sửa kết quả thi</a></td>
												</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>

	<div class="text-center mt-3 mb-5">
		<a href="student-list?class-id=${classID}"
			class="btn btn-sm btn-secondary">Quay lại</a>
	</div>
</div>