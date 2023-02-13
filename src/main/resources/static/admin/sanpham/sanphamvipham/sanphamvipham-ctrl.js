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
 
app.controller('sanphamvipham-ctrl', function($scope, $http, $rootScope) {
	window.socket.setOnCard({
		onCreate: function(data) {

		},
		onUpdate: function(data) {

		},
		onDelete: function(data) {

		}
	})

	window.socket.setOnFailHistory({
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
	$scope.listActive = 5;
	
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
    
    //16. lối vi phạm
    $scope.messLoi = "";
    
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
		//let List = await $scope.productAPI.getPageProductAdmin(NPage, Nitem, true, $scope.listActive, "");
		let List = await $scope.productAPI.getPageByProperties(NPage, Nitem, {'pagewhere': $scope.listActive, 'key':$scope.searchtext}); 
		$scope.listProduct = List.content;
		$scope.MaxPage = List.totalPages;
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


    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
 
    /*END CODE TAB*/

    /*START CODE Funtion , desscription: hàm cơ bản*/ 
    //1. eidit  
    $scope.edit = async function(item) {
        $scope.tab(2);
        $scope.formProduct = angular.copy(item);
        $scope.formeditProduct =item; 
		/*let list = await $scope.failhistorieAPI.getCollections("card",item.card.id)
		$scope.messLoi = ""; 
		 for (let i = 0; i < list.length; i++) {
			if (i == list.length - 1) {
				$scope.messLoi += "VP" + (i + 1) + ": " + list[i].failReason + ". ";
			} else {
				$scope.messLoi += "VP" + (i + 1) + ": " + list[i].failReason + ", ";
			}
		}  */
		let list = await $scope.failhistorieAPI.getCollections("card",item.card.id);
		$scope.messLoi = "";
		for (let i = 0; i < list.length; i++) {
			if (i == list.length - 1) {
				$scope.messLoi += "VP" + (i + 1) + ": " + list[i].failReason + ". ";
			} else {
				$scope.messLoi += "VP" + (i + 1) + ": " + list[i].failReason + ", ";
			}
		}
		 
		await $scope.getTextViPham(item.card.id);
		
		$scope.$digest();
    }
  
  	//2. save 
   /* $scope.duyet = async function(item) { 
           //Sửa 
//           console.log($scope.formeditProduct);
//           console.log($scope.formProduct); 
           let form_edit = angular.copy(item);
           
           form_edit.card.status.id = 3;
             
           console.log(form_edit.card)
           $http.put('/biglobby/cards/'+ form_edit.card.id, form_edit.card)
           .then(resp=>{  
			   let obj_news = {
				   sentUser: { id: JSON.parse(sessionStorage.usersession) },
				   gotUser: resp.data.user,
				   content: "Sản phẩm của bạn đã được duyệt!",
				   newsDate: new Date(),
				   hidden: false,
			   }
			   console.log(obj_news)
			   $http.post('/biglobby/news', obj_news)
			   .then(resp=>{
					   $rootScope.Alert(true, "success", "Duyệt Thành Công!", 3);
					   $scope.tab(1);
			           $scope.formProduct = {};
			           $scope.formeditProduct ={}; 
			           $scope.listActiveAll($scope.listActive ,"");
			   })
			   .catch(Error =>{
					$rootScope.Alert(true, "danger", "Thông báo duyệt Thất bại!", 3);
			   })
		   })
		   .catch(Error =>{
				console.log(Error)
				 $rootScope.Alert(true, "danger", "Duyệt Thất bại!", 3);
		   })
    
	}
  */
    //3. Go delete from table
  	$scope.goDeleteFromTable = function(item){
		$scope.obj_order_delete = item; 
	}
  
    //4. delete 
    $scope.restore = async function(item) {
		item.card.status = {id: 3};
		let Content = angular.copy($scope.formContent.content) 
		if(Content == "" || Content == undefined){
			 Content = "Sản phẩm của bạn đã được khôi phục!";
		}
			let obj_card_update = await $scope.cardAPI.update( item.card.id, item.card)

		//history  
		let obj_news = {
			sentUser: { id: JSON.parse(sessionStorage.usersession) },
			gotUser: obj_card_update.user,
			content: Content,
			newsDate: new Date(),
			hidden: false,
		}
		console.log(obj_news)
		await $scope.newAPI.create(obj_news) 
		$("#deleteModal").modal("hide");
		$("#deleteModalf").modal("hide");
		$scope.tab(1);
		$scope.formProduct = {};
		$scope.formeditProduct = {};
		$rootScope.Alert(true, "success", "Đã Khôi  phục sản phẩm này Sản Phẩm này!", 3);
		await $scope.listActiveAll($scope.listActive, ""); 
		
    } 
 	 
 	//7. load category
 	$scope.loadCategory= async function (){
		$scope.listCategory = await $scope.categorieAPI.getAll();
	}
	
	//8. 
	$scope.getTextViPham = async function(id){  
		let list = await $scope.failhistorieAPI.getCollections("card",id);
		let textViPham = "";
		for (let i = 0; i < list.length; i++) {
			if (i == list.length - 1) {
				textViPham += "VP" + (i + 1) + ": " + list[i].failReason + ". ";
			} else {
				textViPham += "VP" + (i + 1) + ": " + list[i].failReason + ", ";
			}
		}
		 return textViPham;
	}
 	
 	
 	
    /*END CODE */
//<----------------------------------- INITIALIZE ----------------------------------->
    $scope.initialize = async function() {   
		let lActive = 5;
		let Tsearch = "";
        await $scope.listActiveAll(lActive,Tsearch); 
        await $scope.loadCategory();   
    }
//<----------------------------------- END INITIALIZE ----------------------------------->    
    $scope.initialize();
});