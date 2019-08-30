<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>${song.title}</h1>
	<div class="contentAreaInnerContent">
		<p>${song.filepath}</p>

		<c:url value="/play" var="playURL">
			<c:param name="songId" value="${song.id}" />
		</c:url>
		<!-- 
		<a href="/addToPlaylist?songId=${song.id}">Add to playlist</a>
		 -->
		 
		 <form action="/addToPlaylist?songId=${song.id}" method="POST">
		 	<button type="submit" class="btn btn-primary">Add to playlist</button>
		 </form>
		  <!-- 
		  <button onclick="addSong(${song.id})">Add to playlist</button>
		   -->
	</div>
</div>

<%@include file="footer.jsp"%>
