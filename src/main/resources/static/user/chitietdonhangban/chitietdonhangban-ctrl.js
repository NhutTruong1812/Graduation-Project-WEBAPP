app.controller('chitietdonhangban-ctrl', function($scope, $rootScope, $http) {

	$scope.items = [];
	$scope.form = {};
	$scope.read = [];
	$scope.sumPrice;

	const orderAPI = new GlobalAPI();
	orderAPI.setNamespace(GlobalAPI.namespace.ORDER);

	const orderdetailsAPI = new GlobalAPI();
	orderdetailsAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);

	//hàm xử lý định dạng tiền VNĐ
	$scope.currencyFormat = function(bcoin) {
		const config = { style: 'currency', currency: 'VND' }
		const formated = new Intl.NumberFormat('vi-VN', config).format(bcoin);
		return formated;
	}


	$scope.loadForm = function() {
		let itemSession = JSON.parse(sessionStorage.getItem("orderUserSaler"));
		let sessionUser = itemSession.order;
		let sessionSumPrice = itemSession.sumPrice;
		$scope.read = sessionUser;
		$scope.sumPrice = $scope.currencyFormat(sessionSumPrice);

	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}

	$scope.initialize = async function() {
		$scope.loadForm();
		$scope.edit($scope.read);
		let resp = await orderAPI.getCollections($scope.read.id, 'details');
		$scope.items = resp;
		$scope.$digest();
	}

	//Xử lý nút hủy giao dịch
	$scope.cancelOrder = async function(order) {
		order.status.id = 9;
		await orderAPI.update(order.id, order).then(resp => {
			let item = {
				order: resp
			}
			sessionStorage.setItem('orderUserSalerNew', JSON.stringify(item));
			let setorderuserNew = JSON.parse(sessionStorage.getItem("orderUserSalerNew"));
			$rootScope.Alert(true, "success", "Đã hủy đơn hàng", 3);
			$scope.edit(setorderuserNew.order);
			$scope.$digest();
		})
	}

	$scope.currencyFormat = function(money) {
		const config = { style: 'currency', currency: 'VND' }
		const formated = new Intl.NumberFormat('vi-VN', config).format(money);
		return formated;
	}



	$scope.initialize();
});