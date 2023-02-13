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
			controller: "dangnhap-ctrl"
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
		
		.otherwise({
			templateUrl: "/user/home/index.html",
			controller: "home-ctrl"
		});
});
 