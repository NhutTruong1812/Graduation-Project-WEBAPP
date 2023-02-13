/* 
 * Code shopdetail
 * Date: 16-11-2022
 * wait: Xem chi tiết, thêm giỏ hàng, đặt hàng
 * done: 
 */

app.controller('chitietsanpham-ctrl', function($scope, $rootScope, $http) {

	window.socket.setOnProduct({
		onCreate: function(data) {
			//console.log("on product create")
			//console.log(data)
		},
		onUpdate: function(data) {
			//console.log("on product update")
			//console.log(data) 
			if ($scope.sanpham.id == data.id) {
				//console.log($scope.items[i]); 
				// sản phẩm bị ẩn không công khai
				$scope.sanpham = data;
				let itemShow = {
					shop: $scope.shop,
					product: data
				}
				sessionStorage.productobj = JSON.stringify(itemShow);
				$scope.$digest();

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
			if (data.isProduct === true) {
				if ($scope.sanpham.card.id == data.id) {
					//console.log($scope.items[i]);
					//sản phẩm bị vi phạm 
					if (data.status.id === 2) {
						//alert('Sản phẩm này không tồn tại!');
						$rootScope.Alert(true, "danger", "Sản phẩm này không tồn tại!!!", 3);
						location.href = "#!biglobby/cuahang"
					}
					// sản phẩm bị ẩn không công khai
					else if (data.hidden === true) {
						//alert('Sản phẩm này không công khai!');
						$rootScope.Alert(true, "danger", "Sản phẩm này không công khai!!", 3);
						location.href = "#!biglobby/cuahang"
					}
					// trường hợp khác
					else {
						console.log("no work")
					}
				}

			}
		},
		onDelete: function(data) {
			//hoàn thành realtime
			if (data.isProduct === true) {
				if ($scope.sanpham.card.id == data.id) {
					alert("Sản phẩm này không tồn tại!")
					location.href = "#!biglobby/cuahang"
				}
			}
			// hoàn thành real time
		}
	})

	window.socket.setOnReport({
		onCreate: function(data) {

		},
		onUpdate: function(data) {

		},
		onDelete: function(data) {

		}
	})

	$scope.cartAPI = new GlobalAPI();
	$scope.cartAPI.setNamespace(GlobalAPI.namespace.CART);

	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.loveCardAPI = new GlobalAPI();
	$scope.loveCardAPI.setNamespace(GlobalAPI.namespace.LOVECARD);

	$scope.problemAPI = new GlobalAPI();
	$scope.problemAPI.setNamespace(GlobalAPI.namespace.PROBLEM);

	$scope.hidecardAPI = new GlobalAPI();
	$scope.hidecardAPI.setNamespace(GlobalAPI.namespace.HIDECARD);

	$scope.reportcardAPI = new GlobalAPI();
	$scope.reportcardAPI.setNamespace(GlobalAPI.namespace.REPORT);

	$scope.productAPI = new GlobalAPI();
	$scope.productAPI.setNamespace(GlobalAPI.namespace.PRODUCT);

	$scope.sanpham = {};

	$scope.shop = {};

	$scope.listcheckout = [];

	$scope.usersession_obj = {}
	$scope.usersession = JSON.parse(sessionStorage.usersession);

	$scope.formproduct = 1;

	$scope.listCarts = [];
	//list vấn đề vi phạm
	$scope.selectproblem = [];

	$scope.listProblem = [];

	$scope.listProductShop = [];

	let messErr = "";

	$scope.initialize = async function() {
		let showitem = JSON.parse(sessionStorage.productobj);
		$scope.sanpham = showitem.product;
		$scope.shop = showitem.shop;
		$scope.usersession_obj = await $scope.userAPI.getById($scope.usersession)
		await $scope.loadProblem();
		await $scope.getProductOfShop();
		$scope.$digest();
	}

	//Đổi số lượng
	$scope.changeQuantity = function(sp) {
		let qty = angular.copy($scope.formproduct);

		if (qty <= 0) {
			qty = 1;
		} else if (qty > sp.available) {
			qty = sp.available;
			//alert("Sản phẩm còn lại " + qty);
			//messErr = ;
			$rootScope.Alert(true, "danger", "Sản phẩm còn lại " + qty, 3);
		} else {
			qty = qty;
			messErr = "";
		}

		$scope.formproduct = qty;
		$('#quantity' + sp.id).val(qty);
	}

	$scope.createCart = async function() {
		let cart = {
			user: $scope.usersession_obj,
			product: $scope.sanpham,
			quantity: $scope.formproduct,
			price: $scope.sanpham.price,
			cartDate: new Date()
		}
		await $scope.cartAPI.create(cart)
			.catch(Error => {
				//alert('Thêm giỏ hàng thất bại')
				$rootScope.Alert(true, "danger", "Thêm giỏ hàng thất bại!", 3);
			})
		//alert('Thêm vào giỏ hàng thành công!')
		$rootScope.Alert(true, "success", "Thêm vào giỏ hàng thành công!", 3);
		location.href="#!/biglobby/giohang";

	}

	$scope.fromproductcheckout = async function() {
		let cart = {
			user: $scope.usersession_obj,
			product: $scope.sanpham,
			quantity: $scope.formproduct,
			price: $scope.sanpham.price,
			cartDate: new Date()
		}

		let obj_cart_add = await $scope.cartAPI.create(cart)
		$scope.listcheckout.push(obj_cart_add);
		sessionStorage.listcheckout_obj = JSON.stringify($scope.listcheckout);
		location.href = "#!/biglobby/dathang"
	}

	//11
	$scope.showShopDetail = function(idShop) {
		localStorage.idShopDetail = JSON.stringify(idShop);
		location.href = "#!biglobby/xembaidangcuahangkhac";
	}

	/**PROBLEM */
	//load problem
	$scope.loadProblem = async function() {
		$scope.listProblem = await $scope.problemAPI.getAll()
			.catch(Error => {
				$scope.listProblem = [];
			})
		console.log($scope.listProblem)
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
		//alert("Báo cáo đã được gửi với bộ phận quản trị");
		//messErr = "Báo cáo đã được gửi với bộ phận quản trị";
		$rootScope.Alert(true, "danger", "Báo cáo đã được gửi với bộ phận quản trị!", 3);
		$("#exampleModalReport").modal("hide");

	}

	$scope.hidenProduct = async function(product_obj) {
		let obj_hide = {
			user: { id: JSON.parse(sessionStorage.usersession) },
			card: product_obj.card,
			hideDate: new Date(),
		}
		await $scope.hidecardAPI.create(obj_hide)
		//await $scope.goPage(1); 
		//alert("Đã ẩn sản phẩm, sản phẩm này sẽ không hiện trên giỏ hàng của bạn nữa!") 
		$rootScope.Alert(true, "danger", "Đã ẩn sản phẩm, sản phẩm này sẽ không hiện trên giỏ hàng của bạn nữa!", 3);
		location.href = "#!biglobby/cuahang";
	}

	//sản phẩm tương tự
	//2. Truy vấn page
	$scope.getProductOfShop = async function() {
		//lấy sp
		//Danh sách hiển thị
		$scope.listProductShop = []
		let data_get = await $scope.productAPI.getPageByProperties(1, 6, { 'pagewhere': 5, 'key': '', 'user': $scope.shop.user.id });
		let list = data_get.content;
		//console.log("SP tuonwh tự")
		//console.log($scope.shop.user.id)
		//console.log(list)
		//lưu danh sách hiển thị
		list.map(p => {
			if (p.card.user.id === $scope.shop.user.id) {
				let itemShow = {
					shop: $scope.s,
					product: p
				}
				$scope.listProductShop.push(itemShow);
			}
		});
		$scope.$digest();
	}


	$scope.initialize();
});
