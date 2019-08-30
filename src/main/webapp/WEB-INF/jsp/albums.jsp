<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="smp" %>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>Albums</h1>
	<table class="table">
		<tr>
			<th>Artist</th>
			<th>Title</th>
		</tr>
		<tbody>
			<c:forEach items="${albumList}" var="album">
				<tr>
					<td><c:url value="/artist" var="artistURL">
							<c:param name="artistId" value="${album.artist.id}" />
						</c:url> <a href="${artistURL}">${album.artist.name}</a></td>
					<td><c:url value="/album" var="albumURL">
							<c:param name="albumId" value="${album.id}" />
						</c:url> <a href="${albumURL}">${album.title}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<smp:pagination paginationLink="/albums" totalPages="${totalPages}" />
</div>

<%@include file="footer.jsp"%>
