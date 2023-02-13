app = angular.module("user-app", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
		//#!biglobby/index
		//Trang chủ
		.when("/biglobby/index", {
			templateUrl: "/user/home/index.html",
			controller: "home-ctrl"
		})

		//Bài đăng
		.when("/biglobby/baidang", {
			templateUrl: "/user/baidang/index.html",
			controller: "baidang-ctrl"
		})

		//Bài đăng yêu thích
		.when("/biglobby/sanphamyeuthich", {
			templateUrl: "/user/sanphamyeuthich/index.html",
			controller: "sanphamyeuthich-ctrl"
		})

		//Chi tiết đơn hàng
		.when("/biglobby/chitietdonhang", {
			templateUrl: "/user/chitietdonhang/index.html",
			controller: "chitietdonhang-ctrl"
		})

		//Chi tiết đơn hàng bán
		.when("/biglobby/chitietdonhangban", {
			templateUrl: "/user/chitietdonhangban/index.html",
			controller: "chitietdonhangban-ctrl"
		})


		//Chi tiết sản phẩm
		.when("/biglobby/chitietsanpham", {
			templateUrl: "/user/chitietsanpham/index.html",
			controller: "chitietsanpham-ctrl"
		})

		//Cửa hàng
		.when("/biglobby/cuahang", {
			templateUrl: "/user/cuahang/index.html",
			controller: "cuahang-ctrl"
		})

		//Đăng ký
		.when("/biglobby/dangky", {
			templateUrl: "/user/dangky/index.html",
			controller: "dangky-ctrl"
		})

		//Đăng nhập
		.when("/biglobby/dangnhap", {
			templateUrl: "/user/dangnhap/index.html",
			controller: "dangnhap-ctrl",
		})

		//Đổi mật khẩu
		.when("/biglobby/doimatkhau", {
			templateUrl: "/user/doimatkhau/index.html",
			controller: "doimatkhau-ctrl"
		})

		//Giỏ hàng
		.when("/biglobby/giohang", {
			templateUrl: "/user/giohang/index.html",
			controller: "giohang-ctrl"
		})

		//Hỗ trợ
		.when("/biglobby/hotro", {
			templateUrl: "/user/hotro/index.html",
			controller: "hotro-ctrl"
		})

		//Lịch sử mua
		.when("/biglobby/lichsumua", {
			templateUrl: "/user/lichsumua/index.html",
			controller: "lichsumua-ctrl"
		})

		//Quản lý hóa đơn
		.when("/biglobby/quanlyhoadon", {
			templateUrl: "/user/quanlyhoadon/index.html",
			controller: "quanlyhoadon-ctrl"
		})

		//Quản lý sản phẩm
		.when("/biglobby/quanlysanpham", {
			templateUrl: "/user/quanlysanpham/index.html",
			controller: "quanlysanpham-ctrl"
		})

		//Quản lý thống kê
		.when("/biglobby/quanlythongke", {
			templateUrl: "/user/quanlythongke/index.html",
			controller: "quanlythongke-ctrl"
		})

		//Quên mật khẩu
		.when("/biglobby/quenmatkhau", {
			templateUrl: "/user/quenmatkhau/index.html",
			controller: "quenmatkhau-ctrl"
		})

		//Sản phẩm
		.when("/biglobby/sanpham", {
			templateUrl: "/user/sanpham/index.html",
			controller: "sanpham-ctrl"
		})

		//Sản phẩm yêu thích
		.when("/biglobby/sanphamyeuthich", {
			templateUrl: "/user/sanphamyeuthich/index.html",
			controller: "sanphamyeuthich-ctrl"
		})

		//Thông tin tài khoản
		.when("/biglobby/thongtintaikhoan", {
			templateUrl: "/user/thongtintaikhoan/index.html",
			controller: "thongtintaikhoan-ctrl"
		})

		//Xem bài đăng cửa hàng khác
		.when("/biglobby/xembaidangcuahangkhac", {
			templateUrl: "/user/xembaidangcuahangkhac/index.html",
			controller: "xembaidangcuahangkhac-ctrl"
		})

		//Xem sản phẩm của hàng khác
		.when("/biglobby/xemsanphamcuahangkhac", {
			templateUrl: "/user/xemsanphamcuahangkhac/index.html",
			controller: "xemsanphamcuahangkhac-ctrl"
		})

		//Đặt hàng
		.when("/biglobby/dathang", {
			templateUrl: "/user/dathang/index.html",
			controller: "dathang-ctrl"
		})
		//về chúng tôi
		.when("/biglobby/vechungtoi", {
			templateUrl: "/user/vechungtoi/index.html",
			controller: "vechungtoi-ctrl"
		})
		//nạp tiên
		.when("/biglobby/naptien", {
			templateUrl: "/user/naptien/index.html",
			controller: "naptien-ctrl"
		})
		//thông báo
		.when("/biglobby/thongbao", {
			templateUrl: "/user/thongbao/index.html",
			controller: "thongbao-ctrl"
		})
		//bài đăng bị ẩn
		.when("/biglobby/baidangbian", {
			templateUrl: "/user/baidangbian/index.html",
			controller: "baidangbian-ctrl"
		})

		//têu chuẩn công ddonf
		.when("/biglobby/tieuchuancongdong", {
			templateUrl: "/user/tieuchuancongdong/index.html",
			controller: "tieuchuancongdong-ctrl"
		})
		//têu chuẩn công ddonf
		.when("/biglobby/nhantin", {
			templateUrl: "/user/nhantin/index.html",
			controller: "nhantin-ctrl"
		})
		//quảng cáo
		.when("/biglobby/quanlyquangcao", {
			templateUrl: "/user/quangcao/index.html",
			controller: "quangcao-ctrl"
		})
		.otherwise({
			templateUrl: "/user/home/index.html",
			controller: "home-ctrl"
		});
});


