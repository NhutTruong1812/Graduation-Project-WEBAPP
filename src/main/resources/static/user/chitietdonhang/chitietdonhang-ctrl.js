app.controller('chitietdonhang-ctrl', function($scope, $rootScope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.read = [];
	$scope.sumPrice;

	const orderAPI = new GlobalAPI();
	orderAPI.setNamespace(GlobalAPI.namespace.ORDER);

	const orderdetailsAPI = new GlobalAPI();
	orderdetailsAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);

	//format currency
	$scope.currencyFormat = function(money) {
		const config = { style: 'currency', currency: 'VND' }
		const formated = new Intl.NumberFormat('vi-VN', config).format(money);
		return formated;
	}

	$scope.loadForm = function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("orderuser"));
		let sessionSumPrice = sessionUser.sumPrice;
		$scope.read = sessionUser;
		console.log(sessionUser)
		$scope.sumPrice = $scope.currencyFormat(sessionSumPrice);
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}



	$scope.initialize = async function() {
		$scope.loadForm();
		$scope.edit($scope.read);
		console.log($scope.read.order.id)
		let resp = await orderAPI.getCollections($scope.read.order.id, 'details');
		console.log(resp)
		$scope.items = resp;
		$scope.$digest();
	}

	$scope.updateStatus = async function(order) {
		console.log(order)
		order.status.id = 8;
		await orderAPI.update(order.id, order).then(resp => {
			console.log(resp);
			let item = {
				order: resp
			}
			sessionStorage.setItem('orderuserNew', JSON.stringify(item));
			let setorderuserNew = JSON.parse(sessionStorage.getItem("orderuserNew"));
			$rootScope.Alert(true, "success", "Đã hủy đơn hàng", 3);
			$scope.edit(setorderuserNew);
			$scope.$digest();
		})
	}


	$scope.initialize();
});