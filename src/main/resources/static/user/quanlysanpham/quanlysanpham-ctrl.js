app.controller('quanlysanpham-ctrl', function($scope, $rootScope, $http) {
	$scope.shop = [];
	$scope.product = [];
	$scope.resultTabProduct;
	$scope.checkQuantityImageUploadTabProduct = 0;
	$scope.imageWhenSelectMultiTabProduct;
	$scope.checkClickEditimage = 0;
	$scope.checkAddOrEdit = 0;


	window.socket.setOnCard({
		onCreate: function(data) {
		},
		onUpdate: function(data) {
		},
		onDelete: function(data) {
		}
	})

	window.socket.setOnProduct({
		onCreate: function(data) {
		},
		onUpdate: function(data) {
			console.log(data);
		},
		onDelete: function(data) {
		}
	})

	const categoryAPI = new GlobalAPI();
	categoryAPI.setNamespace(GlobalAPI.namespace.CATEGORY);

	const cardAPI = new GlobalAPI();
	cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	const productAPI = new GlobalAPI();
	productAPI.setNamespace(GlobalAPI.namespace.PRODUCT);

	const subbannerAPI = new GlobalAPI();
	subbannerAPI.setNamespace(GlobalAPI.namespace.CARD_SUBBANNER);

	const userAPI = new GlobalAPI();
	userAPI.setNamespace(GlobalAPI.namespace.USER);
	/**
   * Dữ liệu từ db
   */
	$scope.productCard = [];

	// chuyển trạng thái thành hủy bán
	$scope.updateStatus9 = function(product) {
		product.card.status.id = 9;
		cardAPI.update(product.card.id, product.card).then(resp => {
			$rootScope.Alert(true, "success", "Đã ẩn sản phẩm!", 3);
			$scope.initialize();
		})
	}

	// chuyển trạng thái thành sản sàn
	$scope.updateStatus3 = function(product) {
		product.card.status.id = 3;
		cardAPI.update(product.card.id, product.card).then(resp => {
			$rootScope.Alert(true, "success", "Đã hiện sản phẩm!", 3);
			$scope.initialize();
		})
	}

	$scope.showDetail = function(product) {
		let user = product.card.user.id;
		userAPI.getCollections(user, 'shop').then(resp => {
			$scope.shop = resp;
			let itemShow = {
				shop: $scope.shop,
				product: product
			}
			sessionStorage.productobj = JSON.stringify(itemShow);
			location.href = "#!biglobby/chitietsanpham";
		});
	}

	$scope.edit = function(product) {
		$scope.product = angular.copy(product);
		cardAPI.getCollections($scope.product.card.id, 'subbanners').then(resp => {
		})
	}

	//load cbo category
	$scope.itemCates = [];
	$scope.loadCates = function() {
		categoryAPI.getCollections('categoryStatus', '').then(resp => {
			$scope.itemCates = resp;

		})
	}
	$scope.loadCates();

	$scope.changeAddOrEdit = function(num) {
		$scope.checkAddOrEdit = num;
		if ($scope.checkAddOrEdit == 1) {
			$scope.reset();
		}
	}


	//Chọn ảnh tab sản phẩm
	$scope.imageChangedProduct = async function(files) {
		var data = new FormData();
		for (var i = 0; i < files.length; i++) {
			data.append('file', files[i]);
		}
		await GlobalAPI.uploadFile('folder', data).then(resp => {
			let path = resp.data;
			$scope.product.banner = path;
			$scope.checkClickEditimage = 1;
			if ($scope.product.banner.length > 1) {
				$scope.resultTabProduct = $scope.product.banner.length + " ảnh";
				$scope.checkQuantityImageUploadTabProduct = 2;
				$scope.imageWhenSelectMultiTabProduct = $scope.product.banner;
				$scope.$digest();
			} else {
				$scope.resultTabProduct = $scope.product.banner[0].slice(-26);
				$scope.checkQuantityImageUploadTabProduct = 1;
				$scope.$digest();
			}
			document.querySelector(".messageResultBannerProduct").innerHTML = "";
		}).catch(error => {
			console.log("Lỗi poster" + error)
		})
	}


	$scope.updateProduct = function() {
		let product = angular.copy($scope.product);
		let bannerSub = {};
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		if ($scope.checkAddOrEdit == 1) {
			if ($scope.product.product == undefined || $scope.product.product == '' ||
				$scope.product.price == undefined || $scope.product.price == '' ||
				$scope.product.available == undefined || $scope.product.available == '' || $scope.product.banner == undefined || $scope.product.banner == '') {
				if ($scope.product.banner == undefined || $scope.product.banner == '') {
					document.querySelector(".messageResultBannerProduct").innerHTML = "Vui lòng chọn ảnh!";
				}
			} else {
				if ($scope.checkQuantityImageUploadTabProduct > 1) {
					$scope.product.banner = $scope.imageWhenSelectMultiTabProduct[0].slice(-26);
					bannerSub.subBanner = $scope.imageWhenSelectMultiTabProduct.slice(1, $scope.imageWhenSelectMultiTabProduct.length);
				} else {
					$scope.product.banner = $scope.resultTabProduct;
				}
				let obj_card = {};
				obj_card.isProduct = 1;
				obj_card.status = { id: 1 };
				obj_card.hidden = $scope.product.card.hidden;
				obj_card.user = { id: sessionUser };
				obj_card.postAt = new Date();
				cardAPI.create(obj_card).then(resp => {
					let obj_product = {};
					obj_product.product = $scope.product.product;
					obj_product.banner = $scope.product.banner;
					obj_product.available = $scope.product.available;
					obj_product.description = $scope.product.description;
					obj_product.price = $scope.product.price;
					obj_product.pricePercent = 1.0;
					obj_product.category = $scope.product.category;
					obj_product.card = { id: resp.id };
					productAPI.create(obj_product).then(async (resp) => {
						let card = resp.card;
						if ($scope.checkQuantityImageUploadTabProduct > 1) {
							for (var i = 0; i < bannerSub.subBanner.length; i++) {
								let subbanner = {};
								subbanner.card = card;
								subbanner.subbanner = bannerSub.subBanner[i].slice(-26);
								await subbannerAPI.create(subbanner);
							}
						}
						$rootScope.Alert(true, "success", "Đăng sản phẩm thành công!", 3);
						$("#postModel").modal("hide");
						$scope.reset();
						$scope.initialize();
					})

				})
			}

		} else {
			if ($scope.product.product == undefined || $scope.product.product == '' ||
				$scope.product.price == undefined || $scope.product.price == '' ||
				$scope.product.available == undefined || $scope.product.available == '' || $scope.product.banner == undefined || $scope.product.banner == '') {
				if ($scope.product.banner == undefined || $scope.product.banner == '') {
					document.querySelector(".messageResultBannerProduct").innerHTML = "Vui lòng chọn ảnh!";
				}
			} else {
				if ($scope.checkClickEditimage == 1) {
					if ($scope.checkQuantityImageUploadTabProduct > 1) {
						product.banner = $scope.imageWhenSelectMultiTabProduct[0].slice(-26);
						bannerSub.subBanner = $scope.imageWhenSelectMultiTabProduct.slice(1, $scope.imageWhenSelectMultiTabProduct.length);
					} else {
						product.banner = $scope.resultTabProduct;
					}
				}
				productAPI.update(product.id, product).then(resp => {
					cardAPI.update(product.card.id, product.card).then(res => {
						let card = res;
						cardAPI.getCollections(product.card.id, 'subbanners').then(async (respSub) => {
							let subBanner = respSub;
							if (subBanner != undefined && subBanner != '') {
								subBanner.forEach(function(item) {
									subbannerAPI.delete(item.id).then(async (respSubD) => {
										for (var i = 0; i < bannerSub.subBanner.length; i++) {
											item.subbanner = bannerSub.subBanner[i].slice(-26);
											await subbannerAPI.create(item).then(respSubB => {
											})
										}
									})
								})
							} else {
								for (var i = 0; i < bannerSub.subBanner.length; i++) {
									let addSubBanner = {};
									addSubBanner.card = card;
									addSubBanner.subbanner = bannerSub.subBanner[i].slice(-26);
									await subbannerAPI.create(addSubBanner).then(respSubB => {
									})
								}
							}


						})
					})
					$rootScope.Alert(true, "success", "Sửa sản phẩm thành công!", 3);
					$("#postModel").modal("hide");
					$scope.reset();
					$scope.initialize();
				}).catch(error => {
					console.log("Lỗi sửa sản phẩm" + error)
				})
			}
		}
	}

	$scope.reset = function() {
		$scope.product = [];
		$scope.resultTabProduct = '';
		document.querySelector(".messageResultBannerProduct").innerHTML = "";
		document.getElementById('offerCategory__inp').value = '';
	}

	$scope.offerCategory = function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let cate = document.getElementById('offerCategory__inp').value;
		let message = document.getElementById('message__category');
		if (cate != '' && cate != undefined) {
			let category = {};
			category.category = cate;
			category.description = cate;
			category.blocked = false;
			category.addBy = { id: sessionUser };
			category.status = { id: 1 };
			category.addDate = new Date();
			categoryAPI.create(category).then(resp => {
				$rootScope.Alert(true, "success", "Đề xuất thành công!", 3);
				$scope.reset();
				$("#modalCreateFeedd").modal("hide");
			})
		} else {
			message.innerHTML = 'Vui lòng nhập thể loại bạn muốn';
		}

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

	/*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
	$scope.tab = function(tabnum) {
		//1. List-tab, 2. Form-tab 
		$scope.Atab = tabnum;
	}
	/*END CODE TAB*/

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
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		//lấy sp
		this.NPage = this.NPage;
		let data = {
			'key': $scope.searchtext,
			'user': sessionUser,
			'status': $scope.listActive,
		}
		let resp = await productAPI.getPageByProperties(NPage, Nitem, data)
		$scope.productCard = resp.content;
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
	$scope.goSearchA = function(event) {
		let keysearch = event.currentTarget;
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
		form: '#formCard',
		formGroupSelector: '.form-group',
		errorSelector: '.form-message',
		invalidSelector: '.invalid',
		rules: [
			Validator.isRequired("#productName", 'Vui lòng nhập tên sản phẩm!'),
			Validator.isRequired("#price", 'Vui lòng nhập giá!'),
			Validator.isRequired("#available", 'Vui lòng nhập số lượng!'),
		],
		onSubmit: function(data) { }
	});
});
