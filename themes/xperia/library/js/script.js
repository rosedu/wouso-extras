var output = document.querySelector('#output');
var nickname = document.querySelector('#nickname').dataset.name;
var commands = {};
commands.home = {
	command : 'cd ~/home/' + nickname,
	instruction : 'location.href = location.href.substring(0,27)'
};

document.querySelector('h1 span').addEventListener('keydown', function (e) {

	var span = this;
	
	if(span.innerText.trim() != '_') {
		span.classList.remove('blink');
	}

	if(e.keyCode == 13) {
		var h1 = document.createElement('h1');

		for(var i in commands) {
			if(commands[i].command == span.innerText) {
				eval(commands[i].instruction);
				e.preventDefault();
				return false;
			}
		}

		h1.innerHTML = span.innerText + ' : command not found';
		output.appendChild(h1);
		output.scrollTop = output.scrollHeight;
		span.innerText = '';
		e.preventDefault();
	}
	
});

document.querySelector('h1 span').addEventListener('click', function () {
	var span = this;

	if(span.innerText.trim() == '_') {
		span.innerText = '';
		span.classList.remove('blink');
	}
});