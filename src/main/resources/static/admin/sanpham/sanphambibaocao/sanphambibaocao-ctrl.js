/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */

/**
 * CodeDate: V1-19/10/2022,
 * Back-end: V1 (pagination, )
 * 
 */
 
app.controller('sanphambibaocao-ctrl', function($scope, $http, $rootScope) { 
	window.socket.setOnCard({
		onCreate: function(data) {

		},
		onUpdate: function(data) {

		},
		onDelete: function(data) {

		}
	})

	window.socket.setOnNews({
		onCreate: function(data) {

		},
		onUpdate: function(data) {

		},
		onDelete: function(data) {

		}
	})
	
	window.socket.setOnReport({
		onCreate: function(data) {
			if(data.card.isProduct == true){
				alert("Có báo cáo mới!")
			} 
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
	
	$scope.reportcardAPI = new GlobalAPI();
    $scope.reportcardAPI.setNamespace(GlobalAPI.namespace.REPORT);
	
    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listProduct = [];

    //3. form
    $scope.formProduct = {}

	//4. from edit
	$scope.formeditProduct ={};
	 
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
	$scope.listActive = 6;
	
	//11. Số lượng phần tử
	$scope.Nproduct = 0;
	
	//12. search
	$scope.searchtext = "";
	 
	//13. object delete
	$scope.obj_order_delete ={};
	
	//14. list category
	$scope.listCategory  = [];
	
	//15. nội dung từ chối
	$scope.formContent = {};
   
    //
    $scope.listProductAll = [];
    
    $scope.listRPAll = [];
    /*End variable */

     
    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
 
    /*END CODE TAB*/

	/**BEGIN PAGINATION */  
	/**  Quy định số trạng thái 
	    //SET 
	    -X: tìm kiếm
	    1: Tất cả
	    2: Hoạt động
	    3: Ngừng hoạt động
    */
	
    //1. button list
    $scope.getButtonPagination = function() {   
		$scope.listButton = []; 
        for (let i = 1; i <=  $scope.MaxPage; i++) {
            $scope.listButton.push(i); 
        }
        return $scope.listButton;
    }

    //2. Truy vấn page
    $scope.queryPage = async function(NPage, Nitem) {
        //lấy sp
		this.NPage = this.NPage; 
		//let List = await $scope.productAPI.getPageProductReportAdmin(NPage, Nitem, $scope.listActive, "");
		let List = await $scope.productAPI.getPageByProperties(NPage, Nitem, {'pagewhere': $scope.listActive, 'key':$scope.searchtext, 'report': 1}); 
		$scope.listProduct = List.content;
		$scope.MaxPage = List.totalPages;
		 await $scope.getRPAll(); 
		$scope.getButtonPagination();  
		$scope.$digest(); 
    } 

	//2. Phân trang
    $scope.goPage = async function(NumPage) { 
		//alert("PAGE " + NumPage)
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
        await $scope.queryPage(this.NPage, this.Nitem);
        $scope.$digest(); 
    } 
    
   	//3.  list Hiển Thị ALL
    $scope.listActiveAll = async function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtext = Tsearch;
        $scope.listActive = lActive;
        await $scope.queryPage($scope.NPage, $scope.Nitem);  
    }
    
    //4. chuyen tab link
    $scope.changeTabPanelLink  = async function(alink){ 
		//2.1. Số vị trí trang đang sử dụng
	    $scope.APage = 1;
	     
	    //2.2. Số trang hiện tại
	    $scope.NPage = 1;
	    //2.3. Key search
		let Tsearch = "";
		//2.4. GO PAGE
        await $scope.listActiveAll(alink,Tsearch); 
	}
    
    //5. go Search 
    $scope.goSearchA =  async function (keysearch){ 
		let Tsearch = keysearch.value;
		//console.log(Tsearch)
		if((Tsearch != null) | (Tsearch != '')){
			let lActive =  $scope.listActive 
			if(lActive > 0){ 
				 lActive = -($scope.listActive);  
			} 
			$scope.NPage = 1;  
	 	 	await $scope.listActiveAll(lActive,Tsearch) 
		} 
		$scope.APage = 1;
	} 
	/**END PAGINATION */

    /*START CODE Funtion , desscription: hàm cơ bản*/ 
    //1. eidit  
    $scope.edit = async function(item) {
        $scope.tab(2);
        $scope.formProduct = angular.copy($scope.getProduct(item.card));
        $scope.formeditProduct = item;  
    }
  
  	//2. save 
    $scope.refuse = async function(item) { 
    	let object = angular.copy(item);
    	//console.log(object)
    	let problem = object.problem;
    	let card = object.card;
    	for(let i = 0; i < $scope.listRPAll.length; i++){
			if($scope.listRPAll[i].problem.id == problem.id && $scope.listRPAll[i].card.id == card.id){
				//console.log($scope.listRPAll[i]);
				$scope.listRPAll[i].status.id = 14;
				await $scope.reportcardAPI.update($scope.listRPAll[i].id, $scope.listRPAll[i]) 
				$scope.listRPAll[i].card.status.id = 2;
				await $scope.cardAPI.update($scope.listRPAll[i].card.id, $scope.listRPAll[i].card)   
			}  
		}  
		let obj_news = {
			sentUser: { id: JSON.parse(sessionStorage.usersession) },
			gotUser: card.user,
			content: "Sản phẩm của bạn đã bị vi phạm: "+problem.description+"!",
			newsDate: new Date(),
			hidden: false,
		}
		console.log(obj_news)
		$("#check-modal").modal("hide");
		$("#check-modalf").modal("hide");  
		await $scope.newAPI.create(obj_news)
		await $scope.goPage(1);
	}
  
  	$scope.tuchoi = async function(item) { 
    	let object = angular.copy(item);
    	//console.log(object)
    	let problem = object.problem;
    	let card = object.card;
    	for(let i = 0; i < $scope.listRPAll.length; i++){
			if($scope.listRPAll[i].problem.id == problem.id && $scope.listRPAll[i].card.id == card.id){
				//console.log($scope.listRPAll[i]);
				$scope.listRPAll[i].status.id = 13;
				await $scope.reportcardAPI.update($scope.listRPAll[i].id, $scope.listRPAll[i]);
			}  
		} 
		await $scope.goPage(1);
	}
  	//
  	 $scope.unRefuse = async function(item) { 
    	let object = angular.copy(item); 
    	let problem = object.problem;
    	let card = object.card;
    	for(let i = 0; i < $scope.listRPAll.length; i++){
			if($scope.listRPAll[i].problem.id == problem.id && $scope.listRPAll[i].card.id == card.id){ 
				$scope.listRPAll[i].status.id = 13;
				await $scope.reportcardAPI.update($scope.listRPAll[i].id, $scope.listRPAll[i]) 
			}  
		}
		
		let obj_news = {
			sentUser: { id: JSON.parse(sessionStorage.usersession) },
			gotUser: { id: card.user.id },
			content: "Sản phẩm của bạn được gỡ vi phạm: "+problem.description+"!",
			newsDate: new Date(),
			hidden: false,
		}
		await $scope.newAPI.create(obj_news);
		
		await  $scope.getRPAll();
		let check = false; 
		for(let i = 0; i < $scope.listRPAll.length; i++){
			if($scope.listRPAll[i].status.id == 14 && $scope.listRPAll[i].card.id == card.id){
				check = true;
				break;
			}  
		} 
		if(check == false){ 
			card.status.id = 3;
			await $scope.cardAPI.update(card.id, card) 
			let obj_news = {
				sentUser: { id: JSON.parse(sessionStorage.usersession) },
				gotUser: { id: card.user.id },
				content: "Sản phẩm của bạn đã được khôi phục quyền hiển thị lên trang cửa hàng!",
				newsDate: new Date(),
				hidden: false,
			}
			await $scope.newAPI.create(obj_news)
		}
		$("#check-modal").modal("hide");
		$("#check-modalf").modal("hide"); 
		await $scope.goPage(1);
		 
	}
  
    //3. Go delete from table
  	$scope.goDeleteFromTable = function(item){
		$scope.obj_order_delete = item; 
	}
   
 	//7. load category
 	$scope.loadCategory= async function (){
		$scope.listCategory = await $scope.categorieAPI.getAll();
		$scope.$digest();
	}
	  
 	//
	$scope.getProductAll = async function() {
		$scope.listProductAll = await $scope.productAPI.getAll(); 
		console.log($scope.listProductAll)
		$scope.$digest();
	}
 	
 	$scope.getProduct = function(card){  
		//console.log($scope.listProductAll)
		for(let i = 0 ; i<  $scope.listProductAll.length; i++){
			if( $scope.listProductAll[i].card.id == card.id){ 
				//console.log($scope.listProductAll[i])
				return $scope.listProductAll[i];
			}
		}  
		
	}
 	
 	$scope.getRPAll = async function(){
		$scope.listRPAll = await $scope.reportcardAPI.getAll();
		$scope.$digest();
		//console.log($scope.listRPAll);
	}
 	
 	
    /*END CODE */
//<----------------------------------- INITIALIZE ----------------------------------->
    $scope.initialize = async function() {   
		let lActive = 6;
		let Tsearch = "";
        await $scope.listActiveAll(lActive,Tsearch); 
        await $scope.loadCategory(); 
        await $scope.getProductAll();  
    }
//<----------------------------------- END INITIALIZE ----------------------------------->    
    $scope.initialize();
});