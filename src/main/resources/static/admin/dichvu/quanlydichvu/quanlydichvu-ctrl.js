/**
 * CodeDate: V1-09/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */
app.controller('quanlydichvu-ctrl', function($scope, $http, $rootScope) {

	const bservicesAPI = new GlobalAPI();
    bservicesAPI.setNamespace(GlobalAPI.namespace.BSERVICE);
    
    const bservicepricesAPI = new GlobalAPI();
    bservicepricesAPI.setNamespace(GlobalAPI.namespace.BSERVICE_PRICE);
    /*Begin Variable , desscription: biến khởi tạo*/
    //1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
    $scope.Atab = 1;

    //2. danh sách hiển thị 
    $scope.listBservice = [];

    //3. form
    $scope.formBservice = {}

    //4. form active
    $scope.formDataEdit = {};

    $scope.price = 0;

    $scope.image = '';
    
    $scope.hinhanh = [];

    /*End variable */

    /*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
    $scope.tab = function(tabnum) {
        //1. List-tab, 2. Form-tab 
        $scope.Atab = tabnum;
    }

    //2. chọn nút thêm 
    $scope.add = function() {
        localStorage.removeItem('obj_edit_bservice');
        $scope.formBservice = {};
        $scope.formDataEdit = {};
        $scope.image = '';
    }

    /*END CODE TAB*/

    //2. Kiểm tra form cập nhật hay thêm
    $scope.getAForm = function() {
        if ($scope.formDataEdit.bservice != null) {
            return true;
        } else {
            return false;
        }
    }

    //3. eidit  
    $scope.edit = function(itemformBservice) {
        $scope.formBservice = angular.copy(itemformBservice);
        $scope.price = $scope.formBservice.price;
        $scope.image = $scope.formBservice.price;
        $scope.formBservice.bservice.banner = itemformBservice.bservice.banner;
        $scope.formDataEdit = $scope.formBservice;
        console.log($scope.image)
        localStorage.obj_edit_bservice = JSON.stringify($scope.formBservice);
        $scope.tab(2);
    }


    //4. save 
    $scope.save = function() {
        let formDataInput = angular.copy($scope.formBservice);
        let bServiceV = document.getElementById('bService').value;
        let bServicePricesV = document.getElementById('bServicePrices').value;
        let descriptionV = document.getElementById('description').value;

        if ($scope.getAForm()) {
            //Cập Nhật
            console.log(formDataInput);
            if ((bServiceV != '') && (bServicePricesV != '') && (descriptionV != '')) {
				bservicesAPI.update(formDataInput.bservice.id, formDataInput.bservice).then(resp => {
                    if ($scope.price == formDataInput.price) {
                        $scope.formDataEdit = JSON.parse(localStorage.obj_edit_bservice);
                        $scope.tab(1);
                        localStorage.removeItem('obj_edit_bservice');
                        $scope.formBservice = {};
                        $scope.formDataEdit = {};
                        $scope.initialize();
                        $rootScope.Alert(true, "success", "Cập Nhật Thành Công!", 3);
                    } else {
                        let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
                        let bservices = resp;
                        let serviceP = {};
                        serviceP.price = formDataInput.price;
                        serviceP.priceDate = new Date();
                        serviceP.priceBy = { id: sessionUser };
                        serviceP.bservice = bservices;
                        serviceP.note = "Khởi tạo giá dịch vụ";
                        bservicepricesAPI.create(serviceP).then(resp => {
                            $scope.formDataEdit = JSON.parse(localStorage.obj_edit_bservice);
                            $scope.tab(1);
                            localStorage.removeItem('obj_edit_bservice');
                            $scope.formBservice = {};
                            $scope.formDataEdit = {};
                            $scope.initialize();
                            $rootScope.Alert(true, "success", "Cập Nhật Thành Công!", 3);
                        }).catch(erorr => {
                            alert("Cập nhật dịch vụ thất bại");
                            console.log("Erorr: ", erorr);
                        });
                    }
                }).catch(erorr => {
                    alert("Cập nhật dịch vụ thất bại");
                    console.log("Erorr: ", erorr);
                });
            }
        } else {
            //Thêm 
            if ((bServiceV != '') && (bServicePricesV != '') && (descriptionV != '')) {
				console.log(formDataInput);
				formDataInput.bservice.banner =  $scope.hinhanh;
                formDataInput.bservice.blocked = false;
                bservicesAPI.create(formDataInput.bservice).then(resp => {
                    let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
                    let bservices = resp;
                    let serviceP = {};
                    serviceP.price = formDataInput.price;
                    serviceP.priceBy = { id: sessionUser };
                    serviceP.bservice = bservices;
                    serviceP.note = "Khởi tạo giá dịch vụ";
                    console.log(serviceP);
                    bservicepricesAPI.create(serviceP).then(resp => {
                        $scope.tab(1);
                        $rootScope.Alert(true, "success", "Thêm Thành Công!", 3);
                        $scope.formBservice = {};
                        $scope.initialize();
                    }).catch(erorr => {
                        alert("Thêm dịch vụ thất bại");
                        console.log("Erorr: ", erorr);
                    });
                }).catch(erorr => {
                    alert("Thêm dịch vụ thất bại");
                    console.log("Erorr: ", erorr);
                });
            }

        }
    }

    //5. reset 
    $scope.reset = function() {
        if ($scope.getAForm()) {
            //reset update
            $scope.formBservice = JSON.parse(localStorage.obj_edit_bservice);
            $scope.formDataEdit = $scope.formBservice;
        } else {
            //reset create
            $scope.formBservice = {};
        }
    }

    //6. delete 
    $scope.delete = function(item) {
        let formDataInput = angular.copy(item);
        formDataInput.bservice.blocked = true;
        bservicesAPI.update(formDataInput.bservice.id, formDataInput.bservice).then(resp => {
            $("#delete-modal").modal("hide");
            localStorage.removeItem('obj_edit_bservice');
            $scope.formBservice = {};
            $scope.formDataEdit = {};
            $rootScope.Alert(true, "danger", "Khóa dịch vụ Thành Công!", 3);
            $scope.tab(1);
            $scope.initialize();
        }).catch(erorr => {
            alert("Khóa dịch vụ thất bại");
            console.log("Erorr: ", erorr);
        });
    }

    $scope.layid = {};
    $scope.setId = function(item) {
        $scope.layid = angular.copy(item);
    }
    
    
    //Image banner
    $scope.imageChanged = async function(files) {
        var data = new FormData();
        data.append('file', files[0]);
       await GlobalAPI.uploadFile('folder', data).then(resp => {
			console.log(resp.data);
            if ($scope.image != '' && $scope.image != undefined && $scope.image != null) {
                let path = resp.data[0];
                console.log("chạy trên ", path)
                $scope.formBservice.bservice.banner = path.slice(-26);
                $scope.$digest();  
            } else {
                let path = resp.data[0];
                console.log("chạy dưới ", path)
                 //$scope.formBservice.bservice.banner = {};
               // $scope.formBservice.bservice.banner = path.slice(-26);
              	 $scope.hinhanh = path.slice(-26);
                $scope.$digest();  
            }
        }).catch(error => {
            alert("Lỗi poster")
            console.log("Error: " + error)
        })
    }

    //format currency
    $scope.currencyFormat = function(money) {
        const config = { style: 'currency', currency: 'VND' }
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
		 let resp = await bservicesAPI.getPageByProperties(NPage, Nitem, data)
		 console.log(resp)
            $scope.listBservice = resp.content;
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
    $scope.changeTabPanelLink = function(alink) {
        //2.1. Số vị trí trang đang sử dụng
        $scope.APage = 1;

        //2.2. Số trang hiện tại
        $scope.NPage = 1;
        //2.3. Key search
        let Tsearch = "";
        //2.4. GO PAGE
        $scope.listActiveAll(alink, Tsearch);
    }

    //6. go Search 
    $scope.goSearchA = function(keysearch) {
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
    $scope.initialize = function() {
        let lActive = 1;
        let Tsearch = "";
        $scope.listActiveAll(lActive, Tsearch);
    }


    $scope.initialize();

        Validator({
            form: '#formBservice',
            formGroupSelector: '.form-group',
            errorSelector: '.form-message',
            invalidSelector: '.invalid',
            rules: [ 
                Validator.isRequired("#bService", 'Vui lòng nhập tên dịch vụ!'),
                Validator.isRequired("#bServicePrices", 'Vui lòng nhập giá dịch vụ!'),
                Validator.isRequired("#description", 'Vui lòng nhập ghi chú!'), 
            ],
            onSubmit: function(data) {
                console.log(data);
            }
        });


});