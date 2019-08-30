<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="smp" %>  

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>Songs</h1>

	<table class="table">
		<tr>
			<th>Artist</th>
			<th>Title</th>
		</tr>
		<tbody>
			<c:forEach items="${songList}" var="song">
				<tr>
					<td><c:url value="/artist" var="artistURL">
							<c:param name="artistId" value="${song.artist.id}" />
						</c:url> <a href="${artistURL}">${song.artist.name}</a></td>
					
					<td><c:url value="/song" var="songURL">
							<c:param name="songId" value="${song.id}" />
						</c:url> <a href="${songURL}">${song.title}</a></td>	
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<smp:pagination paginationLink="/songs" totalPages="${totalPages}" />
</div>
<%@include file="footer.jsp"%>
