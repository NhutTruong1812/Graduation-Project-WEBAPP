app.controller('quanlythongke-ctrl', function($scope, $http) {
	$scope.formStatistical = [];
	$scope.formStatisticalDetails = {};
	$scope.sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
	$scope.checkClickStatisticalTab = 0;

	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);


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
	$scope.Nitem = 2;

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

	/**BEGIN PAGINATION */

	/**  
		* 5: Đang xác nhận
		* 6: Đồng ý giao dịch
		* 7: Thành công
		* 8: Hủy do người mua
		* 9: Hủy do người bán
		//SET 
		-X: tìm kiếm
		8: tất cả
		9: Đang xác nhận
		10: đang giao dịch
		11: Chờ xác nhận
		12: đã hủy
	*/

	//1. Hàm Danh sách list
	$scope.getButtonPagination = function() {
		$scope.listButton = [];
		for (let i = 1; i <= $scope.MaxPage; i++) {
			$scope.listButton.push(i);
		}
		return $scope.listButton;
	}


	//2. Truy vấn page
	$scope.queryPage = function(NPage, Nitem) {
		let startDate = document.querySelector('#startDate').value;
		let endDate = document.querySelector('#endDate').value;
		if (startDate == '' || endDate == '') {
			startDate = new Date(new Date().getTime() - (1000 * 60 * 60 * 24 * 365)).toISOString().slice(0, 10);
			endDate = new Date().toISOString().slice(0, 10);
		}
		let data = {
			'id': $scope.sessionUser,
			'startDate': startDate,
			'endDate': endDate,
			'status': 1
		}
		$scope.userAPI.getPageByProperties(NPage, Nitem, data).then(resp => {
			$scope.formStatistical = resp.content[0];
			
			let data2 = {
				'id': $scope.sessionUser,
				'startDate': startDate,
				'endDate': endDate,
				'status': $scope.listActive
			}
			$scope.userAPI.getPageByProperties(NPage, Nitem, data2).then(resp2 => {
				$scope.formStatisticalDetails = resp2.content;
				$scope.MaxPage = resp2.totalPages;
				$scope.getButtonPagination();
				$scope.$digest();
			})

		})
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
		$scope.checkClickStatisticalTab = alink;
		//2.1. Số vị trí trang đang sử dụng
		$scope.APage = 1;

		//2.2. Số trang hiện tại
		$scope.NPage = 1;
		//2.3. Key search
		let Tsearch = "";
		//2.4. GO PAGE
		$scope.listActiveAll(alink, Tsearch);
		
	}


	$scope.initialize = function() {
		$scope.checkClickStatisticalTab = 0;
		let lActive = 2;
		let Tsearch = "";
		$scope.listActiveAll(lActive, Tsearch);
	}

	$scope.initialize();
});
