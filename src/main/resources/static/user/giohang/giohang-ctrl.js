/* 
 * Code Cart after comment 5
 * Date: 26-10-2022
 * wait: Đặt hàng, Thêm giỏ hàng
 * done: Xem giỏ hàng, thay đổi số lượng , xóa ra khỏi giỏ hàng
 */

app.controller('giohang-ctrl', function($scope, $rootScope, $http) {
	
	window.socket.setOnProduct({
        onCreate: function(data) {
           	 
        },
        onUpdate: function(data) {    
			for (let i = 0; i < $scope.listshop.length; i++) {

				if ($scope.listshop[i].product.id == data.id) {
					//console.log($scope.items[i]); 
					// sản phẩm bị ẩn không công khai
					 $scope.listshop[i].product = data; 
					check = true;
					$scope.$digest();
					break;
				}

			} 
        },
        onDelete: function(data) { 
        }
    })

    window.socket.setOnCard({
        onCreate: function(data) {
            
        },
        onUpdate: async function(data) { 
			//bắt đâu real time
	        if(data.isProduct === true){ 
				console.log($scope.listshop.length)
				for(let i = 0; i <  $scope.listshop.length; i++){ 
					for(let j = 0; j <  $scope.listshop[i].product.length; j++){
						if($scope.listshop[i].product[j].product.card.id == data.id){ 
						//console.log($scope.items[i]);
						console.log($scope.listshop[i].product[j])
						//sản phẩm bị vi phạm 
						console.log($scope.listshop[i].product[j].id)
						if(data.status.id === 2){
							console.log($scope.listshop[i].product[j].id)
							await $scope.cartAPI.delete($scope.listshop[i].product[j].id)
							$scope.listshop.splice(i, 1);
							
						}
						// sản phẩm bị ẩn không công khai
						else if (data.hidden === true){ 
							console.log($scope.listshop[i].product[j].id) 
							await $scope.cartAPI.delete($scope.listshop[i].product[j].id)
							$scope.listshop.splice(i, 1 );
						} 
						// trường hợp khác
						else{
							console.log("no work")
						} 
						
						await $scope.refreshAll(); 
						$scope.$digest();
						break;
					}
					} 
				 
				} 
			}
			//console.log($scope.items) 
			$scope.$digest();
			//hoàn thành real time
           
        },
        onDelete: function(data) {
			//hoàn thành realtime
        	if(data.isProduct === true){
				let check = false;
				for(let i = 0; i <  $scope.items.length; i++){
					if($scope.items[i].product.card.id == data.id){
						$scope.items.splice(i, 1); 
						check = true;
						break;
					}
				}
				
			}
			// hoàn thành real time
        }
    }) 
	
	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.cartAPI = new GlobalAPI();
	$scope.cartAPI.setNamespace(GlobalAPI.namespace.CART);

	//danh sách cart
	$scope.cartitems = []; 
 	//danh sách hiển thị
 	$scope.listshop = [];
 	//Tổng giá
 	$scope.sumPrice = 0; 
 	//tài khoản người đăng nhập
 	$scope.userlogin = sessionStorage.usersession;
 	 
 	$scope.listSelected =[];
 	 
 	//Hàm tạo
	$scope.initialize =async function() {   
		let cart = await $scope.userAPI.getCollections($scope.userlogin, "carts")
		//sessionStorage.usersession = JSON.stringify(cart[0].product.card.user);
		$scope.cartitems = cart;
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
			$scope.$digest();
		});

		//console.log($scope.listshop);

		this.cartitems = cart;
		this.sumPrice = this.sumPriceCart(cart);       
		
		$scope.$digest();
	};
   	
 
   	//RefreshAll
   	$scope.refreshAll = async function(){
		//Dọn sạch cart
		$scope.cartitems = []; 
	 	//Dọn sạch list hiển thị
	 	$scope.listshop = [];
	 	//Dọn sạch tổng giá
	 	$scope.sumPrice = 0; 
	 	//Lấy lại dữ liệu
	 	await $scope.initialize();  
	 	$scope.$digest();
	}
   	
   	
   	//Xóa cart
	$scope.deleteCart = async function(cart) {
		await $scope.cartAPI.delete(cart.id)
		.catch(Error => {
			$rootScope.Alert(true, "danger", "Không thể xóa ra khỏi giỏ hàng!", 3);
			//alert("Không thể xóa ra khỏi giỏ hàng")
		})
		await $scope.refreshAll();
	}
 
   	//Đổi số lượng
   	$scope.changeQuantity = async function(cart){
		let qty = $('#quantity'+cart.id).val(); 
 
		if(qty <= 0 ){
			qty = 1;
			alert("Sản phẩm còn nhỏ hơn 0 ");  
		}else if(qty > cart.product.available){
			qty = cart.product.available;
			//alert("Sản phẩm còn lại " + qty);
			$rootScope.Alert(true, "danger","Sản phẩm còn lại " + qty, 3);
		}else{
			cart.quantity = qty; 	 
		} 
 
		await $scope.cartAPI.update(cart.id,qty)	
		.catch(Error => {
			//alert("Lỗi Thay đổi số lượng!"
			$rootScope.Alert(true, "danger","Lỗi Thay đổi số lượng", 3);
		})
		await $scope.refreshAll();
		$('#quantity'+cart.id).val(qty);
	}
   	
   	//Tổng tiền giỏ hàng 
 
	$scope.sumPriceCart = function (cart){ 
		$scope.cartitems = angular.copy(cart);
		let sum = 0;
		cart.map(c => { 
				let p =c.product; 
			 	sum += (p.price * c.quantity); 
			});  
		return sum;
	}
 
	
	$scope.selectCheckCart= function(sanpham){
		var idx = $scope.listSelected.indexOf(sanpham);  
		if( (idx > -1)){
			 $scope.listSelected.splice(idx, 1);
		}else{
			 $scope.listSelected.push(sanpham);
		} 
		console.log($scope.listSelected);
	}  
	
	$scope.selectCheckAllShopCart = function(listshopitem){
		let item = listshopitem.product;
		 	if(document.querySelector('#selectallshopcart'+listshopitem.id).checked){
				//console.log(item)
				angular.forEach(item, function(it){ 
					let idx = $scope.listSelected.indexOf(it);
					if(idx >= 0){
						return true;
					}else{
						$scope.listSelected.push(it);
					} 
				})
			}else{ 
				angular.forEach(item, function(it){ 
					let idx = $scope.listSelected.indexOf(it);
					if(idx >= 0){
						$scope.listSelected.splice(idx,1);
					}else{
						 return true; 
					} 
				})
			}
			
		 
		//console.log($scope.listSelected)
	}
	 
		
	$scope.checkallcart= function(){
		
		if($scope.selectallcart){
			//console.log($scope.listshop)
			angular.forEach($scope.listshop, function(item){
				//console.log(item)  
			   	angular.forEach(item.product, function(it){
					//console.log(item)  
					let idx = $scope.listSelected.indexOf(it);
					if(idx >= 0){
						return true;
					}else{
						$scope.listSelected.push(it);
					}
				})
			 
			})
		}else{
			$scope.listSelected=[];
		}
		//console.log($scope.listSelected)
	} 
	 
	
	$scope.exist = function(item){
		return $scope.listSelected.indexOf(item) > -1;
	}
	 
	$scope.fromcartcheckout = function(){
		if($scope.listSelected.length <= 0){
			$scope.listSelected = $scope.cartitems;	
			//console.log($scope.listSelected.length)
			//console.log("Checkou listcart")
		}
		sessionStorage.listcheckout_obj = JSON.stringify($scope.listSelected);
		location.href="#!/biglobby/dathang"
	}
	 
	 
	 
	 $scope.checked = function(id){
		let html = document.querySelector('#checkboxCart'+id)
		  if(html.checked === true){
			return true;
		  }else{
			return false;
		  }
	}
	
	$scope.refreshAll();
  
});
 