var producti = 0;

app.controller('cuahang-ctrl', function($scope, $rootScope, $http) {
    window.socket.setOnProduct({
        onCreate: function(data) {
           	 
        },
        onUpdate: function(data) {    
			for (let i = 0; i < $scope.items.length; i++) {

				if ($scope.items[i].product.id == data.id) {
					//console.log($scope.items[i]); 
					// sản phẩm bị ẩn không công khai
					 $scope.items[i].product = data; 
					check = true;
					$scope.$digest();
					break;
				}

			} 
        },
        onDelete: function(data) { 
        }
    })

    window.socket.setOnCard({
        onCreate: function(data) {
            
        },
        onUpdate: function(data) { 
			//bắt đâu real time
	        if(data.isProduct === true){
				let check = false;  
				for(let i = 0; i <  $scope.items.length; i++){
					 
					if($scope.items[i].product.card.id == data.id){ 
						//console.log($scope.items[i]);
						//sản phẩm bị vi phạm 
						if(data.status.id === 2){
							$scope.items.splice(i, 1);
						}
						// sản phẩm bị ẩn không công khai
						else if (data.hidden === true){ 
							$scope.items.splice(i, 1 );
						} 
						// trường hợp khác
						else{
							console.log("no work")
						}
						check = true;
						break;
					}
				 
				}
				// nếu chưa có trên giao diện
				if(check === false){
					//nếu được thay đổi thành trạng thái 3
					if(data.status.id == 3){
						console.log("Có cập nhật mới!")
						//alert('có sản phẩm mới được thêm!')
						$rootScope.Alert(true, "success","Có cập nhật mới", 3);
					}
					// trường hợp khác không làm gì cả
					else{
						console.log("no work")
					} 
					
				}
			}
			//console.log($scope.items) 
			$scope.$digest();
			//hoàn thành real time
           
        },
        onDelete: function(data) {
			//hoàn thành realtime
        	if(data.isProduct === true){
				let check = false;
				for(let i = 0; i <  $scope.items.length; i++){
					if($scope.items[i].product.card.id == data.id){
						$scope.items.splice(i, 1); 
						check = true;
						break;
					}
				}
				
			}
			// hoàn thành real time
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

    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listProduct = [];

    //3. form
    $scope.formProduct = {}

    //4. from edit
    $scope.formeditProduct = {};

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
    $scope.listActive = 3;

    //11. Số lượng phần tử
    $scope.Nproduct = 1;

    //12. search
    $scope.searchtext = "";

    //13. object delete
    $scope.obj_order_delete = {};

    //14. list category
    $scope.listCategory = [];

    //15. nội dung từ chối
    $scope.formContent = {};

    //16. lối vi phạm
    $scope.messLoi = "";

    //17. Danh sách hiển thị
    $scope.items = [];

    //18. Localstore SHOP
    //$scope.IdShop =  JSON.parse(localStorage.idShopDetail)

    //19. get register
    $scope.register = {};

    // 20. page product
    $scope.pageProductActiveShop = [];

    //21. follow shop all
    $scope.pageFollowShopALl = [];

    //22. sô lượng đã bán
    $scope.sumQuantitySaleOfShop = 0;

    //23. obj_follow
    $scope.obj_follow = {};

    //24. list category
    $scope.listCategory = [];

    // location
    $scope.listLocation = [];

    //26. slect category
    $scope.categoryselect = [];
    //27. select location
    $scope.locationselect = [];

    //from
    $scope.fr = {};

    $scope.obj_filter = {};

    // list filter
    $scope.listLovecard = [];

    //list vấn đề vi phạm
    $scope.selectproblem = [];

    $scope.listProblem = [];
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
        //$scope.items = [];
        this.NPage = this.NPage;
        //let data_get = await $scope.productAPI.getPageProductUserShop(NPage, Nitem, $scope.listActive, $scope.searchtext, JSON.parse(sessionStorage.usersession))
        let data_get = await $scope.productAPI.getPageByProperties(NPage, Nitem, { 'pagewhere': $scope.listActive, 'key': $scope.searchtext, 'user': JSON.parse(sessionStorage.usersession) });
        console.log(data_get);
        $scope.listProduct = data_get.content;
        $scope.pageProductActiveShop = data_get;
        $scope.MaxPage = data_get.totalPages;
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

    //2. Truy vấn page
    $scope.queryPageSearch = async function(NPage, Nitem) {
        //lấy sp
        //Danh sách hiển thị
        //$scope.items = [];
        this.NPage = this.NPage;
        //let get_data = await $scope.productAPI.getPageProductUserShopFilter($scope.NPage, $scope.Nitem, $scope.listActive, $scope.obj_filter); 
        let get_data = await $scope.productAPI.getPageByPropertiesAndFilter(NPage, Nitem, { 'pagewhere': $scope.listActive }, $scope.obj_filter);
        $scope.listProduct = get_data.content;
        $scope.pageProductActiveShop = get_data;
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
                alert("Đã hết sản phẩm");
            } else if (NumPage < 1) {
                this.NPage = 1;
                alert("Đã hết sản phẩm");
            } else {
                this.NPage = NumPage;
                //3.2 set page hiện tại
                $scope.APage = this.NPage;

                //5.3 Số trang hiện tại
                $scope.NPage = this.NPage;
                //5.4 lấy SP
                if ($scope.listActive < 0) {
                    await $scope.queryPageSearch(this.NPage, this.Nitem)
                } else {
                    await $scope.queryPage(this.NPage, this.Nitem);
                }
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
    $scope.changeTabPanelLink = async function(alink) {
        //2.1. Số vị trí trang đang sử dụng
        $scope.APage = 1;

        //2.2. Số trang hiện tại
        $scope.NPage = 1;
        //2.3. Key search
        let Tsearch = "";
        //2.4. GO PAGE
        await $scope.listActiveAll(alink, Tsearch);
    }

    //5. go Search 
    $scope.goSearchA = async function(keysearch) {
            let Tsearch = keysearch.value;
            //console.log(Tsearch)
            if ((Tsearch != null) | (Tsearch != '')) {
                let lActive = $scope.listActive
                if (lActive > 0) {
                    lActive = -($scope.listActive);
                }
                $scope.NPage = 1;
                $scope.listActiveAll(lActive, Tsearch)


            }
            $scope.APage = 1;
        }
        /**END PAGINATION */


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


    //7. load category
    $scope.floadCategory = async function() {
            let category = await $scope.categorieAPI.getCollections("categoryStatus", "");
            for (let i = 0; i < category.length; i++) {
                if (category[i].blocked == true) {
                    category.splice(i, 1);
                }
            }
            $scope.loadCategory = category;
            $scope.$digest();
            // console.log($scope.loadCategory) 
        }
        // load vij tris shop
    $scope.loadLocation = function() {
            $scope.listLocation = [{ name: "Ninh Kiều" }, { name: "Bình Thủy" }, { name: "Cái Răng" }, { name: "Ô Môn" }, { name: "Thốt Nốt" },
                { name: "Cờ Đỏ" }, { name: "Thới Lai" }, { name: "Phong Điền" }, { name: " Vĩnh Thạnh" }
            ];
            //console.log($scope.listLocation)
        },

        $scope.selectCheckLocation = function(location) {
            var idx = $scope.locationselect.indexOf(location.name);
            if ((idx > -1)) {
                $scope.locationselect.splice(idx, 1);
            } else {
                $scope.locationselect.push(location.name);
            }
            //console.log($scope.locationselect);
        }

    $scope.existLoca = function(location) {
        return $scope.locationselect.indexOf(location.name) > -1;
    }


    $scope.selectCheckCategory = function(category) {
        var idx = $scope.categoryselect.indexOf(category.category);
        if ((idx > -1)) {
            $scope.categoryselect.splice(idx, 1);
        } else {
            $scope.categoryselect.push(category.category);
        }
        //console.log($scope.categoryselect);
    }

    $scope.exist = function(category) {
        return $scope.categoryselect.indexOf(category) > -1;
    }

    $scope.goFilter = async function() {
        $scope.items = [];
        let f = $scope.fr;
        let search = f.keySearch;
        let fPrice = f.fromPrice;
        let tPrice = f.toPrice;
        let lCategory = $scope.categoryselect;
        let lLocation = $scope.locationselect;
        let Filter = {
            key: search,
            from: fPrice,
            to: tPrice,
            category: lCategory,
            location: lLocation,
            user: JSON.parse(sessionStorage.usersession)
        }
        $scope.obj_filter = Filter;
        /*console.log("_____________________________________________")
        console.log("Search"); 
        console.log(search);
        console.log("f price"); 
        console.log(fPrice);
        console.log("t price"); 
        console.log(tPrice);
        console.log("l cate"); 
        console.log(lCategory);
        console.log("l loca"); 
        console.log(lLocation); 
        console.log("_____________________________________________")*/
        $scope.listActive = -100;
        await $scope.goPage(1);
    }

    $scope.removeFilter = async function() {
        $scope.items = [];
        $scope.obj_filter = {};
        $scope.NPage = 1;
        $scope.APage = 1;
        $scope.categoryselect = [];
        //27. select location
        $scope.locationselect = [];
        let lActive = 3;
        let Tsearch = "";
        await $scope.listActiveAll(lActive, Tsearch);
    }

    /**LIKE - UNLIKE */
    //GET LIST LIKE
    $scope.getLike = async function() {
            //$scope.listLovecard = await $scope.productAPI.getPageProductUserShop(1, 6, 4, $scope.searchtext, JSON.parse(sessionStorage.usersession))
            $scope.listLovecard = await $scope.loveCardAPI.getPageByProperties(1, 6, { 'status': 4, 'key': $scope.searchtext, 'user': JSON.parse(sessionStorage.usersession) })
                .catch(Error => {
                    $scope.listLovecard = [];
                })
                //console.log($scope.listLovecard)
            $scope.$digest();
            //$http.get(" /biglobby/products/user/page/" + 1 + "/" + 6 +"/"+ 4 +"?key="+  + "&user="  + ).then(resp => {
            // resp.data;  
            //console.log("___LIST_____");
            //console.log($scope.listLovecard);
            //})


        }
        //check like
    $scope.checkLike = function(product) {
        // console.log($scope.listLovecard.content);
        // console.log($scope.listLovecard);
        let check = false;

        if ($scope.listLovecard.totalElements >= 1) {

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
        } else {
            check = false;
        }

        return check;
    }

    //LIKEs
    $scope.Like = async function(product) {
            let obj_lovecard = {
                user: { id: JSON.parse(sessionStorage.usersession) },
                card: product.card,
                loveDate: new Date,
            }
            await $scope.loveCardAPI.create(obj_lovecard)
                .catch(Error => {
	 		//alert("Thích sản phẩm thất bại");
	 		$rootScope.Alert(true, "danger","Thích sản phẩm thất bại", 3);
	  })
            await $scope.getLike();
        }
        //UNLIKE
    $scope.unLike = async function(product) {
        //console.log("UN LIKE ACTION");
        //console.log(JSON.parse(sessionStorage.usersession))
        //console.log(product.card.id)
        let get_lovecard_data = await $scope.loveCardAPI.getByProperty("user.id", JSON.parse(sessionStorage.usersession) + "&card.id=" + product.card.id)
        await $scope.loveCardAPI.delete(get_lovecard_data.id);
        await $scope.getLike();
    }

    /**BEGIN HAOTN  */
    //hàm xử lý nút like sản phẩm
    /*$scope.likeProduct = function(elm) {
        let aParent = elm.parentNode;
        aParent.addEventListener("click", function(event) {
            event.preventDefault()
        });
        elm.classList.toggle("like--active");
    }*/

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
            console.log(cardOld)
            var cardShow = elm.parentNode;
            var cardShowClassName = cardShow.className;
            while (cardShowClassName.indexOf('card') < 0) {
                cardShow = cardShow.parentNode;
                cardShowClassName = cardShow.className;
            }
            cardShow.innerHTML = cardOld;
        }

    }

    /**END HAOTN */

    $scope.showDetail = function(product) {
        sessionStorage.productobj = JSON.stringify(product);
        location.href = "#!biglobby/chitietsanpham";
    }

    /**By TRUONGNVN */

    /**PROBLEM */
    //load problem
    $scope.loadProblem = async function() {
        $scope.listProblem = await $scope.problemAPI.getAll()
            .catch(Error => {
                $scope.listProblem = [];
            })
        $scope.$digest();
    }

    //
    $scope.selectCheckProblem = function(problem) {
        var idx = $scope.selectproblem.indexOf(problem);
        if ((idx > -1)) {
            $scope.selectproblem.splice(idx, 1);
        } else {
            $scope.selectproblem.push(problem);
        }
        console.log($scope.selectproblem);
    }

    $scope.existProblem = function(problem) {
        return $scope.selectproblem.indexOf(problem) > -1;
    }

    //report
    $scope.reportProduct = async function(product_report) {
        console.log(product_report)
        for (let i = 0; i < $scope.selectproblem.length; i++) {
            let obj_report_card = {
                user: { id: JSON.parse(sessionStorage.usersession) },
                card: product_report.card,
                reportDate: new Date(),
                problem: { id: $scope.selectproblem[i].id },
                note: "",
                status: { id: 12 },
            }
            await $scope.reportcardAPI.create(obj_report_card)
        }
        $("#baoCaoViPham").modal("hide");
       // alert("Báo cáo đã được gửi với bộ phận quản trị");
       $rootScope.Alert(true, "warning","Báo cáo đã được gửi với bộ phận quản trị", 3);
    }

    $scope.hidenProduct = async function(product_obj) {
        let obj_hide = {
            user: { id: JSON.parse(sessionStorage.usersession) },
            card: product_obj.card,
            hideDate: new Date(),
        }
        await $scope.hidecardAPI.create(obj_hide)
        await $scope.goPage(1);
        alert("Đã ẩn sản phẩm này!")
    }

    /* END CODE: hàm function cơ bản */
    //<----------------------------------- INITIALIZE ----------------------------------->
    $scope.initialize = async function() {
            let lActive = 3;
            let Tsearch = "";
            await $scope.listActiveAll(lActive, Tsearch);
            await $scope.floadCategory();
            await $scope.loadLocation();
            await $scope.getLike();
            await $scope.loadProblem();
        }
        //<----------------------------------- END INITIALIZE ----------------------------------->    
    $scope.initialize();



});