// Đăng xuất
function logout() {
	sessionStorage.clear();
	window.setCookie('_biglobby_sesstoken_', '', -30);
	window.setCookie('_biglobby_udata_', '', -30);
	location.href = 'http://localhost:8080/user/index.html#!/biglobby/dangnhap';
}



app.run(function($rootScope, $timeout) {
	//1. Thông báo
	$rootScope.ResponseModel = {};
	$rootScope.ResponseModel.ResponseAlert = false;
	$rootScope.ResponseModel.ResponseType = "success";
	$rootScope.ResponseModel.ResponseMessage = "Thông báo!!"
	//1. set message (ẩn/hiện, loại thông báo, nội dung thông báo, thời gian hiện thông báo tính bằng giây)
	$rootScope.Alert = function(show, style, message, time) {
		$rootScope.ResponseModel = {};
		$rootScope.ResponseModel.ResponseAlert = show;
		$rootScope.ResponseModel.ResponseType = style;
		$rootScope.ResponseModel.ResponseMessage = message;
		$timeout(function() {
			$rootScope.ResponseModel.ResponseAlert = false;
		}, (time * 1000));
	}

	$rootScope.host = "https://biglobby.herokuapp.com/";

	$rootScope.userAPI = new GlobalAPI();
	$rootScope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$rootScope.mybcoinAPI = new GlobalAPI();
	$rootScope.mybcoinAPI.setNamespace(GlobalAPI.namespace.MYBCOIN);

	$rootScope.authorityAPI = new GlobalAPI();
	$rootScope.authorityAPI.setNamespace(GlobalAPI.namespace.AUTHORITY);

	$rootScope.userLogin = {};
	$rootScope.myBcoin = {};
	$rootScope.myAuthority = {};
	$rootScope.getUserLogin = async function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let resp = await $rootScope.userAPI.getById(sessionUser);
		$rootScope.userLogin = resp;

		//get bcoin of user login
		let respB = await $rootScope.mybcoinAPI.getByProperties({ 'idUser': sessionUser });
		$rootScope.myBcoin = respB;

		//get authority of user login
		let respA = await $rootScope.authorityAPI.getByProperties({ 'idUser': sessionUser });
		$rootScope.myAuthority = respA;
		

		$rootScope.$digest();

	}

	//hàm xử lý định dạng tiền Bcoin
	$rootScope.currencyBcoinFormat = function(bcoin) {
		const config = { maximumSignificantDigits: 3 }
		const formated = new Intl.NumberFormat('vi-VN', config).format(bcoin);
		return formated;
	}


	$rootScope.getUserLogin();
});