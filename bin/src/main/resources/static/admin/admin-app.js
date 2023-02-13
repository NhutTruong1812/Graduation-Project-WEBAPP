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

		.when("/biglobby/admin/baiviet/baivietvipham", {
			templateUrl: "/admin/baiviet/baivietvipham/index.html",
			controller: "baivietvipham-ctrl"
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
 