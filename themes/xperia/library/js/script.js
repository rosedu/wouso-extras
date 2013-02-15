$('h1 span').on('keydown', function (e) {

	var span = $(this);
	if(span.text().trim() != '_') {
		span.removeClass('blink');
	}

	if(e.keyCode == 13) {
		$('<h1>' + span.text().substring(0,10) + ' : command not found </h1>').insertAfter(span);
	}


});

