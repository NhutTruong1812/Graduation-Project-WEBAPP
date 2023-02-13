/**
 * CodeDate: V1-5/11/2022,
 * Front-end: V1(render, thông báo) 
 * 
 */
app.controller('lichsunaptien-ctrl', function($scope, $http, $rootScope) {
	
	const bcoinhistoriesAPI = new GlobalAPI();
    bcoinhistoriesAPI.setNamespace(GlobalAPI.namespace.BCOIN_HISTORY);
	 /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listBCoinHistory = [];
 
 	//3. form 
 	$scope.formBCoinHistory={};
 	
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
	        console.log($scope.formBCoinHistory)
	        //localStorage.obj_edit_displayfee = JSON.stringify($scope.formDisplayFee);
	    }
	    
	    
	//format currency
	$scope.currencyFormat = function(money){
		const config = { style: 'currency', currency: 'VND'}
	    const formated = new Intl.NumberFormat('vi-VN', config).format(money);
	    return formated;		
	}

    /*END CODE TAB*/
    
    ///////////////////////////
	 
	//5. Số vị trí trang đang sử dụng
    $scope.APage = 1;

    //6. Số trang hiện tại
    $scope.NPage = 1;

    //7. Số trang tối đa
    $scope.MaxPage = 1;

    //8. Số phần tử của 1 trang
    $scope.Nitem = 6;

    //9. Button Pagination
    $scope.listButton = [];
	
	//10. Active list 
	$scope.listActive = 1;
	
	//11. Số lượng phần tử
	$scope.Nproduct = 0;
	
	//12. search
	$scope.searchtext = "";
	 
    /*End variable */
 
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
		 let resp = await bcoinhistoriesAPI.getPageByProperties(NPage, Nitem, data)
            $scope.listBCoinHistory = resp.content; 
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
});
 