/**
 * CodeDate: V1-3/11/2022,
 * Front-end: V1(render, thông báo) 
 * 
 */
app.controller('giaodienhethong-ctrl', function($scope, $http, $rootScope) {
	//danh sách hiển thị
	$scope.listBservice = [];
	
	//1. getData
	$scope.getData = function(){
		/*for(let i = 1; i <= 20; i++){
			let a = "{id:"+i+",bService:'Dịch vụ "+i+"', banner: 'banner "+i+"', description: 'Mô tả giao diện "+i+"'},";
			console.log(a);
		} */
		
		$scope.listBservice = [
			{id:1,bService:'Dịch vụ 1', banner: 'banner 1', description: 'Mô tả giao diện 1'},
			{id:2,bService:'Dịch vụ 2', banner: 'banner 2', description: 'Mô tả giao diện 2'},
			{id:3,bService:'Dịch vụ 3', banner: 'banner 3', description: 'Mô tả giao diện 3'},
			{id:4,bService:'Dịch vụ 4', banner: 'banner 4', description: 'Mô tả giao diện 4'},
			{id:5,bService:'Dịch vụ 5', banner: 'banner 5', description: 'Mô tả giao diện 5'},
			{id:6,bService:'Dịch vụ 6', banner: 'banner 6', description: 'Mô tả giao diện 6'},
			{id:7,bService:'Dịch vụ 7', banner: 'banner 7', description: 'Mô tả giao diện 7'},
			{id:8,bService:'Dịch vụ 8', banner: 'banner 8', description: 'Mô tả giao diện 8'},
			{id:9,bService:'Dịch vụ 9', banner: 'banner 9', description: 'Mô tả giao diện 9'},
			{id:10,bService:'Dịch vụ 10', banner: 'banner 10', description: 'Mô tả giao diện 10'},
			{id:11,bService:'Dịch vụ 11', banner: 'banner 11', description: 'Mô tả giao diện 11'},
			{id:12,bService:'Dịch vụ 12', banner: 'banner 12', description: 'Mô tả giao diện 12'},
			{id:13,bService:'Dịch vụ 13', banner: 'banner 13', description: 'Mô tả giao diện 13'},
			{id:14,bService:'Dịch vụ 14', banner: 'banner 14', description: 'Mô tả giao diện 14'},
			{id:15,bService:'Dịch vụ 15', banner: 'banner 15', description: 'Mô tả giao diện 15'},
			{id:16,bService:'Dịch vụ 16', banner: 'banner 16', description: 'Mô tả giao diện 16'},
			{id:17,bService:'Dịch vụ 17', banner: 'banner 17', description: 'Mô tả giao diện 17'},
			{id:18,bService:'Dịch vụ 18', banner: 'banner 18', description: 'Mô tả giao diện 18'},
			{id:19,bService:'Dịch vụ 19', banner: 'banner 19', description: 'Mô tả giao diện 19'},
			{id:20,bService:'Dịch vụ 20', banner: 'banner 20', description: 'Mô tả giao diện 20'} 
		]
	}
	
	//1. useService
	$scope.useService = function(){
		/**
		 *Code .....
		 */
		 $rootScope.Alert(true,"success","Đổi giao diện thành công!", 3);
	}
	
	//
	$scope.initialize = function(){
		$scope.getData();
	}
	
	$scope.initialize();
});
 