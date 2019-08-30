(function() {
	let currentSongIndex = 0;

	function getCurrentSongIndex() {
		let request = new XMLHttpRequest();
		request.open("GET", "/getCurrentSongIndex");
		request.setRequestHeader("X-Test", "test1");
		request.setRequestHeader("X-Test", "test2");
		request.addEventListener('load', function(event) {
			if (request.status >= 200 && request.status < 300) {
				currentSongIndex = request.responseText;
				console.log("determined a new currentSongIndex: "
						+ currentSongIndex);
				markCurrentSongAsPlaying();
			} else {
				console.warn(request.statusText, request.responseText);
			}
		});
		request.send();
	}

	function notifyPlayerControllerAboutSongEnded() {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", '/onSongEnded', true);

		//Send the proper header information along with the request
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		xhr.onreadystatechange = function() { // Call a function when the state changes.
		    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
		        // Request finished. Do processing here.
		    	console.log("request finished.")
		    }
		}
		xhr.send();
	}
	
	function markCurrentSongAsPlaying() {
		getCurrentSongIndex();
		$(".tableRow").each(function(index, element) {
			if (index == currentSongIndex) {
				$(element).css("background-color", "#007bff");
				console.log(index + " is " + currentSongIndex);
			} else {
				$(element).css("background-color", "white");
				console.log(index + " is not " + currentSongIndex);
			}
		});
	}

	const audio = document.getElementById('audioId');
	audio.addEventListener("ended", function() {
		notifyPlayerControllerAboutSongEnded();
		audio.pause();
		audio.src = "/srcFromPlaylist";
		audio.load();
		getCurrentSongIndex();
		console.log(currentSongIndex);
		console.log(audio.src);
		audio.play();
		markCurrentSongAsPlaying();
	});
	audio.addEventListener("play", markCurrentSongAsPlaying);
})();