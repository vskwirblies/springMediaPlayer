function getParam(name){
   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
      return decodeURIComponent(name[1]);
}

(function() {
	let pageNo = getParam("page");

	// mark the selected page
	$(".page-item").each(function(index, item) {
		if ($(item).text() == pageNo) $(item).addClass("active");
	});
})();
