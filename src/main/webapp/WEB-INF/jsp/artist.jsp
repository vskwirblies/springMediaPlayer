<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>${artist.name}</h1>
	<img class="artistImg" src="${artistImgUrl}" alt="Artists image" />

	<h2>Albums</h2>
	<table class="table">
		<tr>
			<th>Title</th>
		</tr>
		<tbody>
			<c:forEach items="${artist.albums}" var="album">
				<tr>
					<td><c:url value="/album" var="albumURL">
							<c:param name="albumId" value="${album.id}" />
						</c:url> <a href="${albumURL}">${album.title}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@include file="footer.jsp"%>
