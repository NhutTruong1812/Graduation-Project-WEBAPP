/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */


/**
 * Author: haotn
 * CodeDate: 2/11/2022
 * Backend: Hiện dữ liệu, phân trang
 */
app.controller('phitreosanpham-ctrl', function($scope, $http, $rootScope, $timeout) {


	/**
	 * 6/11/2022
	 * haotn
	 * Index trang hiện tại
	 */
	$scope.curentPage = 1;

	/**
	 * 6/11/2022
	 * haotn
	 * Dữ liệu từ db
	 */
	$scope.pageData = {};

	/**
	 * 6/11/2022
	 * haotn
	 * Số trang hiển thị trên nút
	 */
	$scope.pages = {
		p1: 1,
		p2: 2,
		p3: 3,
		p4: 4,
		p5: 5
	};



	/**
	 * haotn
	 * Khai báo API
	 */
	const displayfeeAPI = new GlobalAPI();
	displayfeeAPI.setNamespace(GlobalAPI.namespace.DISPLAYFEE);


	//format currency
	$scope.currencyFormat = function(money) {
		const config = { style: 'currency', currency: 'VND' }
		const formated = new Intl.NumberFormat('vi-VN', config).format(money);
		return formated;
	}

	/**
	 * 6/11/2022
	 * haotn
	 * Xóa item trong array
	 */
	Array.prototype.removeItem = function(obj) {
		const index = this.indexOf(obj);
		if (index > -1) { // only splice array when item is found
			this.splice(index, 1); // 2nd parameter means remove one item only
		}
	};


	/*Begin Variable , desscription: biến khởi tạo*/
	//1. tab active được hiển thị hiện tại default tab1 (id: list-tab)
	$scope.currentTab = 1;
	/*End variable */

	/**
	 * 6/11/2022
	 * haotn
	 * clear all .invalid in formgroup when change tab 
	 */
	function clearAllInvalid(formSelector, groupSelector) {
		let form = document.querySelector(formSelector);
		Array.from(form.querySelectorAll(groupSelector)).forEach(function(el) {
			let sp = el.querySelectorAll('span.form-message')[0].textContent = '';
		});
	}

	/*START CODE TAB ,description: Chuyển tab số ntab sang chế độ hiển thị*/
	$scope.tab = function(tabnum) {
		//1. List-tab, 2. Form-tab 
		if (tabnum === 1) {
			clearAllInvalid('#formDisplayservice', '.form-group');
		}
		$scope.currentTab = tabnum;
	}
	/**
	 * haotn
	 * 6/11/2022
	 * Fill data to form
	 */
	function fillFormData(selector, data) {
		let form = document.querySelector(selector);
		let keys = Object.keys(data);
		let keyLength = keys.length;
		for (let i = 0; i < keyLength; i++) {
			if (keys[i] === '$$hashKey') {
				continue;
			}
			let el = form.querySelector(`[name="${keys[i]}"]`);
			el.value = data[keys[i]];
		}
	}

	//2. chọn nút thêm 
	$scope.add = function() {
		$scope.reset();
	}

	/*END CODE TAB*/


	//3. eidit displayfee 
	$scope.edit = function(itemDisplayFee) {
		$scope.tab(2);
		fillFormData("#formDisplayservice", itemDisplayFee)
		// localStorage.obj_edit_displayfee = JSON.stringify($scope.formDisplayFee);
		document.querySelector("#btnDelete").addEventListener('click', async function() {
			deleteItem(itemDisplayFee.id);
			$("#deleteModal").modal("hide");
			$scope.tab(1);
			localStorage.removeItem('obj_edit_displayfee');
			$rootScope.Alert(true, "danger", "Xóa Thành Công!", 3);
		})
	}

	//5. reset displayfee
	$scope.reset = function() {
		let data = {
			id: '',
			priceFrom: '',
			priceTo: '',
			fixedFee: '',
			percentFee: '',
			description: ''
		};
		fillFormData('#formDisplayservice', data);
	}





	/**
	 * haotn
	 * Gọi dữ liệu theo trang
	 */
	$scope.getPage = async function(pagenum) {
		let page = await displayfeeAPI.getPage(pagenum, 5);
		$timeout(function() {
			$scope.pageData = page;
		});
		// let page = await $http.get(`/biglobby/displayfees/?page=${pagenum}&limit=5`).then((res) => {
		//     return res.data;
		// });
	}

	/**
	 * 6/11/2022 
	 * Fix lỗi không lấy được giá trị input
	 * Chưa fix - lỗi trên form vẫn hiện khi chuyển về tab table
	 */

	/**
	 * 6/11/2022
	 * Hàm thêm phí trưng bày
	 */

	async function create(data) {
		let created = await displayfeeAPI.create(data);
		if (created) {
			$scope.tab(1);
			$rootScope.Alert(true, "success", "Thêm Thành Công!", 3);
			$scope.pageData.content.push(created);
			$scope.initialize();
			return true;
		}
		return false;
	}

	/**
	 * 6/11/2022
	 * haotn
	 * update đối tượng
	 */
	async function update(data) {
		let itemsLength = $scope.pageData.content.length;
		for (let i = 0; i < itemsLength; i++) {
			if ($scope.pageData.content[i].id == data.id) {
				if ($scope.pageData.content[i] != data) {
					let updated = await displayfeeAPI.update(data.id, data);
					if (updated) {
						$scope.pageData.content[i] = updated;
						$scope.tab(1);
						$rootScope.Alert(true, "success", "Cập nhật thành công!", 3);
						$scope.initialize();
						return true;
					}
					return false;
				}
				$rootScope.Alert(true, "success", "Không có thay đổi!", 3);
			}
		}
	}

	/**
	 * 6/11/2022
	 * haotn 
	 * Xóa đối tượng
	 */
	async function deleteItem(id) {
		let success = await displayfeeAPI.delete(id);
		if (success) {
			$scope.tab(1);
			let item = $scope.pageData.content.filter(o => o.id === id);
			$scope.pageData.content.removeItem(item);
			$rootScope.Alert(true, "danger", "Xóa Thành Công!", 3);
			$scope.initialize();
		}
	}

	/**
	 * haotn
	 * Phân trang
	 */
	$scope.pageChange = function(num) {
		for (const [key, value] of Object.entries($scope.pages)) {
			const val = value + num;
			$scope.pages[key] = val;
		}
	}
	/**
	 * 6/11/2022
	 * haotn
	 * Khởi tạo sự kiện chuyển trang
	 */
	$scope.initPaging = function() {
		let pages = $scope.pages;
		let btnP1 = document.querySelector("#btnP1");;
		let btnP2 = document.querySelector("#btnP2");
		let btnP3 = document.querySelector("#btnP3");
		let btnP4 = document.querySelector("#btnP4");
		let btnP5 = document.querySelector("#btnP5");
		console.log('add event')
		btnP1.addEventListener("click", async () => {
			await $scope.getPage(pages.p1 - 1);
			$scope.currentPage = pages.p1;
		});

		btnP2.addEventListener('click', async () => {
			await $scope.getPage(pages.p2 - 1);
			$scope.currentPage = pages.p2;
		});

		btnP3.addEventListener('click', async () => {
			await $scope.getPage(pages.p3 - 1);
			$scope.currentPage = pages.p3;
		});

		btnP4.addEventListener('click', async () => {
			await $scope.getPage(pages.p4 - 1);
			$scope.currentPage = pages.p4;
		});

		btnP5.addEventListener('click', async () => {
			await $scope.getPage(pages.p5 - 1);
			$scope.currentPage = pages.p5;
		});

	}

	/**
	 * 6/11/2022
	 * haotn
	 * Lấy dữ liệu từ form
	 */
	function getFormData(selector) {
		return $(selector).serializeArray().reduce(function(obj, item) {
			obj[item.name] = item.value;
			return obj;
		}, {});
	}


	/*END CODE */
	$scope.initialize = async function() {
		$scope.curentPage = 1;
		await $scope.getPage(0);

		$scope.initPaging();
		Validator({
			form: '#formDisplayservice',
			formGroupSelector: '.form-group',
			errorSelector: '.form-message',
			invalidSelector: '.invalid',
			rules: [
				Validator.isRequired("#priceFrom", 'Vui lòng nhập giá bắt đầu!'),
				Validator.isNumber("#priceFrom", "Giá bắt đầu phải là số"),
				Validator.minValue("#priceFrom", 1, "Giá bắt đầu phải lớn hơn 0!"),
				Validator.customRule("#priceFrom", async function() {
					let id = document.querySelector('#id').value;
					let price = document.querySelector('#priceFrom').value;
					price = Number(price);
					let result = await displayfeeAPI.getByProperty('price', price);
					if (result) {
						if (id) {
							if (result.id != id) {
								return false;
							}
						} else {
							return false;
						}
					}
					return true;
				}, "Đã tồn tại phí cho mức giá này!"),
				Validator.isRequired("#priceTo", 'Vui lòng nhập giá kết thúc!'),
				Validator.isNumber("#priceTo", 'Giá kết thúc phải là số!'),
				Validator.minValue("#priceTo", function() {
					let min = document.querySelector('#priceFrom').value;
					return Number(min) + 1;
				}, "Giá kết thúc phải lớn hơn giá bắt đầu!"),
				Validator.customRule("#priceTo", async function() {
					let id = document.querySelector('#id').value;
					let price = document.querySelector('#priceTo').value;
					price = Number(price);
					let result = await displayfeeAPI.getByProperty('price', price);
					if (result) {
						if (id) {
							if (result.id != id && result.priceFrom < price) {
								return false;
							}
						} else {
							return false;
						}
					}
					return true;
				}, "Đã tồn tại phí cho mức giá này!"),
				Validator.isRequired("#FixedFee", 'Vui lòng nhập phí cố định!'),
				Validator.isNumber("#FixedFee", 'Phí cố định phải là số!'),
				Validator.minValue("#FixedFee", 1, "Phí cố định phải lớn hơn 0!"),
				Validator.isRequired("#percentFee", 'Vui lòng nhập phi phần trăm !'),
				Validator.isNumber("#percentFee", 'Phí phần trăm phải là số!'),
				Validator.minValue("#percentFee", 1, "Phí phần trăm phải lớn hơn 0!"),
			],
			onSubmit: async function(data) {
				if (!data.id) {
					data.id = null;
					await create(data);
				} else {
					await update(data);
				}
			}
		});
	}






	// window.onload = async function() {

	//     $scope.initialize();
	// }
	// $scope.initialize();
});