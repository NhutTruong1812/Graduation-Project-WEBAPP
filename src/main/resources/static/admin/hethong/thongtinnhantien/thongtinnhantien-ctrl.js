/**
 * CodeDate: V1-5/11/2022,
 * Front-end: V1(render, thông báo) 
 * 
 */
app.controller('thongtinnhantien-ctrl', function($scope, $http, $rootScope) {
	 /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listBCoinHistory = [];
 
 	//3. form 
 	$scope.formBCoinHistory={};
 	
 	//4. form active
 	$scope.formDataEdit ={};
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }

	 //2. eidit displayfee 
	    $scope.showDetail = function(item) {
	        $scope.tab(2);
	        $scope.formBCoinHistory = angular.copy(item);
	        $scope.formDataEdit = item;
	        localStorage.obj_edit_BCoin = JSON.stringify($scope.formBCoinHistory);
	    }


	 //2. chọn nút thêm 
	    $scope.add = function() {
	        localStorage.removeItem('obj_edit_BCoin');
	        $scope.formBCoinHistory = {};
	    }
    /*END CODE TAB*/
	
	//1. getData
	$scope.getData = function(){
		  /*for(let i = 0 ; i < 20; i++){
			let a = "{id:"+i +", stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},"
			console.log(a); 
		 } */ 
		  
		 $scope.listBCoinHistory=[ 
			{id:0, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:1, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:2, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:3, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:4, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:5, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:6, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:7, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:8, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:9, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:10, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:11, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:12, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:13, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:14, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:15, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:16, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:17, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:18, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
			{id:19, stk: '255525212155', user: 'user 1', nganhang: 'sacombank'},
		]
		  
	}
	  
	    //2. Kiểm tra form cập nhật hay thêm
    $scope.getAForm = function() {
        if ($scope.formBCoinHistory.id != null) {
            return true;
        } else {
            return false;
        }
    }

    //3. eidit displayfee 
    $scope.edit = function(itemDisplayFee) {
        $scope.tab(2);
        $scope.formBCoinHistory = angular.copy(itemDisplayFee);
        localStorage.obj_edit_BCoin = JSON.stringify($scope.formBCoinHistory);
    }

    //4. save displayfee
    $scope.save = function() {
        let formDataInput = angular.copy($scope.formBCoinHistory);
        if ($scope.getAForm()) {
            //Cập Nhật
            $scope.formDataEdit = JSON.parse(localStorage.obj_edit_BCoin);
            $scope.tab(1);
            localStorage.removeItem('obj_edit_BCoin');
            $scope.formBCoinHistory = {};
            $rootScope.Alert(true, "success", "Cập Nhật Thành Công!", 3);
        } else {
            //Thêm 
            $rootScope.Alert(true, "success", "Thêm Thành Công!", 3);
          	$scope.tab(1);
            $scope.formBCoinHistory = {};
        }
    }

    //5. reset displayfee
    $scope.reset = function() {
        if ($scope.getAForm()) {
            //reset update
            $scope.formBCoinHistory = JSON.parse(localStorage.obj_edit_BCoin);
        } else {
            //reset create
            $scope.formBCoinHistory = {};
        }
    }

    //6. delete displayfee
    $scope.delete = function() {
        $("#deleteModal").modal("hide");
        $scope.tab(1);
        localStorage.removeItem('obj_edit_BCoin');
        $scope.formBCoinHistory = {};
        $rootScope.Alert(true, "danger", "Xóa Thành Công!", 3);
    }
	   
	//
	$scope.initialize = function(){
		$scope.getData();
	}
	
	$scope.initialize();
});
 