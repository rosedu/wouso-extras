var output = document.querySelector('#output');
var nickname = document.querySelector('#nickname').dataset.nickname;
var commands = {};
commands.home = {
	command : 'cd /home/' + nickname,
	instruction : 'location.href = location.href.substring(0,27)'
};
commands.online = {
	command : 'online',
	instruction : 'displayOnlinePlayers()'
};
commands.nick = {
	command : 'nick',
	instruction : 'updateNickname()'
};
commands.theme = {
	command : 'theme',
	instruction : 'updateTheme()'
};

document.querySelector('h1 span').addEventListener('keydown', function (e) {

	var span = this;
	var content = span.textContent || span.innerText;

	console.log(content);

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
			var re = new RegExp(commands[i].command, "ig");
			var match = re.test(content);

			if(match) {
				eval(commands[i].instruction);
				if(!span.innerText)
					span.textContent = '';
				else
					span.innerText = '';
				e.preventDefault();
				return false;
			}
		}

		h1.innerHTML = content + ' : command not found';
		output.style.overflow = 'scroll';
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

var updateNickname = function () {
	var span = document.querySelector('h1 span');
	var content = span.textContent || span.innerText;
	content = content.split(' ');
	content.shift();
	content.join('');
	var formdata = new FormData();
	formdata.append('nickname', content);

	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'https://woso.cs.pub.ro/2013/api/info/nickname/', true);
	xhr.onload = function(e) {
		if (this.status == 200) {

			var response = JSON.parse(this.responseText);
			var h3 = document.createElement('h3');
          	if(response.success)
				h3.innerHTML = 'Nickname updated';
          	else
              h3.innerHTML = 'Nickname update failed, reason: ' + response.error;
			output.appendChild(h3);
			output.style.overflow = 'scroll';

		}
	};

	xhr.send(formdata);

};

var updateTheme = function () {
	var span = document.querySelector('h1 span');
	var content = span.textContent || span.innerText;
	content = content.split(' ');
	content.shift();
	content.join('');
	var formdata = new FormData();
	formdata.append('theme', content);

	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'https://woso.cs.pub.ro/2013/api/info/theme/', true);
	xhr.onload = function(e) {
		if (this.status == 200) {

			var response = JSON.parse(this.responseText);
			var h3 = document.createElement('h3');
          	if(response.success)
				h3.innerHTML = 'Theme updated';
          	else
              h3.innerHTML = 'Theme update failed, reason: ' + response.error;
			output.appendChild(h3);
			output.style.overflow = 'scroll';

		}
	};

	xhr.send(formdata);

};

var displayOnlinePlayers = function () {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'https://woso.cs.pub.ro/2013/api/info/online/', true);
	xhr.onload = function(e) {
		if (this.status == 200) {

			var response = JSON.parse(this.responseText);
			var h3 = document.createElement('h3');
			h3.innerHTML = 'Players online ('+response.length+') :';
			output.appendChild(h3);
			output.style.overflow = 'scroll';
			response.forEach(function (player) {
				h3 = document.createElement('h3');
				h3.innerHTML = player.nickname;
				output.appendChild(h3);
				output.scrollTop = output.scrollHeight;
			});
		}
	};

	xhr.send();
};