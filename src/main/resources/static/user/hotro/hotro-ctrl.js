app.controller('hotro-ctrl', function($scope, $http) {

	const mailAPI = new GlobalAPI();
    mailAPI.setNamespace(GlobalAPI.namespace.MAIL);
    
    $scope.form = {};
    $scope.result;

    $scope.send = function() {
        let context = document.getElementById('context').value;
        let to = 'huynhnhatquang411281@gmail.com';
        let subject = 'Thắc măc từ người dùng: ';
        let img = $scope.form.banner;
        if (context != '') {
            $scope.sendmail(to, subject, context + img);
           alert("Cảm ơn bạn đã góp ý");
        } else {
            alert('không được bỏ trống thông tin');
        }

    }

    $scope.sendmail = function(to, subject, body) {
	mailAPI.getByProperties({'to': to, 'subject': subject, 'body': body}).then(resp => {
        }).catch(error => {
            alert("Lỗi gửi mail");
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
            $scope.form.banner = path.slice(-26);
            if ($scope.form.banner.length > 1) {
                    $scope.result = 'Đã chọn ảnh';
            } else {
                    $scope.result = $scope.form.banner[0];
            }
            $scope.$digest();
            console.log($scope.form.banner)
        }).catch(error => {
            alert("Lỗi poster")
            console.log("Error: ", error)
        })
    }
});