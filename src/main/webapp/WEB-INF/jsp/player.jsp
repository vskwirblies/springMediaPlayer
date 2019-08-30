<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/contentArea.css" />
<link rel="stylesheet" type="text/css" href="css/overall.css" />
<link rel="stylesheet" type="text/css" href="css/player.css" />
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0-11/css/all.css" />

<div class="contentArea">
	<h1>Player</h1>

	<div class="contentAreaInnerContent">
		<audio controls src="/srcFromPlaylist" id="audioId">
		</audio>

		<table class="table">
			<tr>
				<th>Play</th>
				<th>Artist</th>
				<th>Title</th>
			</tr>
			<c:forEach items="${playlist}" var="song" varStatus="status">
				<tr class="tableRow">
					<td>
					<form action="/playSongFromPlaylist?songNo=${status.index}" method="POST">
						<button type="submit"><i class="fas fa-play"></i></button>
					</form>
					</td>
					<td>${song.artist.name}</td>
					<td>${song.title}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script src="https://code.jquery.com/jquery-3.4.1.js"
		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/player.js"></script>
</div>

