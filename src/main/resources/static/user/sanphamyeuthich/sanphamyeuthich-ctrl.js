var producti = 0;

app.controller('sanphamyeuthich-ctrl', function($scope, $rootScope, $http) {
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
	$scope.listActive = 1;
	
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
    
    //Danh sách hiển thị
     $scope.items = [];
    
    // list filter
    $scope.listLovecard=[];
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
        for (let i = 1; i <= $scope.MaxPage; i++) {
            $scope.listButton.push(i); 
        }
        return $scope.listButton;
    }

    //2. Truy vấn page
    $scope.queryPage = async function(NPage, Nitem) {
        //lấy sp
        //Danh sách hiển thị
       // $scope.items = [];
        this.NPage = this.NPage; 
        //let get_data  = await $scope.productAPI.getPageProductUserShop(NPage, Nitem, $scope.listActive, $scope.searchtext, JSON.parse(sessionStorage.usersession)) 
        let get_data  =  await $scope.productAPI.getPageByProperties(NPage, Nitem, {'pagewhere': $scope.listActive, 'key':$scope.searchtext, 'user':JSON.parse(sessionStorage.usersession)}); 
        $scope.listProduct = get_data.content; 
        $scope.MaxPage = get_data.totalPages;
        $scope.getButtonPagination(); 
        //truy cập shop của sp
		let get_shop_data = await $scope.shopAPI.getAll();
		$scope.shop = get_shop_data;
		//lưu danh sách hiển thị
		$scope.listProduct.map(p => {
			$scope.shop.map(s => {
				if (p.card.user.id === s.user.id) {
					let itemShow = {
						shop: s,
						product: p
					}
					$scope.items.push(itemShow);
				}
			});
		});
 		$scope.$digest();   
    } 

	//2. Phân trang
    $scope.goPage = async function(NumPage) { 
	
        //3.1 ràng buộc phạm vi
        if (NumPage > $scope.MaxPage) {
            this.NPage = $scope.MaxPage;
        } else if (NumPage < 1) {
            this.NPage = 1;
        } else {
            this.NPage = NumPage;
	         //3.2 set page hiện tại
	        $scope.APage = this.NPage;
	
	        //5.3 Số trang hiện tại
	        $scope.NPage = this.NPage;
	 
	        //5.4 lấy SP
	        await $scope.queryPage(this.NPage, this.Nitem);
	        $scope.$digest();
        } 
       
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
    $scope.goSearchA = async function (keysearch){ 
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

	/**LIKE - UNLIKE */
	//GET LIST LIKE
	$scope.getLike = async function(){
		//$scope.listLovecard = await $scope.productAPI.getPageProductUserShop(1, 6, 4, $scope.searchtext, JSON.parse(sessionStorage.usersession))
		$scope.listLovecard = await $scope.loveCardAPI.getPageByProperties(1, 6, {'status': 4, 'key':$scope.searchtext, 'user':JSON.parse(sessionStorage.usersession)})
		.catch(Error=>{
			$scope.listLovecard =[];
		}) 
		$scope.$digest();
		//$http.get(" /biglobby/products/user/page/" + 1 + "/" + 6 +"/"+ 4 +"?key="+  + "&user="  + ).then(resp => {
        	// resp.data;  
        	//console.log("___LIST_____");
        	//console.log($scope.listLovecard);
        //})
       
		
	}
	//check like
	$scope.checkLike = function (product){
		// console.log($scope.listLovecard.content);
		// console.log($scope.listLovecard);
		let check = false;
		
		if($scope.listLovecard.totalElements >=1){
			
			for (let i = 0; i < $scope.listLovecard.totalElements; i++) {  
				if ($scope.listLovecard.content[i].card.id == product.card.id) {
					check = true;
					//console.log("L: " + $scope.listLovecard.content[i].card.id + " P:" + product.card.id);
					break;
				}
				//console.log("_______________________________________")
				//console.log(product.id)
				//console.log($scope.listLovecard.content[i].id)
				//console.log("_______________________________________")
			}
		}else{
			check = false;
		}
		
		return check;
	}
	 
	 
	//LIKEs
	$scope.Like = async function(product){
		let obj_lovecard = {
			user: {id: JSON.parse(sessionStorage.usersession)},
			card: product.card,
			loveDate:new Date,
		}
		await $scope.loveCardAPI.create(obj_lovecard)
	    .catch(Error=>{ alert("Thích sản phẩm thất bại");})
		await $scope.getLike();
	}
	//UNLIKE
	$scope.unLike= async function(product){
		//console.log("UN LIKE ACTION");
		//console.log(JSON.parse(sessionStorage.usersession))
		//console.log(product.card.id)
		//let get_lovecard_data = await $scope.loveCardAPI.getLoveCard(JSON.parse(sessionStorage.usersession),product.card.id)
		let get_lovecard_data = await $scope.loveCardAPI.getByProperty("user.id",JSON.parse(sessionStorage.usersession)+"&card.id="+product.card.id)
		await $scope.loveCardAPI.delete(get_lovecard_data.id);
		await $scope.getLike(); 
		await $scope.goPage(1);
	}
	/** BEGIN:Ham hàm funcctioon cơ bản */
    //hàm xử lý nút like sản phẩm
    $scope.likeProduct = function(elm) {
        let aParent = elm.parentNode;
        aParent.addEventListener("click", function(event) {
            event.preventDefault()
        });
        elm.classList.toggle("like--active");
    }

    //hàm xử lý nút ẩn sản phẩm
    $scope.hideProduct = function(elm) {
        var card = elm.parentNode;
        card.addEventListener("click", function(event) {
            event.preventDefault()
        });
        var cardClassName = card.className;
        while (cardClassName.indexOf('card') < 0) {
            card = card.parentNode;
            cardClassName = card.className;
        }
        var cardOld = card.innerHTML;
        elmTemp = `
		        
               <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%">
       				 <p>Đã ẩn sản phẩm.</p>
       				 <button type="button" class="btn__ btn__--green" onclick="angular.element(this).scope().showProduct(this)">Hoàn tác</button>
  			  </div>
        `;
        card.innerHTML = elmTemp;
        cardOld = cardOld.replace("dropdown-menu show", "dropdown-menu");

        $scope.showProduct = function(elm) {
            var cardShow = elm.parentNode;
            var cardShowClassName = cardShow.className;
            while (cardShowClassName.indexOf('card') < 0) {
                cardShow = cardShow.parentNode;
                cardShowClassName = cardShow.className;
            }
            cardShow.innerHTML = cardOld;

        }
    }

	$scope.showDetail = function(product) {
		sessionStorage.productobj = JSON.stringify(product);
		location.href = "#!biglobby/chitietsanpham";
	}


 /* END CODE: hàm function cơ bản */
//<----------------------------------- INITIALIZE ----------------------------------->
    $scope.initialize = async function() {   
		let lActive =1;
		let Tsearch = "";
        await $scope.listActiveAll(lActive,Tsearch);  
        await $scope.getLike();
    }
//<----------------------------------- END INITIALIZE ----------------------------------->    
    $scope.initialize();

});