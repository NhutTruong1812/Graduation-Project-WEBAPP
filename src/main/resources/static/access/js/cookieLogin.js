            var show = true;   
function ShowPass()
			{
				if(show){
					document.querySelector('#password').type = "text";
					show = false;
				}else{
					document.querySelector('#password').type = "password";
					show = true;
				}
			}
			
function ShowPass2(){
				if(show){
					document.querySelector('#newpassword').type = "text";
					show = false;
				}else{
					document.querySelector('#newpassword').type = "password";
					show = true;
				}
}

function ShowPass3(){
				if(show){
					document.querySelector('#confirmpassword').type = "text";
					show = false;
				}else{
					document.querySelector('#confirmpassword').type = "password";
					show = true;
				}
}

function sendEmail(){
	Email.send({
		Host : "smtp.gmail.com",
		Username: "huynhnhatquang281@gmail.com",
		Password: "lateqpabljwazxkg",
		To: 'huynhnhatquang411281@gmail.com',
		From: "sadasdsad",
		Subject: "quang đẹp trai",
		Body: "And this is the body"
	}).then(
		message => alert(message)
	);
}