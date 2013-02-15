document.querySelector('h1 span').addEventListener('keydown', function (e) {

	var span = this;
	
	if(span.innerText.trim() != '_') {
		span.classList.remove('blink');
	}

	if(e.keyCode == 13) {
		var h1 = document.createElement('h1');
		h1.innerHTML = span.innerText.substring(0, 10) + ' : command not found';
		insertAfter(this.parentNode, h1);		
	}
	
});

function insertAfter(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}