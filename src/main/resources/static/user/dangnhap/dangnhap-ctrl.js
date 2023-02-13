app.controller('dangnhap-ctrl', function($scope, $http) {

    let userAPI = new GlobalAPI();
    userAPI.setNamespace(GlobalAPI.namespace.USER);

    let registerActiveAPI = new GlobalAPI();
    registerActiveAPI.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);

    let shopAPI = new GlobalAPI();
    shopAPI.setNamespace(GlobalAPI.namespace.MYSHOP);

    $scope.items = [];
    $scope.form = {};
    $scope.sessionStorage = sessionStorage;

    // Đăng nhập
    $scope.myLogin = async function(data) {
        let loi = document.getElementById('message__Login');
        let username = data.username;
        let password = data.password;
        let response = await GlobalAPI.login({ username: username, password: password }).catch(err => {
            console.log(err);
            if (err.status === 401) {
                loi.innerText = 'Sai tài khoản hoặc mật khẩu!';
                return;
            }
        });

        if (!response) {
            return;
        }

        let user = response.user;
        let rooms = response.rooms;

        if (!user) {
            loi.innerText = 'Sai tài khoản hoặc mật khẩu!';
            return;
        }

        let active = await registerActiveAPI.getByProperty("user.id", user.id);
        if (!active) {
            loi.innerText = 'Tài khoản không hoạt động';
            return;
        }
        if (!active.actived) {
            loi.innerText = 'Tài khoản không hoạt động';
            return;
        }
        window.socket.login(user.username, rooms);
        let shop = await userAPI.getCollections(user.id, 'shop');
        sessionStorage.setItem('usersession', JSON.stringify(user.id));
        sessionStorage.setItem('shopsession', JSON.stringify(shop));
        var remember = document.querySelector('#remember');
        if (remember.checked) {
            let dataEncrypted = await GlobalAPI.encryptUserData({
                username: user.username,
                password: password
            });
            window.setCookie('_biglobby_udata_', dataEncrypted['_biglobby_udata_'], 30);
        }
        location.href = 'http://localhost:8080/biglobby/index';
    }

    Validator({
        form: '#loginform',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#username", 'Vui lòng nhập username!'),
            Validator.isRequired("#password", 'Vui lòng nhập password!'),
        ],
        onSubmit: function(data) {
            $scope.myLogin(data);
        }
    });

});