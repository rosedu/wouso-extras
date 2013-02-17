var output = document.querySelector('#output');
var nickname = document.querySelector('#nickname').dataset.nickname;
var commands = {};
commands.home = {
	command : 'cd ~/home/' + nickname,
	instruction : 'location.href = location.href.substring(0,27)'
};

document.querySelector('h1 span').addEventListener('keydown', function (e) {

	var span = this;
	var content = span.textContent || span.innerText;
	
	if(content.trim() != '_') {
		span.classList.remove('blink');
	} else {
		if(!span.innerText)
			span.textContent = '';
		else
			span.innerText = '';
	}

	if(e.keyCode == 13) {
		var h1 = document.createElement('h1');

		for(var i in commands) {
			if(commands[i].command == content) {
				eval(commands[i].instruction);
				e.preventDefault();
				return false;
			}
		}

		h1.innerHTML = content + ' : command not found';
		output.appendChild(h1);
		output.scrollTop = output.scrollHeight;
		if(!span.innerText)
			span.textContent = '';
		else
			span.innerText = '';
		e.preventDefault();
	}
	
});

document.querySelector('h1 span').addEventListener('click', function () {
	var span = this;
	var content = span.textContent || span.innerText;
	
	if(content.trim() == '_') {
		if(!span.innerText)
			span.textContent = '';
		else
			span.innerText = '';

		span.classList.remove('blink');
    }
});