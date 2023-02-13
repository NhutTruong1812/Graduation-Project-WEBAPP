app.controller('quenmatkhau-ctrl', function($scope, $http, $timeout, $interval) {
    let userAPI = new GlobalAPI();
    userAPI.setNamespace(GlobalAPI.namespace.USER);

    const mailAPI = new GlobalAPI();
    mailAPI.setNamespace(GlobalAPI.namespace.MAIL);

    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
            $scope.Atab = tabnum;
        }
        /*END CODE TAB*/

    // xử lý form quên mật khẩu b1
    $scope.confirmuser = async function() {
        let message = document.getElementById('message__Login');
        var username = document.querySelector('#username');
        var email = document.querySelector('#email');
        let subjectmail = "Mã xác nhận";
        let bodymail = "Đây là mã xác nhận mail của bạn: ";
        let user = await userAPI.getByProperty("username", username.value);
        if (username.value != '' && email.value != '') {
            if (user) {
                if (username.value != user.username) {
                    message.innerHTML = "Tài khoản không chính xác";
                } else {
                    if (email.value != user.email) {
                        message.innerHTML = "Email không chính xác";
                    } else {
                        var newpasswork = '';
                        for (var i = 0; i < 6; i++) {
                            var temp = Math.round(Math.random() * 9);
                            newpasswork = newpasswork + String(temp);
                        }
                        $scope.sendmail(user.email, subjectmail, bodymail + newpasswork);
                        sessionStorage.setItem('userconfirm', JSON.stringify(user));
                        sessionStorage.setItem('verification', JSON.stringify(newpasswork));
                        alert("Vui lòng đợi trong giây lát");
                    }
                }
            } else {
                message.innerText = "Tài khoản không chính xác";
                console.log("Erorr: ", erorr);
            }
        } else {
            message.innerText = "Không được để trống thông tin";
            console.log("Erorr: ", erorr);
        }

    }

    $scope.cleartimeout;
    $scope.clearinterval;
    //Gửi mail
    $scope.sendmail = async function(to, subject, body) {
        mailAPI.getByProperties({ 'to': to, 'subject': subject, 'body': body }).then(resp => {
            alert("Mã đã được gửi đến mail vui lòng nhập mã")
            $scope.tab(2);
            //qua 60 giây sẽ xóa 
            $scope.cleartimeout = $timeout(function() {
                alert("Đã qua thời gian mã hoạt động mời bạn nhập lại");
                sessionStorage.removeItem('verification');
                $scope.tab(1);
            }, 60000);
            //đồng đồ đếm ngược 
            $scope.countDown = 60;
            $scope.clearinterval = $interval(function() {
                console.log($scope.countDown--)
            }, 1000);
        })
    }


    // Xử lý form quên mật khẩu b2
    $scope.confirmCode = function() {
        let sessionCode = JSON.parse(sessionStorage.getItem("verification"));
        let elmConfirm = document.getElementById('codeConfirm').value;
        let message = document.getElementById('message__Login');
        console.log(message)
        if (elmConfirm != '') {
            if (elmConfirm == sessionCode) {
                alert("Mã xác nhận chính xác")
                $interval.cancel($scope.clearinterval);
                $timeout.cancel($scope.cleartimeout);
                $scope.tab(3);
            } else {
                alert('Mã xác nhận không chính xác!')
                message.innerHTML = "Mã xác nhận không chính xác!";
            }
        } else {
            alert('Không được để trống thông tin!')
            message.innerHTML = "Không được để trống thông tin";
        }
    }

    // Xử lý form quên mật khẩu b3
    $scope.changePassword = function() {
        let sessionUser = JSON.parse(sessionStorage.getItem("userconfirm"));
        let password = document.getElementById('password').value;
        let confirmpassword = document.getElementById('confirmpassword').value;
        let message = document.getElementById('message__Login');
        if (password != '' && confirmpassword != '') {
            if (confirmpassword == password) {
                sessionUser.password = confirmpassword;
                console.log(sessionUser);
                userAPI.putRequest(sessionUser.id, 'changepass', sessionUser).then(resp => {
                    console.log(resp.data);
                    alert('Đổi mật khẩu thành công');
                    sessionStorage.clear();
                    window.setCookie('_biglobby_udata_', '', -30);
                    location.href = '#!/biglobby/dangnhap';
                }).catch(erorr => {
                    alert("Đổi mật khẩu thất bại");
                    console.log("Erorr: ", erorr);
                });
            }
        } else {
            alert("Không được để trống thông tin")
            message.innerText = "Không được để trống thông tin";
        }
    }

    Validator({
        form: '#forgotpassword_1',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#username", 'Vui lòng nhập username!'),
            Validator.isRequired("#email", 'Vui lòng nhập email!'),
            Validator.isEmail("#email", 'không đúng định dạng email!'),
        ],
        onSubmit: function(data) {
            $scope.myLogin(data);
        }
    });

    Validator({
        form: '#forgotpassword_2',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#codeConfirm", 'Vui lòng nhập mã xác nhận!'),
        ],
        onSubmit: function(data) {
            $scope.myLogin(data);
        }
    });

    Validator({
        form: '#forgotpassword_3',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#password", 'Vui lòng nhập password!'),
            Validator.minLength("#password", 6),
            Validator.isEquals("#confirmpassword", function() {
                return document.querySelector("#forgotpassword_3 #password").value;
            }, 'Mật khẩu nhập lại không chính xác!'),
        ],
        onSubmit: function(data) {
            $scope.myLogin(data);
        }
    });
});