var output = document.querySelector('#output');

document.querySelector('h1 span').addEventListener('keydown', function (e) {

	var span = this;
	
	if(span.innerText.trim() != '_') {
		span.classList.remove('blink');
	}

	if(e.keyCode == 13) {
		var h1 = document.createElement('h1');
		h1.innerHTML = span.innerText.substring(0, 10) + ' : command not found';
		output.appendChild(h1);
      	output.scrollTop = output.scrollHeight;
      	span.innerText = '';
      	e.preventDefault();
	}
	
});

document.querySelector('h1 span').addEventListener('click', function (e) {
  	span = this;
	if(span.innerText.trim() != '_') {
      	span.innerText = '';
		span.classList.remove('blink');
	}
});

function insertAfter(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}