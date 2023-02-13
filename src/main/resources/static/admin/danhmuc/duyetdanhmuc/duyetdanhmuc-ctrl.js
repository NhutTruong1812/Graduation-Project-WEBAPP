/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */

 
app.controller('duyetdanhmuc-ctrl', function($scope, $http, $rootScope, $timeout) {

	const categoryAPI = new GlobalAPI();
    categoryAPI.setNamespace(GlobalAPI.namespace.CATEGORY);
    
    const newsAPI = new GlobalAPI();
    newsAPI.setNamespace(GlobalAPI.namespace.NEWS);
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

    
    //2. eidit  
    $scope.edit = function(item) {
        $scope.tab(2);
        $scope.formCategory = angular.copy(item);
        $scope.formeditCategory = $scope.formCategory;
        localStorage.obj_edit_category = JSON.stringify($scope.formCategory);
    }

    //4. save 
    $scope.duyet = function() {
		let formDataInput = angular.copy($scope.formCategory);
		console.log(formDataInput);
		//Cập Nhật
			formDataInput.status = {id : 3};
			formDataInput.addDate = new Date(formDataInput.addDate);
			categoryAPI.update(formDataInput.id, formDataInput).then(resp => {
				console.log("")
				let formDataEdit = JSON.parse(localStorage.obj_edit_category);
				$scope.tab(1);
				localStorage.removeItem('obj_edit_category');
				$scope.formCategory = {};
				$scope.formeditCategory = {};
				$rootScope.Alert(true, "success", "Đã Duyệt!", 3); 
				 $scope.initialize();
			}).catch(erorr => {
			    alert("Duyệt danh mục thất bại");
			    console.log("Erorr: ", erorr);
			});
		
		
    }

    
    //6. delete 
    $scope.tuchoi = function(item) {
		let formDataInput = angular.copy(item);
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let lydo = document.getElementById('lydo').value;
		let message = document.querySelector('#form-message');
		console.log(formDataInput)
		if(lydo != '' && lydo != undefined){
				let news = {};
				news.gotUser = formDataInput.addBy;
				news.sentUser = {id: sessionUser};
				news.content = lydo;
				news.newsDate = new Date();
				news.hidden = false;
				console.log(news);
				newsAPI.create(news).then(resp => {
				categoryAPI.delete(formDataInput.id).then(resp => {
						$("#block-modal").modal("hide");
				        $scope.tab(1);
				        localStorage.removeItem('obj_edit_category');
				        $scope.formCategory = {};
				        $scope.formeditCategory ={};
				        $rootScope.Alert(true, "danger", "Đã từ chối danh mục này", 3);
				        $scope.initialize();
					}).catch(erorr => {
						$("#deleteModal").modal("hide");
				        $("#deleteModalf").modal("hide");
					    $rootScope.Alert(true, "danger", "Từ chối thất bại danh mục đã được sử dụng!", 5);
					});
				})
		}else{
			message.innerHTML = "không được bỏ trống thông tin";
		}
		
        
    }
    
    $scope.layid = {};
    $scope.setId = function(item){
		$scope.layid = angular.copy(item);
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
	$scope.listActive = 2;
	
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
		let lActive = 2;
		let Tsearch = "";
        $scope.listActiveAll(lActive,Tsearch); 
    }
    $scope.initialize();
    
});