app.controller('dangky-ctrl', function($scope, $http, $timeout) {
	window.socket.setOnUser({
		onCreate: function(data) { },
		onUpdate: function(data) { },
		onDelete: function(data) { }
	})

	const userAPI = new GlobalAPI();
	userAPI.setNamespace(GlobalAPI.namespace.USER);

	const registeractivesAPI = new GlobalAPI();
	registeractivesAPI.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);

	const shopAPI = new GlobalAPI();
	shopAPI.setNamespace(GlobalAPI.namespace.MYSHOP);

	const mailAPI = new GlobalAPI();
	mailAPI.setNamespace(GlobalAPI.namespace.MAIL);


	const uploadAPI = new GlobalAPI();
	uploadAPI.setNamespace(GlobalAPI.namespace.UPLOAD);

	const authoritiesAPI = new GlobalAPI();
	authoritiesAPI.setNamespace(GlobalAPI.namespace.AUTHORITY);

	const mybcoinsAPI = new GlobalAPI();
	mybcoinsAPI.setNamespace(GlobalAPI.namespace.MYBCOIN);

	$scope.items = [];
	$scope.form = {};

	//Xóa trống form
	$scope.reset = function() {
		$scope.form = {};
		document.querySelector('#username').value = '';
		document.querySelector('#fullname').value = '';
		document.querySelector('#email').value = '';
		document.querySelector('#phonenum').value = '';
		document.querySelector('#password').value = '';
		document.querySelectorAll('#avatar__register__logo')[0] = '';
		document.querySelector('#confirmpassword').value = '';
	}

	$scope.create = async function() {
		let subjectmail = "Xác nhận tham gia cộng đồng BIGLOBBY";
		var pass = document.querySelector('#password').value;
		var item = angular.copy($scope.form);
		item.password = pass;
		let user = await userAPI.create(item);
		sessionStorage.setItem('idUsersession', JSON.stringify(user.id));
		await registeractivesAPI.create(user.id);
		let shop = {};
		shop.intro = '';
		shop.location = '';
		shop.user = user;
		await shopAPI.create(shop);
		let authorities_Obj = {};
		authorities_Obj.user = { id: user.id };
		authorities_Obj.role = { id: 1 };
		authorities_Obj.authorizeDate = new Date();
		await authoritiesAPI.create(authorities_Obj);
		let bcoin_obj ={};
		bcoin_obj.user = { id: user.id };
		bcoin_obj.coinnum = 0;
		await mybcoinsAPI.create(bcoin_obj)
		$scope.reset();
		alert("Vui lòng đợi trong giây lát");
		await $scope.sendmail(user.email, subjectmail);
	}

	// gửi mail
	$scope.sendmail = function(to, subject) {
		mailAPI.getByProperties({ 'mailer.to': to, 'mailer.subject': subject }).then(resp => {
			alert("Mã đã được gửi đến mail vui lòng kiểm tra mail của bạn");
		}).catch(error => {
			alert("Lỗi gửi mail")
			console.log("Error: " + error)
		})
	}


	//Image banner
	$scope.imageChanged = async function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		await GlobalAPI.uploadFile('folder', data).then(resp => {
			console.log(resp.data);
			let path = resp.data[0];
			$scope.form.avatar = path.slice(-26);
			$scope.$digest();
			console.log($scope.form.avatar)
		}).catch(error => {
			alert("Lỗi poster")
			console.log("Error: ", error)
		})
	}


	Validator({
		form: '#FormDangKy',
		formGroupSelector: '.form-group',
		errorSelector: '.form-message',
		invalidSelector: '.invalid',
		rules: [
			Validator.isRequired("#username", 'Vui lòng nhập username!'),
			Validator.customRule("#username", (async function(username) {
				let user = await userAPI.getByProperty("username", username);
				if (user) {
					return false;
				}
				return true;
			}), 'Username đã được sử dụng!'),
			Validator.isRequired("#fullname", 'Vui lòng nhập fullname!'),
			Validator.isRequired("#email", 'Vui lòng nhập email!'),
			Validator.isEmail("#email", 'không đúng định dạng email'),
			Validator.customRule("#email", (async function(email) {
				let user = await userAPI.getByProperty("email", email);
				if (user) {
					return false;
				}
				return true;
			}), 'Email đã được sử dụng!'),
			Validator.isRequired("#phonenum", 'Vui lòng nhập phonenum!'),
			Validator.isPhoneNumber("#phonenum", 'Không đúng định dạng số điện thoại!'),
			Validator.isRequired("#password", 'Vui lòng nhập password!'),
			Validator.minLength("#password", 6),
			Validator.isEquals("#confirmpassword", function() {
				return document.querySelector("#FormDangKy #password").value;
			}, 'Mật khẩu nhập lại không chính xác!'),
			Validator.isRequired("input[name='gender']", 'Vui lòng chọn giới tính!'),
		],
		onSubmit: function(data) { }
	});

});