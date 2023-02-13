app.controller('baidangbian-ctrl', function($scope, $rootScope, $http) {

	const hidecardAPI = new GlobalAPI();
	hidecardAPI.setNamespace(GlobalAPI.namespace.HIDECARD);

	const postAPI = new GlobalAPI();
	postAPI.setNamespace(GlobalAPI.namespace.POST);

	//danh sách hidecard
	$scope.hidecardItems = [];

	//Danh sách post từ hideCard
	$scope.postItems = [];

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
	$scope.listActive = 8;

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

	//Hiển thị trở lại bài đăng đã ẩn
	$scope.restorePost = async function(idHideCard) {
		await hidecardAPI.delete(idHideCard).then(resp => {
			$rootScope.Alert(true, "success", "Bài viết đã hiển thị trở lại!", 3);
			var btn_close = document.querySelector('#btn_restore_close');
			btn_close.click();
			$scope.initialize();
		})
	}

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
			'idUser': sessionUser,
			'status': $scope.listActive,
		}
		let resp = await hidecardAPI.getPageByProperties(NPage, Nitem, data)
		$scope.hidecardItems = resp.content;
		let temp = $scope.hidecardItems;
		for (var i = 0; i < $scope.hidecardItems.length; i++) {
			let respPost = await postAPI.getByProperties({ 'card.id': $scope.hidecardItems[i].card.id });
			temp[i].post = respPost;
		}
		$scope.hidecardItems = temp;
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
		let lActive = 8;
		let Tsearch = "";
		$scope.listActiveAll(lActive, Tsearch);
	}

	$scope.initialize();

});