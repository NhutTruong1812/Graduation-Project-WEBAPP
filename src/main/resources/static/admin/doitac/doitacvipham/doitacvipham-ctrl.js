/**
 * CodeDate: V1-09/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */

 
app.controller('doitacvipham-ctrl', function($scope, $http, $rootScope) {

    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listBanner = [];

    //3. form
    $scope.formBanner = {}

	//4. from edit
	$scope.formeditBanner ={};
	
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
 
    /*END CODE TAB*/

    /*START CODE Funtion , desscription: hàm cơ bản*/
    //1. getData
    $scope.getData = function() {
        /* code tạo dữ liệu mẫu*/
        	/*for(let i = 1; i <= 20; i++){
        		let code = "{ "+"id: "+i+", username: 'user "+i+"', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai "+i+"' "+"},";
        		console.log(code) 
        	}  */
        	
        	$scope.listBanner=[
			 { id: 1, username: 'user 1', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 1' },
			 { id: 2, username: 'user 2', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 2' },
			 { id: 3, username: 'user 3', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 3' },
			 { id: 4, username: 'user 4', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 4' },
			 { id: 5, username: 'user 5', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 5' },
			 { id: 6, username: 'user 6', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 6' },
			 { id: 7, username: 'user 7', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 7' },
			 { id: 8, username: 'user 8', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 8' },
			 { id: 9, username: 'user 9', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 9' },
			 { id: 10, username: 'user 10', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 10' },
			 { id: 11, username: 'user 11', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 11' },
			 { id: 12, username: 'user 12', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 12' },
			 { id: 13, username: 'user 13', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 13' },
			 { id: 14, username: 'user 14', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 14' },
			 { id: 15, username: 'user 15', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 15' },
			 { id: 16, username: 'user 16', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 16' },
			 { id: 17, username: 'user 17', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 17' },
			 { id: 18, username: 'user 18', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 18' },
			 { id: 19, username: 'user 19', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 19' },
			 { id: 20, username: 'user 20', fullname: 'fullname', email: 'abc@gmail.com', addDate:'2022-10-22', status : 'trang thai 20' },
			]
         
    };
 

    //3. eidit  
    $scope.edit = function(item) {
        $scope.tab(2);
        $scope.formBanner = angular.copy(item);
        $scope.formeditBanner = $scope.formBanner;
        localStorage.obj_edit_banner = JSON.stringify($scope.formBanner);
    }
  
    //6. delete 
    $scope.khoiphuc = function() {
        $("#deleteModal").modal("hide");
        $("#deleteModalf").modal("hide");
        $scope.tab(1);
        localStorage.removeItem('obj_edit_banner');
        $scope.formBanner = {};
        $scope.formeditBanner ={};
        $rootScope.Alert(true, "danger", "Khôi Phục Thành Công!", 3);
    }

  

    /*END CODE */
    $scope.initialize = function() {
         $scope.getData();
    }
    $scope.initialize();
});