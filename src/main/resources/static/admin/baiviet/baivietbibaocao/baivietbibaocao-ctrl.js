/**
 * CodeDate: V1-02/11/2022,
 * Front-end: V1(chuyển tab, hiện bằng dữ liệu mẫu, nút edit) 
 * 
 */


app.controller('baivietbibaocao-ctrl', function($scope, $rootScope, $http, $rootScope) {

	const postAPI = new GlobalAPI();
	postAPI.setNamespace(GlobalAPI.namespace.POST);

	const cardAPI = new GlobalAPI();
	cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	const reportAPI = new GlobalAPI();
	reportAPI.setNamespace(GlobalAPI.namespace.REPORT);

	$scope.pageData = {};

	$scope.formViewReportCard = {};

	$scope.formPost = {};

	$scope.Atab = 1;

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
		//lấy sp
		this.NPage = this.NPage;
		let data = {
			'key': $scope.searchtext,
			'status': $scope.listActive,
		}
		reportAPI.getPageByProperties(NPage, Nitem, data).then(async resp => {
			$scope.MaxPage = resp.totalPages;
			$scope.getButtonPagination();
			let listPost = [];
			let datas = resp.content;
			for (var i = 0; i < datas.length; i++) {
				let curr = datas[i];
				let postForRP = await postAPI.getCollections(curr.card.id, GlobalAPI.namespace.POST).then(response => {
					let pst = response;
					let exist = listPost.find(o => o.id === pst.id);
					if (exist) {
						curr.post = exist;
					} else {
						curr.post = pst;
						listPost.push(pst);
					}
					return curr;
				});
				datas[i] = postForRP;
			}
			$scope.pageData = datas;
			$scope.$digest();
		});

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
		if (Tsearch != null) {
			let lActive = $scope.listActive = -($scope.listActive);
			$scope.NPage = 1;
			$scope.listActiveAll(lActive, Tsearch)

		} else {
			if ($scope.listActive < 0) {
				$scope.listActive = $scope.listActive * -1;
			}
			let lActive = $scope.listActive;
			let Tsearch = "";
			$scope.listActiveAll(lActive, Tsearch);
		}
	}

	//3. view  
	$scope.view = function(item) {
		$scope.formViewReportCard = angular.copy(item);
	}

	/**END PAGINATION */
	$scope.initialize = function() {
		let lActive = 1;
		let Tsearch = "";
		$scope.listActiveAll(lActive, Tsearch);
	}

	//Duyệt bài viết không vi phạm
	$scope.confirmNoVio = function() {
		$scope.reportCard.status.id = 13;
		reportAPI.update($scope.reportCard.id, $scope.reportCard).then(resp => {
			$rootScope.Alert(true, "success", "Xác nhận bài viết không vi phạm thành công!", 3);
			var btn_close = document.querySelector('#closeFormConfirmNovio');
			btn_close.click();
			$scope.initialize();
		})
	}

	//Duyệt bài viết vi phạm
	$scope.confirmVio = function() {
		$scope.reportCard.status.id = 14;
		if (document.querySelector('#lydo').value == '' || document.querySelector('#lydo').value == null || document.querySelector('#lydo').value == undefined) {
		} else {
			$scope.reportCard.note = document.querySelector('#lydo').value;
		}
		reportAPI.update($scope.reportCard.id, $scope.reportCard).then(resp => {
			cardAPI.getById($scope.reportCard.card.id).then(respCard => {
				let card = respCard;
				card.status.id = 2;
				cardAPI.update(card.id, card).then(resp => {
					$rootScope.Alert(true, "success", "Khóa bài viết vi phạm thành công!", 3);
					var btn_close = document.querySelector('#closeFormConfirmvio');
					btn_close.click();
					$scope.initialize();
				})
			})
		})
	}

	$scope.reportCard = {};
	//get reportCard
	$scope.getRPCard = function(RPCard) {
		$scope.reportCard = RPCard;
		console.log($scope.reportCard)
	}


	$scope.initialize();

});