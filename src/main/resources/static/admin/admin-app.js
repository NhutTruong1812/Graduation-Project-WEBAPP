app = angular.module("admin-app", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
		//#!biglobby/index
		//He thong
		.when("/biglobby/admin/hethong/giaodienhethong", {
			templateUrl: "/admin/hethong/giaodienhethong/index.html",
			controller: "giaodienhethong-ctrl"
		})

		.when("/biglobby/admin/hethong/lichsunaptien", {
			templateUrl: "/admin/hethong/lichsunaptien/index.html",
			controller: "lichsunaptien-ctrl"
		})

		.when("/biglobby/admin/hethong/phitreosanpham", {
			templateUrl: "/admin/hethong/phitreosanpham/index.html",
			controller: "phitreosanpham-ctrl"
		})

		.when("/biglobby/admin/hethong/thongtinnhantien", {
			templateUrl: "/admin/hethong/thongtinnhantien/index.html",
			controller: "thongtinnhantien-ctrl"
		})

		.when("/biglobby/admin/baiviet/baivietbibaocao", {
			templateUrl: "/admin/baiviet/baivietbibaocao/index.html",
			controller: "baivietbibaocao-ctrl"
		})


		.when("/biglobby/admin/baiviet/quanlybaiviet", {
			templateUrl: "/admin/baiviet/quanlybaiviet/index.html",
			controller: "quanlybaiviet-ctrl"
		})

		.when("/biglobby/admin/danhmuc/duyetdanhmuc", {
			templateUrl: "/admin/danhmuc/duyetdanhmuc/index.html",
			controller: "duyetdanhmuc-ctrl"
		})

		.when("/biglobby/admin/danhmuc/quanlydanhmuc", {
			templateUrl: "/admin/danhmuc/quanlydanhmuc/index.html",
			controller: "quanlydanhmuc-ctrl"
		})

		.when("/biglobby/admin/dichvu/lichsumuadichvu", {
			templateUrl: "/admin/dichvu/lichsumuadichvu/index.html",
			controller: "lichsumuadichvu-ctrl"
		})

		.when("/biglobby/admin/dichvu/quanlydichvu", {
			templateUrl: "/admin/dichvu/quanlydichvu/index.html",
			controller: "quanlydichvu-ctrl"
		})

		.when("/biglobby/admin/doitac/doitacvipham", {
			templateUrl: "/admin/doitac/doitacvipham/index.html",
			controller: "doitacvipham-ctrl"
		})

		.when("/biglobby/admin/doitac/quanlydoitac", {
			templateUrl: "/admin/doitac/quanlydoitac/index.html",
			controller: "quanlydoitac-ctrl"
		})

		.when("/biglobby/admin/donhang", {
			templateUrl: "/admin/donhang/index.html",
			controller: "donhang-ctrl"
		})

		.when("/biglobby/admin/sanpham/duyetsanpham", {
			templateUrl: "/admin/sanpham/duyetsanpham/index.html",
			controller: "duyetsanpham-ctrl"
		})

		.when("/biglobby/admin/sanpham/quanlysanpham", {
			templateUrl: "/admin/sanpham/quanlysanpham/index.html",
			controller: "quanlysanpham-ctrl"
		})

		.when("/biglobby/admin/sanpham/sanphamvipham", {
			templateUrl: "/admin/sanpham/sanphamvipham/index.html",
			controller: "sanphamvipham-ctrl"
		})

		.when("/biglobby/admin/sanpham/sanphambibaocao", {
			templateUrl: "/admin/sanpham/sanphambibaocao/index.html",
			controller: "sanphambibaocao-ctrl"
		})

		.when("/biglobby/admin/thongbao/quanlythongbao", {
			templateUrl: "/admin/thongbao/quanlythongbao/index.html",
			controller: "quanlythongbao-ctrl"
		})


		.when("/biglobby/admin/thongbao/thongbao", {
			templateUrl: "/admin/thongbao/thongbao/index.html",
			controller: "thongbao-ctrl"
		})


		.otherwise({
			templateUrl: "/admin/hethong/giaodienhethong/index.html",
			controller: "giaodienhethong-ctrl"
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

	//2. localhost
	$rootScope.localhost = "https://biglobby.herokuapp.com/";

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
	$rootScope.getUserLogin();

});