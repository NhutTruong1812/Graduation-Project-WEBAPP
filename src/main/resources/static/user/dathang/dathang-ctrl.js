/* 
 * Code Cart after comment 5
 * Date: 26-10-2022
 * wait: Đặt hàng, Thêm giỏ hàng
 * done: Xem giỏ hàng, thay đổi số lượng , xóa ra khỏi giỏ hàng
 */

app.controller('dathang-ctrl', function($scope, $rootScope, $http) {

	window.socket.setOnOrder({
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

	$scope.orderdetailAPI = new GlobalAPI();
	$scope.orderdetailAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);

	$scope.orderAPI = new GlobalAPI();
	$scope.orderAPI.setNamespace(GlobalAPI.namespace.ORDER);

	$scope.cartAPI = new GlobalAPI();
	$scope.cartAPI.setNamespace(GlobalAPI.namespace.CART);

	$scope.addressAPI = new GlobalAPI();
	$scope.addressAPI.setNamespace(GlobalAPI.namespace.ADDRESS);

	//danh sách cart
	$scope.cartitems = [];

	//danh sách hiển thị
	$scope.listshop = [];

	//Tổng giá
	$scope.sumPrice = 0;

	//tài khoản người đăng nhập
	$scope.userlogin = sessionStorage.usersession;
	$scope.userlogin_obj = {}

	//danh sách chọn
	$scope.listselected = [];

	//danh sách địa chỉ
	$scope.listaddress = [];

	//address default
	$scope.addressdefault;

	//status
	$scope.status_obj = {};

	//form address
	$scope.formaddress = {};

	$scope.formcheckout = {}

	//tabpane
	$scope.Atab = 1;

	$scope.tab = function(tabnum) {
		$scope.Atab = tabnum;
	}

	//$(".nav-tabs a:eq(1)").tab('show')

	/*BEGIN CODE ADDRESS */
	//lấy dữ liệu address
	$scope.getaddress = async function() {
		//$http.get("/biglobby/users/"+$scope.userlogin+"/addresses")
		$scope.listaddress = await $scope.userAPI.getCollections($scope.userlogin, "addresses")
		console.log($scope.listaddress)
		$scope.addressdefault = $scope.listaddress[0];
		$scope.$digest();
	}

	//refersh address
	$scope.refershaddress = async function() {
		$scope.formaddress = {};
		await $scope.getaddress();
	}

	//xóa address
	$scope.deleteaddress = async function(address) {
		await $scope.addressAPI.delete(address.id)
			.catch(Error => {
				//alert("Lỗi xóa địa chỉ!")
				$rootScope.Alert(true, "danger", "Không thể xóa địa chỉ", 3);
			})
		await $scope.refershaddress();
		//alert('Đã xóa địa chỉ') 
		$rootScope.Alert(true, "success", "Đã xóa địa chỉ!", 3);
	}

	$scope.checkFrom = function() {
		let ad = angular.copy($scope.formaddress);
		if (ad.fullname == null || ad.phonenum == null || ad.address == null || ad.fullname == "" || ad.phonenum == "" || ad.address == "") {
			return false;
		} else {
			return true;
		}


	}


	//thêm hoặc sửa address
	$scope.createaddress = async function(formid) {
		if ($scope.checkFrom()) {
			if (formid != null) {
				let ad = angular.copy($scope.formaddress);
				let obj_address = {}

				obj_address.user = { id: JSON.parse(sessionStorage.usersession) };
				obj_address.fullname = ad.fullname,
					obj_address.phonenum = ad.phonenum,
					obj_address.address = ad.address

				await $scope.addressAPI.update(formid, obj_address)

				//alert("Đã cập nhật địa chỉ");
				$rootScope.Alert(true, "success", "Đã cập nhật địa chỉ", 3);
				$scope.tab(1);
				await $scope.refershaddress();

			} else {
				let ad = angular.copy($scope.formaddress);
				let obj_address = {}
				obj_address.user = { id: JSON.parse(sessionStorage.usersession) };
				obj_address.fullname = ad.fullname;
				obj_address.phonenum = ad.phonenum;
				obj_address.address = ad.address;
				//console.log(obj_address); 
				await $scope.addressAPI.create(obj_address)
					.catch(Error => {
						//alert(Error);
						//alert("Thêm Thất Bại!!!!!!");
						$rootScope.Alert(true, "danger", "Thêm địa chỉ thất bại", 3);
					})
				//alert("Đã thêm địa chỉ")
				$rootScope.Alert(true, "success", "Thêm địa chỉ thành công", 3);
				$scope.tab(1);
				await $scope.refershaddress();
			}
		} else {
			$rootScope.Alert(true, "warning", "Vui lòng kiểm tra lại thông tin đặt hàng! thông tin nhận hàng không được để trống", 3);
		}
	}

	//edit address 
	$scope.editaddress = function(addr) {
		$scope.tab(2);
		$scope.formaddress = addr;
	}

	//reload address
	$scope.reloadformaddress = function() {
		$scope.formaddress = {};
	}

	//chọn address
	$scope.chooseaddress = function(address) {
		$scope.addressdefault = address;
		//console.log($scope.addressdefault)
	}
	/*END CODE ADDRESS */


	//lấy dối tượng đăng nhập
	/*$scope.getUserLogin= function(){
	  $http.get("/biglobby/users/"+$scope.userlogin)
	  .then(resp=>{
		  $scope.userlogin_obj = resp.data; 
	  })
  }
	*/
	//lấy status chờ xác nhận
	/*$scope.getStatus=function(){
	  $http.get("/biglobby/status/5")
	  .then(resp=>{
		  $scope.status_obj = resp.data;
	  })
  }
	*/
	// $scope.getaddress();

	//Hàm tạo
	$scope.initialize = function() {
		let cart = JSON.parse(sessionStorage.listcheckout_obj);
		let sum = 0;
		cart.map(c => {
			c.cart_date = new Date(c.cart_date);
			if ($scope.listshop) {
				let checkexist = false;
				for (let i = 0; i < $scope.listshop.length; i++) {
					if ($scope.listshop[i].id === c.product.card.user.id) {
						$scope.listshop[i].product.push(c);
						checkexist = true;
					}
				}

				if (!checkexist) {
					let a = {
						id: c.product.card.user.id,
						user: c.product.card.user,
						product: [c]
					}
					$scope.listshop.push(a);
				}
			} else {
				let a = {
					id: c.product.card.user.id,
					user: c.product.card.user,
					product: [c]
				}
				$scope.listshop.push(a);

			}

		});

		this.cartitems = cart;

		this.sumPrice = this.sumPriceCart(cart);

		if ($scope.listshop.length <= 0) {
			//alert("Không có sản phẩm nào để đặt hàng!")
			$rootScope.Alert(true, "warning", "Không có sản phẩm nào để thanh toán", 3);
			location.href = "#!/biglobby/giohang";
		}
	};

	//RefreshAll
	$scope.refreshAll = async function() {
		//Dọn sạch cart
		$scope.cartitems = [];
		//Dọn sạch list hiển thị
		$scope.listshop = [];

		//Dọn sạch tổng giá
		$scope.sumPrice = 0;

		//Dọn sạch danh sách địa chỉ
		$scope.listaddess = [];

		//Lấy lại dữ liệu
		$scope.initialize();

		//Lấy địa chỉ
		await $scope.getaddress();
	}


	//Xóa cart
	$scope.deleteCart = async function(cart) {
		await $scope.cartAPI.delete(cart.id)
			.catch(Error => {
				//alert("Không thể xóa ra khỏi giỏ hàng")
				$rootScope.Alert(true, "warning", "Không thể xóa ra khỏi giỏ hàng", 3);
			})

		let ckcart = JSON.parse(sessionStorage.listcheckout_obj);
		for (let i = 0; i < ckcart.length; i++) {
			if (ckcart[i].id == cart.id) {
				console.log(ckcart[i].id + " - " + cart.id);
				ckcart.splice(i, 1);
			}
		}
		console.log("LISt PRESENT")
		console.log(ckcart)
		sessionStorage.listcheckout_obj = JSON.stringify(ckcart);
		await $scope.refreshAll();
	}

	//Đổi số lượng
	$scope.changeQuantity = async function(cart) {
		let qty = $('#quantity' + cart.id).val();
		if (qty <= 0) {
			qty = 1;
		} else if (qty > cart.product.available) {
			qty = cart.product.available;
			alert("Sản phẩm còn lại " + qty);
		} else {
			qty = qty;
		}

		await $scope.cartAPI.put(cart.id, qty)
			.catch(Error => {
				//alert("Lỗi Thay đổi số lượng!")
				$rootScope.Alert(true, "danger", "lỗi thay đổi số lượng", 3);
			})

		$('#quantity' + cart.id).val(qty);
		let ckcart = JSON.parse(sessionStorage.listcheckout_obj);
		angular.forEach(ckcart, function(item) {
			let idx = ckcart.indexOf(item);
			if (idx > -1) {

				ckcart.splice(idx, 1)

				ckcart.push(item);

				item.quantity = qty;
			}
		});
		sessionStorage.listcheckout_obj = JSON.stringify(ckcart);
	}

	//Tổng tiền giỏ hàng
	$scope.sumPriceCart = function(cart) {
		$scope.cartitems = angular.copy(cart);
		let sum = 0;
		cart.map(c => {
			let p = c.product;
			sum += (p.price * c.quantity);
		});
		return sum;
	}

	//Kiểm tra input nhập
	$scope.checkDataCheckout = function() {
		// console.log($scope.cartitems.length)
		if ($scope.listshop.length <= 0) {
			return false;
		}
		//console.log($scope.addressdefault); 
		if ($scope.addressdefault == null) {
			return false;
		}

		return true;
	}

	//đặt hàng
	$scope.checkout = async function() {
		if ($scope.checkDataCheckout()) {
			$scope.listshop.map(c => {
				//begin map
				let order_obj = {
					buyer: { id: JSON.parse(sessionStorage.usersession) },
					fullname: $scope.addressdefault.fullname,
					phonenum: $scope.addressdefault.phonenum,
					address: $scope.addressdefault.address,
					addDate: new Date(),
					noteBuyer: $scope.formcheckout.notebuyer,
					noteSaler: null,
					status: { id: 5 },
					saler: c.user
				}

				//console.log(order_obj) 
				$scope.orderAPI.create(order_obj)
					.then(resp => {
						let orderadd_obj = resp;
						c.product.map(cp => {
							let orderdetail_obj = {
								order: orderadd_obj,
								product: cp.product,
								quantity: cp.quantity,
								price: cp.product.price
							}
							//console.log(orderdetail_obj) 
							$scope.orderdetailAPI.create(orderdetail_obj)
								.then(resp => {
									console.log("Thêm odt ok")
									//xóa cart 
									$scope.cartAPI.delete(cp.id)
										.catch(Error => {
											console.log("xóa Cart tạch")
										})
									//alert("Thanh Toán Thành Công! Vui lòng chờ trạng thái đơn hàng để thanh toán!")
									sessionStorage.removeItem('listcheckout_obj');
									location.href = "#!/biglobby/lichsumua"
									$scope.refreshAll();

								})
								.catch(Error => {
									console.log("thêm odt tạch")
								})
						})
					})
					.catch(Error => {
						console.log("Thanh toán tạch");
					})

				//end map
			})
			$rootScope.Alert(true, "success", "Đặt hàng thành công! Bạn có thể kiểm tra đơn hàng trong phần Lịch Sử Mua!", 3);
		} else {
			console.log("stop checkout")
		}
	}

	$scope.refreshAll();

});
