<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="smp" %>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>Artists</h1>

	<table class="table">
		<tr>
			<th>Name</th>
		</tr>
		<tbody>
			<c:forEach items="${artistList}" var="artist">
				<tr>
					<td><c:url value="/artist" var="artistURL">
							<c:param name="artistId" value="${artist.id}" />
						</c:url> <a href="${artistURL}">${artist.name}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<smp:pagination paginationLink="/artists" totalPages="${totalPages}" />
</div>
<%@include file="footer.jsp"%>
