/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */

 
app.controller('quanlydoitac-ctrl', function($scope, $http, $rootScope) {
	
	 window.socket.setOnUser({
        onCreate: function(data) {
			alert('Có cập nhật mới');
        },
        onUpdate: function(data) {
			console.log(data);
			alert('Có cập nhật mới');
        },
        onDelete: function(data) {
			alert('Có cập nhật mới');
        }
    })
	
	const registeractivesAPI = new GlobalAPI();
    registeractivesAPI.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);
    
    const usersAPI = new GlobalAPI();
    usersAPI.setNamespace(GlobalAPI.namespace.USER);
    
    const bservicehistoriesAPI = new GlobalAPI();
    bservicehistoriesAPI.setNamespace(GlobalAPI.namespace.BSERVICE_HISTORY);
    
    const orderdetailsAPI = new GlobalAPI();
    orderdetailsAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);
    
    const orderAPI = new GlobalAPI();
    orderAPI.setNamespace(GlobalAPI.namespace.ORDER);
    
    const postAPI = new GlobalAPI();
    postAPI.setNamespace(GlobalAPI.namespace.POST);
    
    const lovecardsAPI = new GlobalAPI();
    lovecardsAPI.setNamespace(GlobalAPI.namespace.LOVECARD);

    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listBanner = [];

    //3. form
    $scope.formBanner = {}

	//4. from edit
	$scope.formeditBanner ={};
	
	$scope.listTitle = [
             { id: 'Id', title1: 'Tên dịch vụ', title2: 'Người bán', title3: 'Người mua',title4: 'Ngày tạo', title5: 'Thời hạn', title6: 'Trạng thái' },
             { id: 'Id', title1: 'Tên sản phẩm', title2: 'Người bán', title3: 'Người mua',title4: 'Giá', title5: 'Thể loại', title6: 'Số lượng' },
             { id: 'Id', title1: 'Tiêu đề bài viết', title2: 'Người viết', title3: 'Trạng thái bán',title4: 'Trạng thái hiển thị', title5: 'Trạng thái bài đăng', title6: 'Ngày đăng' },
             { id: 'Id', title1: 'Tên người nhận', title2: 'Người bán', title3: 'Người mua',title4: 'Tổng hóa đơn', title5: 'Ngày mua', title6: 'Trạng thái' },
             { id: 'Id', title1: 'Người tương tác', title2: 'Người đăng bài', title3: 'Trạng thái bán',title4: 'Trạng thái hiển thị', title5: 'Trạng thái bài đăng', title6: 'Ngày tương tác' },
        ]
	
	$scope.listtitleShow = {};
	
	$scope.nameSearch = {};
	
	// session user đăng nhập
	$scope.sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
	
    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }
 
    /*END CODE TAB*/

    /*START CODE Funtion , desscription: hàm cơ bản*/
    
    $scope.showList =  function(item){
		$scope.listtitleShow = 	$scope.listTitle[item];
		if(item == 0){
			$scope.nameSearch = 'search0';
		}else if(item ==1){
			$scope.nameSearch = 'search1';
		}else if(item ==2){
			$scope.nameSearch = 'search2';
		}else if(item ==3){
			$scope.nameSearch = 'search3';
		}else if(item ==4){
			$scope.nameSearch = 'search4';
		}
	}
 

    //3. eidit  
    $scope.edit = function(item) {
        $scope.tab(2);
        $scope.formBanner = angular.copy(item);
        $scope.formeditBanner = $scope.formBanner;
        localStorage.obj_edit_banner = JSON.stringify($scope.formBanner);
    }
  	
  	//4. save 
    $scope.save = function() {
		let username = document.getElementById('username').value;
		let fullname = document.getElementById('fullname').value;
		let email = document.getElementById('email').value;
		let numberphone = document.getElementById('phonenum').value;
		let user = angular.copy($scope.formBanner);
		if((username != '') && (fullname != '') && (email != '') && (numberphone != '')){
			usersAPI.update(user.id, user).then(resp => {
	            $scope.formProduct = {};
	            $scope.formeditProduct = {};
				$scope.tab(1);	
	            $rootScope.Alert(true, "success", "Cập nhật đối tác Thành Công!", 3);
	            $scope.initialize();
			}).catch(erorr => {
				$rootScope.Alert(true, "danger", "Cập nhật đối tác thất bại!", 5);
				console.log("Erorr: ", erorr);
			}); 
		}
		 
    }
  
  
    //6. delete 
     $scope.delete = function(item) {
			let user = angular.copy(item);
			 console.log(user)
			user.blocked = true;
			usersAPI.update(user.id, user).then(resp => {
				$("#deleteModal").modal("hide");
		        $("#deleteModalf").modal("hide");
		        $scope.tab(1);
		        localStorage.removeItem('obj_edit_banner');
		        $scope.formBanner = {};
		        $scope.formeditBanner ={};
		        $rootScope.Alert(true, "success", "Khóa đối tác Thành Công!", 3);
		        $scope.initialize();
			}).catch(erorr => {
				$rootScope.Alert(true, "success", "Khóa đối tác thất bại!", 3);
				console.log("Erorr: ", erorr);
			}); 
		
		 
    }
    
    $scope.layid = {};
    $scope.setId = function(item){
		$scope.layid = angular.copy(item);
	}
	
	//format currency
	$scope.currencyFormat = function(money){
		const config = { style: 'currency', currency: 'VND'}
	    const formated = new Intl.NumberFormat('vi-VN', config).format(money);
	    return formated;		
	}
    
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
		 let resp = await registeractivesAPI.getPageByProperties(NPage, Nitem, data)
            $scope.listBanner = resp.content; 
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
    
    
    
    //5. Số vị trí trang đang sử dụng
    $scope.APages = 1;

    //6. Số trang hiện tại
    $scope.NPages = 1;

    //7. Số trang tối đa
    $scope.MaxPages = 1;

    //8. Số phần tử của 1 trang
    $scope.Nitems = 1;

    //9. Button Pagination
    $scope.listButtons = [];
	
	//10. Active list 
	$scope.listActives = 2;
	
	//12. search
	$scope.searchtexts = "";
    
    $scope.listHistory = [];
    
    $scope.listOrder = [];
    
    //1. Hàm Danh sách list
    $scope.getButtonPaginationHistory = function() {   
		$scope.listButtons = []; 
        for (let i = 1; i <= $scope.MaxPages; i++) {
            $scope.listButtons.push(i); 
        }
        return $scope.listButtons;
    }
    
    //2. Truy vấn page
    $scope.queryPageHistory = async function(NPages, Nitems) {
        //lấy sp
        this.NPages = this.NPages;
         let data = {
			'key': $scope.searchtexts,
			'user': $scope.formBanner.id,
			'status': $scope.listActives,
		}
		 let resp = await bservicehistoriesAPI.getPageByProperties(NPages, NPages, data)
            $scope.listHistory = resp.content; 
            $scope.MaxPages = resp.totalPages;
            $scope.getButtonPaginationHistory();
            $scope.$digest();      
    } 
    
    //3. Phân trang
    $scope.goPageHistory = function(NumPages) { 
	
        //5.1 ràng buộc phạm vi
        if (NumPages > $scope.MaxPages) {
            this.NPages = $scope.MaxPages;
        } else if (NumPages < 1) {
            this.NPages = 1;
        } else {
            this.NPages = NumPages;
        } 
        //5.2 set page hiện tại
        $scope.APages = this.NPages;

        //5.3 Số trang hiện tại
        $scope.NPages = this.NPages;
 
        //5.4 lấy SP
        $scope.queryPageHistory(this.NPages, this.Nitems);
    }
    
    //4.  list Hiển Thị ALL
    $scope.listActiveAllHistory = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtexts = Tsearch;
        $scope.listActives = lActive;
        $scope.queryPageHistory($scope.NPages, $scope.Nitems); 
    }
    
    //5. go Search 
    $scope.goSearchAHistory = function (keysearch){
		let Tsearch = keysearch.value;
		if(keysearch.id == 'search0'){
			if((Tsearch != null) | (Tsearch != '')){
				let lActive =  $scope.listActives 
				if(lActive > 0){ 
					 lActive = -($scope.listActives);  
				} 
				$scope.NPages = 1;  
		 	 	$scope.listActiveAllHistory(lActive,Tsearch)
			} 
			$scope.APages = 1;
		}
	}
	
	//5. chuyen tab link
    $scope.changeTabPanelLinkHistory  = function(alink){ 
		//2.1. Số vị trí trang đang sử dụng
	    $scope.APages = 1;
	     
	    //2.2. Số trang hiện tại
	    $scope.NPages = 1;
	    //2.3. Key search
		let Tsearch = "";
		//2.4. GO PAGE
		$scope.listActiveAllHistory(alink,Tsearch); 
	}
    
    $scope.initializeHistory = function() { 
		let lActive = 2;
		let Tsearch = "";
		$scope.showList(0);
        $scope.listActiveAllHistory(lActive,Tsearch); 
    }
    
    // hiển thị theo sản phẩm
    //2. Truy vấn page
    $scope.queryPageProduct = async function(NPages, Nitems) {
        //lấy sp
        this.NPages = this.NPages;
         let data = {
			'key': $scope.searchtexts,
			'user': $scope.formBanner.id,
			'status': $scope.listActives,
		}
		 let resp = await orderdetailsAPI.getPageByProperties(NPages, NPages, data)
            $scope.listHistory = resp.content; 
            $scope.MaxPages = resp.totalPages;
            $scope.getButtonPaginationHistory();     
            $scope.$digest(); 
    } 
    
    
     //4.  list Hiển Thị ALL
    $scope.listActiveAllProduct = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtexts = Tsearch;
        $scope.listActives = lActive;
        $scope.queryPageProduct($scope.NPages, $scope.Nitems); 
    }
    
    //5. go Search 
    $scope.goSearchAProduct = function (keysearch){
		let Tsearch = keysearch.value;
		if(keysearch.id == 'search1'){
			if((Tsearch != null) | (Tsearch != '')){
				let lActive =  $scope.listActives 
				if(lActive > 0){ 
					 lActive = -($scope.listActives);  
				} 
				$scope.NPages = 1;  
		 	 	$scope.listActiveAllProduct(lActive,Tsearch)
			} 
			$scope.APages = 1;
		}
	}
    
    
    $scope.initializeProduct = function() { 
		let lActive = 1;
		let Tsearch = "";
		$scope.showList(0);
        $scope.listActiveAllProduct(lActive,Tsearch); 
    }
    
    // hiển thị theo đơn hàng
    //2. Truy vấn page
    $scope.queryPageOrder = async function(NPages, Nitems) {
        //lấy sp
        this.NPages = this.NPages;
        let data = {
			'key': $scope.searchtexts,
			'user': $scope.formBanner.id,
			'status': $scope.listActives,
		}
		 let resp = await orderAPI.getPageByProperties(NPages, Nitems, data)
            $scope.listOrder = resp.content; 
            $scope.MaxPages = resp.totalPages;
            $scope.getButtonPaginationHistory();
            $scope.$digest();   
    } 
    
    //4.  list Hiển Thị ALL
    $scope.listActiveAllOrder = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtexts = Tsearch;
        $scope.listActives = lActive;
        $scope.queryPageOrder($scope.NPages, $scope.Nitems); 
    }
    
    //5. go Search 
    $scope.goSearchAOrder = function (keysearch){
		let Tsearch = keysearch.value;
		if(keysearch.id == 'search3'){
			if((Tsearch != null) | (Tsearch != '')){
				let lActive =  $scope.listActives 
				if(lActive > 0){ 
					 lActive = -($scope.listActives);  
				} 
				$scope.NPages = 1;  
		 	 	$scope.listActiveAllOrder(lActive,Tsearch)
			} 
			$scope.APages = 1;
		}
	}
    
    $scope.initializeOrder = function() { 
		let lActive = 8;
		let Tsearch = "";
		$scope.showList(0);
        $scope.listActiveAllOrder(lActive,Tsearch); 
    }
    
    
    // hiển thị theo bài đăng
    //2. Truy vấn page
    $scope.queryPagePost = async function(NPages, Nitems) {
        //lấy sp
        this.NPages = this.NPages;
        let data = {
			'key': $scope.searchtexts,
			'user': $scope.formBanner.id,
			'status': $scope.listActives,
		}
		 let resp = await postAPI.getPageByProperties(NPages, Nitems, data)
            $scope.listHistory = resp.content; 
            $scope.MaxPages = resp.totalPages;
            $scope.getButtonPaginationHistory();
            $scope.$digest();  
    } 
    
    
     //4.  list Hiển Thị ALL
    $scope.listActiveAllPost = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtexts = Tsearch;
        $scope.listActives = lActive;
        $scope.queryPagePost($scope.NPages, $scope.Nitems); 
    }
    
    //5. go Search 
    $scope.goSearchAPost = function (keysearch){
		console.log(keysearch.id)
		let Tsearch = keysearch.value;
		if(keysearch.id == 'search2'){
			if((Tsearch != null) | (Tsearch != '')){
				let lActive =  $scope.listActives 
				if(lActive > 0){ 
					 lActive = -($scope.listActives);  
				} 
				$scope.NPages = 1;  
		 	 	$scope.listActiveAllPost(lActive,Tsearch)
			} 
			$scope.APages = 1;
		}
	}
    
    
    $scope.initializePost = function() { 
		let lActive = 1;
		let Tsearch = "";
		$scope.showList(0);
        $scope.listActiveAllPost(lActive,Tsearch); 
    }
    
    // hiển thị theo Tương tác
    //2. Truy vấn page
    $scope.queryPageLoveCard = async function(NPages, Nitems) {
        //lấy sp
        this.NPages = this.NPages;
        let data = {
			'key': $scope.searchtexts,
			'user': $scope.formBanner.id,
			'status': $scope.listActives,
		}
		 let resp = await lovecardsAPI.getPageByProperties(NPages, Nitems, data)
            $scope.listHistory = resp.content; 
            $scope.MaxPages = resp.totalPages;
            $scope.getButtonPaginationHistory();
            $scope.$digest();  
    } 
    
    
     //4.  list Hiển Thị ALL
    $scope.listActiveAllLoveCard = function(lActive, Tsearch) {	
        //1. lấy maxpage 
		$scope.searchtexts = Tsearch;
        $scope.listActives = lActive;
        $scope.queryPageLoveCard($scope.NPages, $scope.Nitems); 
    }
    
    //5. go Search 
    $scope.goSearchALoveCard = function (keysearch){
		let Tsearch = keysearch.value;
		if(keysearch.id == 'search4'){
			if((Tsearch != null) | (Tsearch != '')){
				let lActive =  $scope.listActives 
				if(lActive > 0){ 
					 lActive = -($scope.listActives);  
				} 
				$scope.NPages = 1;  
		 	 	$scope.listActiveAllLoveCard(lActive,Tsearch)
			} 
			$scope.APages = 1;
		}
	}
    
    
    $scope.initializeLoveCard = function() { 
		let lActive = 1;
		let Tsearch = "";
		$scope.showList(0);
        $scope.listActiveAllLoveCard(lActive,Tsearch); 
    }
    
    Validator({
            form: '#formbanner',
            formGroupSelector: '.form-group',
            errorSelector: '.form-message',
            invalidSelector: '.invalid',
            rules: [ 
                Validator.isRequired("#id", 'Vui lòng nhập id!'), 
                Validator.isRequired("#email", 'Vui lòng nhập email!'), 
                Validator.isRequired("#fullname", 'Vui lòng nhập họ tên!'),  
                Validator.isRequired("#username", 'Vui lòng nhập username!'),  
                Validator.isRequired("input[name='gender']", 'Vui lòng chọn giới tính!'), 
                Validator.isRequired("#phonenum", 'Vui lòng nhập số điện thoại!'),  
            ],
            onSubmit: function(data) {
                console.log("dssdffssdsdfsdff", data);
            }
        });

});