/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */

 
app.controller('quanlydanhmuc-ctrl', function($scope, $http, $rootScope, $timeout) {

	const categoryAPI = new GlobalAPI();
    categoryAPI.setNamespace(GlobalAPI.namespace.CATEGORY);
    
    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //3. form
    $scope.formCategory = {}

	//4. from edit
	$scope.formeditCategory ={};
	
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }

    //2. chọn nút thêm 
    $scope.add = function() {
        localStorage.removeItem('obj_edit_category');
        $scope.formCategory = {};
        $scope.formeditCategory ={};
    }

    /*END CODE TAB*/

    //2. Kiểm tra form cập nhật hay thêm
    $scope.getAForm = function() {
        if ($scope.formeditCategory.id != null) {
            return true;
        } else {
            return false;
        }
    }

    //3. eidit  
    $scope.edit = function(item) {
		item.addDate = new Date(item.addDate);
        $scope.formCategory = angular.copy(item);
        $scope.tab(2);
        $scope.formeditCategory = $scope.formCategory;
        localStorage.obj_edit_category = JSON.stringify($scope.formCategory);
    }

    //4. save 
    $scope.save = function() {
        let formDataInput = angular.copy($scope.formCategory);
        let categoryV = document.getElementById('category').value;
        let descriptionsV  = document.getElementById('descriptions').value;
        if ($scope.getAForm()) {
			//Cập Nhật
			if(categoryV != '' && descriptionsV != ''){
				categoryAPI.update(formDataInput.id, formDataInput).then(resp => {
					let formDataEdit = JSON.parse(localStorage.obj_edit_category);
		            localStorage.removeItem('obj_edit_category');
				    $scope.tab(1);
				    $scope.formCategory = {};
				    $scope.formeditCategory ={};
				    $scope.initialize();
				    $rootScope.Alert(true, "success", "Cập Nhật danh mục thành Công!", 3);
				}).catch(erorr => {
			        $scope.tab(1);
			        $scope.formCategory = {};
			        $scope.formeditCategory ={};
			        $rootScope.Alert(true, "danger", "Cập Nhật thất bại!", 3);
			        console.log("Erorr: ", erorr);
			    });
			}
        } else {
            //Thêm 
            if(categoryV != '' && descriptionsV != ''){
				let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
	            	formDataInput.status = {id: 3};
	            	formDataInput.blocked = false;
	            	formDataInput.addDate = new Date();
	            	formDataInput.addBy = {id: sessionUser};
	            	console.log(formDataInput);
	            	categoryAPI.create(formDataInput).then(resp => {
			            $scope.tab(1);
			            $scope.formCategory = {};
			            $scope.formeditCategory ={};
			            $scope.initialize();
			            $rootScope.Alert(true, "success", "Thêm danh mục thành Công!", 5);
					}).catch(erorr => {
						$rootScope.Alert(true, "danger", "Thêm danh mục thất bại!", 3);
			            console.log("Erorr: ", erorr);
			        });
			}
            
        }
    }
    
    

    //5. reset 
    $scope.reset = function() {
        if ($scope.getAForm()) {
            //reset update
            $scope.formCategory = JSON.parse(localStorage.obj_edit_category);
            $scope.formeditCategory =  $scope.formCategory;
        } else {
            //reset create
            $scope.formCategory = {};
        }
    }

    //6. delete 
    $scope.delete = function(item) {
			console.log(item);
		 let formDataInput = angular.copy(item);
		 formDataInput.status.id = 1;
		 categoryAPI.update(formDataInput.id, formDataInput).then(resp => {
			$("#deleteModal").modal("hide");
	        $("#deleteModalf").modal("hide");
	        $scope.tab(1);
	        localStorage.removeItem('obj_edit_category');
	        $scope.formCategory = {};
	        $scope.formeditCategory ={};
	        $scope.initialize();
	        $rootScope.Alert(true, "success", "Khóa Thành Công!", 3);
		}).catch(erorr => {
			$("#deleteModal").modal("hide");
	        $("#deleteModalf").modal("hide");
		    $rootScope.Alert(true, "danger", "Khóa thất bại!", 5);
		});
    }
    
    $scope.layid = {};
    $scope.setId = function(item){
		$scope.layid = angular.copy(item);
		console.log($scope.layid)
	}
    
    
    
    
    /**
     * 6/11/2022
     * haotn
     * Dữ liệu từ db
     */
    $scope.pageData = {};
    
    //5. Số vị trí trang đang sử dụng
    $scope.APage = 1;

    //6. Số trang hiện tại
    $scope.NPage = 1;

    //7. Số trang tối đa
    $scope.MaxPage = 1;

    //8. Số phần tử của 1 trang
    $scope.Nitem = 2;

    //9. Button Pagination
    $scope.listButton = [];
	
	//10. Active list 
	$scope.listActive = 1;
	
	//11. Số lượng phần tử
	$scope.Nproduct = 0;
	
	//12. search
	$scope.searchtext = "";
	 
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
    /*END CODE TAB*/
 
	/**BEGIN PAGINATION */ 
	
    //1. Hàm Danh sách list
    $scope.getButtonPagination = function() {   
		$scope.listButton = []; 
        for (let i = 1; i <= $scope.MaxPage; i++) {
            $scope.listButton.push(i); 
        }
        return $scope.listButton;
    }

    //2. Truy vấn page
    $scope.queryPage = async function(NPage, Nitem) {
        //lấy sp
        this.NPage = this.NPage;
         let data = {
			'key': $scope.searchtext,
			'status': $scope.listActive,
		}
		 let resp = await categoryAPI.getPageByProperties(NPage, Nitem, data)
            $scope.pageData = resp.content; 
            $scope.MaxPage = resp.totalPages;
            $scope.getButtonPagination();
            $scope.$digest();  
		        
    } 

	//3. Phân trang
    $scope.goPage = function(NumPage) { 
	
        //5.1 ràng buộc phạm vi
        if (NumPage > $scope.MaxPage) {
            this.NPage = $scope.MaxPage;
        } else if (NumPage < 1) {
            this.NPage = 1;
        } else {
            this.NPage = NumPage;
        } 
        //5.2 set page hiện tại
        $scope.APage = this.NPage;

        //5.3 Số trang hiện tại
        $scope.NPage = this.NPage;
 
        //5.4 lấy SP
        $scope.queryPage(this.NPage, this.Nitem);
    }
 
   	//4.  list Hiển Thị ALL
    $scope.listActiveAll = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtext = Tsearch;
        $scope.listActive = lActive;
        $scope.queryPage($scope.NPage, $scope.Nitem); 
    }
    
    //5. chuyen tab link
    $scope.changeTabPanelLink  = function(alink){ 
		//2.1. Số vị trí trang đang sử dụng
	    $scope.APage = 1;
	     
	    //2.2. Số trang hiện tại
	    $scope.NPage = 1;
	    //2.3. Key search
		let Tsearch = "";
		//2.4. GO PAGE
$scope.listActiveAll(alink,Tsearch); 
	}
    
    //6. go Search 
    $scope.goSearchA = function (keysearch){ 
		let Tsearch = keysearch.value;
		//console.log(Tsearch)
		if((Tsearch != null) | (Tsearch != '')){
			let lActive =  $scope.listActive 
			if(lActive > 0){ 
				 lActive = -($scope.listActive);  
			} 
			$scope.NPage = 1;  
	 	 	$scope.listActiveAll(lActive,Tsearch)
		} 
		$scope.APage = 1;
	}
     
	/**END PAGINATION */
     $scope.initialize = function() {   
		let lActive = 1;
		let Tsearch = "";
        $scope.listActiveAll(lActive,Tsearch); 
    }
    
    
    $scope.initialize();
   
    Validator({
        form: '#formcate',
        formGroupSelector: '.form-group',
        errorSelector: '.form-message',
        invalidSelector: '.invalid',
        rules: [
            Validator.isRequired("#category", 'Vui lòng nhập tên danh mục!'),
            Validator.isRequired("#descriptions", 'Vui lòng nhập ngày tạo!'),
        ],
        onSubmit: function(data) {

        }
    });

});