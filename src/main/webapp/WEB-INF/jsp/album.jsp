<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>${album.title}</h1>

	<table class="table">
		<tr>
			<th>Track</th>
			<th>Title</th>
		</tr>
		<tbody>
			<c:forEach items="${album.songs}" var="song">
				<tr>
					<td>${song.trackNumber}</td>
					<td><c:url value="/song" var="songURL">
							<c:param name="songId" value="${song.id}" />
						</c:url> <a href="${songURL}">${song.title}</a></td>	
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

<%@include file="footer.jsp"%>
