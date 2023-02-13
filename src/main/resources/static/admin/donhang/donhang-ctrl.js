/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */
/**
 * Codedate: 17/11/20222 
 * back-end: load dữ liệu lên form, phân trang, tabpanel, showdetail
 */
 
app.controller('donhang-ctrl', function($scope, $http, $rootScope) {
	window.socket.setOnOrder({
		onCreate: function(data) {

		},
		onUpdate: function(data) {

		},
		onDelete: function(data) {

		}
	})
	 	/**KHAI BÁO API */
	$scope.productAPI = new GlobalAPI();
	$scope.productAPI.setNamespace(GlobalAPI.namespace.PRODUCT);

	$scope.cardAPI = new GlobalAPI();
	$scope.cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	$scope.categorieAPI = new GlobalAPI();
	$scope.categorieAPI.setNamespace(GlobalAPI.namespace.CATEGORY);

	$scope.failhistorieAPI = new GlobalAPI();
	$scope.failhistorieAPI.setNamespace(GlobalAPI.namespace.FAIL_HISTORY);

	$scope.producthistorieAPI = new GlobalAPI();
	$scope.producthistorieAPI.setNamespace(GlobalAPI.namespace.PRODUCT_HISTORY);

	$scope.newAPI = new GlobalAPI();
	$scope.newAPI.setNamespace(GlobalAPI.namespace.NEWS);

	$scope.shopAPI = new GlobalAPI();
	$scope.shopAPI.setNamespace(GlobalAPI.namespace.MYSHOP);

	$scope.loveCardAPI = new GlobalAPI();
	$scope.loveCardAPI.setNamespace(GlobalAPI.namespace.LOVECARD);

	$scope.problemAPI = new GlobalAPI();
	$scope.problemAPI.setNamespace(GlobalAPI.namespace.PROBLEM);

	$scope.hidecardAPI = new GlobalAPI();
	$scope.hidecardAPI.setNamespace(GlobalAPI.namespace.HIDECARD);

	$scope.reportcardAPI = new GlobalAPI();
	$scope.reportcardAPI.setNamespace(GlobalAPI.namespace.REPORT);

	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.registerAPI = new GlobalAPI();
	$scope.registerAPI.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);

	$scope.followshopAPI = new GlobalAPI();
	$scope.followshopAPI.setNamespace(GlobalAPI.namespace.FOLLOW_SHOP);

	$scope.orderAPI = new GlobalAPI();
	$scope.orderAPI.setNamespace(GlobalAPI.namespace.ORDER);
	
	$scope.orderdetailAPI = new GlobalAPI();
	$scope.orderdetailAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);
	
	
	$scope.cartAPI= new GlobalAPI();
	$scope.cartAPI.setNamespace(GlobalAPI.namespace.CART);

 
    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listOrder = [];

    //3. form
    $scope.formOrder = {}

	//4. from edit
	$scope.formeditOrder ={}; 
	
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
	 
	//13. object delete
	$scope.obj_order_delete ={};
	
	//14. ácd
	$scope.listProduct = [];	

    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
    /*END CODE TAB*/
 
	/**BEGIN PAGINATION */  
	/**  Quy định số trạng thái
		//@access status
	    * 5: Đang xác nhận
	    * 6: Đồng ý giao dịch
	    * 7: Thành công
	    * 8: Hủy do người mua
	    * 9: Hủy do người bán
	    //SET 
	    -X: tìm kiếm
	    1: Tất cả
	    2: CHỜ XÁC NHẬN trạng thái status 5
	    3: ĐỒNG Ý GIAO DỊCH giao dịch trạng thái 6
	    4: THÀNH CÔNG trạng thái 7
	    5: HỦY trạng thái 8 và 9
	    6: HỦY do người mua trạng thái 8
	    7: HỦY do người bán trạng thái 9
    */
	
    //1. button list
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
        
        await $scope.orderAPI.getPageByProperties( NPage , Nitem ,{pagewhere: $scope.listActive, key: $scope.searchtext} ).then(resp => {
            $scope.listOrder = resp.content; 
           // console.log($scope.listOrder)
            $scope.MaxPage = resp.totalPages;
            $scope.getButtonPagination(); 
            $scope.$digest();
        }); 
         
    } 

	//2. Phân trang
    $scope.goPage = function(NumPage) { 
	
        //3.1 ràng buộc phạm vi
        if (NumPage > $scope.MaxPage) {
            this.NPage = $scope.MaxPage;
        } else if (NumPage < 1) {
            this.NPage = 1;
        } else {
            this.NPage = NumPage;
        } 
        //3.2 set page hiện tại
        $scope.APage = this.NPage;

        //5.3 Số trang hiện tại
        $scope.NPage = this.NPage;
 
        //5.4 lấy SP
        $scope.queryPage(this.NPage, this.Nitem);
    } 
   	//3.  list Hiển Thị ALL
    $scope.listActiveAll = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtext = Tsearch;
        $scope.listActive = lActive;
        $scope.queryPage($scope.NPage, $scope.Nitem); 
    }
    
    //4. chuyen tab link
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
    
    //5. go Search 
    $scope.goSearchA = function (keysearch){ 
		let Tsearch = keysearch.value;
		if(Tsearch != null){
			let lActive = $scope.listActive = -($scope.listActive); 
			$scope.NPage = 1;  
	 	 	$scope.listActiveAll(lActive,Tsearch)
			
		}else{ 
			if($scope.listActive < 0){
				$scope.listActive = $scope.listActive *-1;
			}
			let lActive = $scope.listActive ;
			let Tsearch = "";
	        $scope.listActiveAll(lActive,Tsearch); 
		} 
	} 
	/**END PAGINATION */


    /*START CODE Funtion , desscription: hàm cơ bản*/ 
    //1. eidit  
    $scope.edit = async function(item) {
        $scope.tab(2);
        $scope.formOrder = angular.copy(item);
        $scope.formeditOrder = $scope.formOrder;
        localStorage.obj_edit_post = JSON.stringify($scope.formOrder);
        
        $scope.listProduct = [];
        console.log('order detail')
        $scope.listProduct = await $scope.orderdetailAPI.getByProperties({'order.id': item.id}) 
		.catch(Error=>{
			  $scope.listProduct = [];
		})
        $scope.$digest();	
    	}
  	
  	//2. Go delete from table
  	$scope.goDeleteFromTable = function(item){
		$scope.obj_order_delete = item;
	}
  	
     
    /*END CODE */ 
    
    //<----------------------------------- INITIALIZE ----------------------------------->
    $scope.initialize = function() {   
		let lActive = 1;
		let Tsearch = "";
        $scope.listActiveAll(lActive,Tsearch); 
    }
    
    
    $scope.initialize();
});