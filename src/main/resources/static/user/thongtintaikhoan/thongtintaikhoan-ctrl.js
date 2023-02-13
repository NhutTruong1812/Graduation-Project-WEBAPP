app.controller('thongtintaikhoan-ctrl', function($scope, $http) {
    $scope.form = {};
    $scope.items = [];
    $scope.read = [];

    window.socket.setOnUser({
        onCreate: function(data) {},
        onUpdate: function(data) {},
        onDelete: function(data) {}
    })

    window.socket.setOnShop({
        onCreate: function(data) {},
        onUpdate: function(data) {},
        onDelete: function(data) {}
    })

    let userAPI = new GlobalAPI();
    userAPI.setNamespace(GlobalAPI.namespace.USER);

    let shopAPI = new GlobalAPI();
    shopAPI.setNamespace(GlobalAPI.namespace.MYSHOP);

    $scope.loadForm = function() {
        let sessionshop = JSON.parse(sessionStorage.getItem("shopsession"));
        $scope.form = sessionshop;
    }

    $scope.initialize = function() {
        $scope.loadForm();
    }

    //Image banner
    $scope.imageChanged = async function(files) {
        var data = new FormData();
        data.append('file', files[0]);
        await GlobalAPI.uploadFile('folder', data).then(resp => {
            console.log(resp.data);
            let path = resp.data[0];
            $scope.form.user.avatar = path.slice(-26);
            $scope.$digest();
        }).catch(error => {
            alert("Lỗi poster")
            console.log("Error: ", error)
        })
    }

    $scope.update = async function() {
        var item = angular.copy($scope.form);
        await userAPI.update(item.user.id, item.user).then(res => {
            userAPI.getCollections(res.id, 'shop').then(resp => {
                let shop = resp;
                console.log(shop)
                if (item.intro == null && item.location == null) {
                    item.intro = '';
                    item.location = '';
                } else {
                    shop.intro = item.intro;
                    shop.location = item.location;
                    shopAPI.update(shop.id, shop).then(resp => {
                        sessionStorage.setItem('shopsession', JSON.stringify(resp));
                        $scope.initialize();
                        $scope.reset
                        alert("Cập nhật tài khoản thành công");
                    })
                }
            })
        }).catch(erorr => {
            alert("Cập nhật tài khoản thất bại");
            console.log("Erorr: ", erorr);
        });
    }

    $scope.reset = function() {
        document.querySelector('#password').value = '';
        document.querySelector('#newpassword').value = '';
        document.querySelector('#confirmpassword').value = '';
    }

    $scope.changePass = async function() {
        let loi = document.getElementById('message__Login');
        var pass = document.querySelector('#password').value;
        var newpass = document.querySelector('#newpassword').value;
        var confirmpass = document.querySelector('#confirmpassword').value;
        let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
        await userAPI.getById(sessionUser).then(async(resp) => {
            let user = resp;
            if ((pass != '') && (newpass != '') && (confirmpass != '')) {
                if (newpass == confirmpass) {
                    await GlobalAPI.login({ username: user.username, password: pass }).then(resp => {
                        console.log(resp.user);
                        resp.user.password = newpass;
                        userAPI.putRequest(resp.user.id, "changepass", resp.user).then(resp => {
                            sessionStorage.removeItem('usersession');
                            sessionStorage.removeItem('shopsession');
                            window.setCookie('_biglobby_udata_', '', -30);
                            loi.innerText = "Đổi mật khẩu thành công";
                            location.href = 'http://localhost:8080/user/index.html#!/biglobby/dangnhap';
                        }).catch(erorr => {
                            alert("Đổi mật khẩu thất bại");
                            console.log("Erorr: ", erorr);
                        });
                    }).catch(erorr => {
                        loi.innerText = 'Mật khẩu hiện tại không chính xác!';
                        console.log("Erorr: ", erorr);
                    });
                }

            }
        });
    }

    Validator({
        form: '#formThongTin',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#username", 'Vui lòng nhập username!'),
            Validator.isRequired("#fullname", 'Vui lòng nhập fullname!'),
            Validator.isRequired("#email", 'Vui lòng nhập email!'),
            Validator.isEmail("#email", 'không đúng định dạng email'),
            Validator.isRequired("#phonenum", 'Vui lòng nhập phonenum!'),
            Validator.isPhoneNumber("#phonenum"),
            Validator.isRequired("input[name='gender']", 'Vui lòng chọn giới tính!'),
        ],
        onSubmit: function(data) {
            console.log(data);
        }
    });

    Validator({
        form: '#changeForm',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#password", 'Vui lòng nhập nhật khẩu hiện tại!'),
            Validator.isRequired("#newpassword", 'Vui lòng nhập mật khẩu mới!'),
            Validator.minLength("#newpassword", 6),
            Validator.isEquals("#confirmpassword", function() {
                return document.querySelector("#changeForm #newpassword").value;
            }, 'Mật khẩu nhập lại không chính xác!'),
        ],
        onSubmit: function(data) {
            // console.log(data);
        }
    });

    $scope.initialize();
});