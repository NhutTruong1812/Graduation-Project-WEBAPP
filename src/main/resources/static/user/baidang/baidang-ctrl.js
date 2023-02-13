window.userId = function() {
	return JSON.parse(sessionStorage.getItem('usersession'));
}
app.controller('baidang-ctrl', function($scope, $rootScope, $http, $timeout) {
	
	window.socket.setOnPost({
        onCreate: function(data) {
			$rootScope.Alert(true, "success", "Có bài đăng mới bạn có muốn xem không!", 3);
		},
        onUpdate: function(data) {
			$scope.listPost = data;
		},
        onDelete: function(data) {
			$scope.listPost = data;
		}
    })
    
    window.socket.setOnProduct({
        onCreate: function(data) {},
        onUpdate: function(data) {},
        onDelete: function(data) {}
    })
    
    window.socket.setOnCard({
        onCreate: function(data) {},
        onUpdate: function(data) {},
        onDelete: function(data) {}
    })
    
    window.socket.setOnRepComment({
        onCreate: function(data) {
			let post = $scope.listPost.find(o => o.card.id === data.card.id)
			if(post){
				$scope.replys[data.comment.id].push(data);
			}
		},
        onUpdate: function(data) {
			
		},
        onDelete: function(data) {
			let reply = $scope.replys[data.comment.id].find(o => o.id === data.id);
				if(reply){
					let arr = removeItemFromArray($scope.replys, reply);
					$scope.replys = arr;
				}
		}
    })
    //comment.showReply
    window.socket.setOnComment({
        onCreate: function(data) {
			let post = $scope.listPost.find(o => o.card.id === data.card.id)
			if(post){
				$scope.comments[data.comment.id].push(data);
			}
		},
        onUpdate: function(data) {
			$scope.comments = data;
		},
        onDelete: function(data) {
			let comment = $scope.comments[data.comment.id].find(o => o.id === data.id);
				if(comment){
					let arr = removeItemFromArray($scope.comments, comment);
					$scope.comments = arr;
				}
		}
    })
    
    window.socket.setOnLoveCard({
        onCreate: function(data) {
			let post = $scope.listPost.find(o => o.card.id === data.card.id)
			if(post){
				$scope.listPost.find(o => o.card.id === data.card.id).like.count++;
				$scope.likes[post.card.id].push(data);
			}
		},
        onUpdate: function(data) {},
        onDelete: function(data) {
			let post = $scope.listPost.find(o => o.card.id === data.card.id)
			if(post){
				$scope.listPost.find(o => o.card.id === data.card.id).like.count--;
				let like = $scope.likes[post.card.id].find(o => o.id === data.id);
				if(like){
					let arr = removeItemFromArray($scope.likes, like);
					$scope.likes = arr;
				}
			}
		}
    })

	$scope.cardAPI = new GlobalAPI();
	$scope.cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	$scope.postAPI = new GlobalAPI();
	$scope.postAPI.setNamespace(GlobalAPI.namespace.POST);

	$scope.productAPI = new GlobalAPI();
	$scope.productAPI.setNamespace(GlobalAPI.namespace.PRODUCT);

	$scope.cardSubbannerAPI = new GlobalAPI();
	$scope.cardSubbannerAPI.setNamespace(GlobalAPI.namespace.CARD_SUBBANNER);

	$scope.categorieAPI = new GlobalAPI();
	$scope.categorieAPI.setNamespace(GlobalAPI.namespace.CATEGORY);

	$scope.hidecardAPI = new GlobalAPI();
	$scope.hidecardAPI.setNamespace(GlobalAPI.namespace.HIDECARD);

	$scope.shareAPI = new GlobalAPI();
	$scope.shareAPI.setNamespace(GlobalAPI.namespace.SHARE);

	$scope.reportAPI = new GlobalAPI();
	$scope.reportAPI.setNamespace(GlobalAPI.namespace.REPORT);

	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.mybcoinAPI = new GlobalAPI();
	$scope.mybcoinAPI.setNamespace(GlobalAPI.namespace.MYBCOIN);


	let lastScrollTop = 0;
	$scope.user;
	/**
	 * 6/11/2022
	 * haotn
	 * Xóa item trong array
	 */
	// Array.prototype.removeItem = function(obj) {
	//     const index = this.indexOf(obj);
	//     if (index > -1) {
	//         this.splice(index, 1);
	//     }
	// };

	function removeItemFromArray(arr, item) {
		const index = arr.indexOf(item);
		if (index > -1) {
			arr.splice(index, 1);
		}
		return arr;
	}

	/**
	 * 8/11/2022
	 * haotn
	 * Replace item in array
	 */
	Array.prototype.replace = function(oldItem, newItem) {
		const index = this.indexOf(oldItem);
		if (index > -1) { // only splice array when item is found
			this[index] = newItem // 2nd parameter means remove one item only
		}
	};

	/**
	 * 10/11/2022
	 * haotn
	 * Copyright(c)
	 * Insert new item into array
	 */
	function insertIntoArray(arr, index, ...newItems) {
		return [
			...arr.slice(0, index),
			...newItems,
			...arr.slice(index)
		];
	}




	/**
	 * 7/11/2022
	 * haotn
	 * Copyright(c)
	 * Biến chứa page
	 */

	$scope.currentPostPage = 0;

	$scope.posts = [];

	$scope.likes = {};

	$scope.comments = {};

	$scope.replys = {};

	$scope.shares = {};
	/**
	 * 7/11/2022
	 * haotn
	 * Copyright(c)
	 * Khai báo api
	 */
	const cardAPI = new GlobalAPI();

	const postAPI = new GlobalAPI();

	const likeAPI = new GlobalAPI();

	const commentAPI = new GlobalAPI();

	const repCommentAPI = new GlobalAPI();

	const shareAPI = new GlobalAPI();

	const userAPI = new GlobalAPI();

	/**
	 * Set namespace for apis
	 */
	cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	postAPI.setNamespace(GlobalAPI.namespace.POST);

	likeAPI.setNamespace(GlobalAPI.namespace.LOVECARD);

	commentAPI.setNamespace(GlobalAPI.namespace.COMMENT);

	repCommentAPI.setNamespace(GlobalAPI.namespace.REPCOMMENT);

	shareAPI.setNamespace(GlobalAPI.namespace.SHARE);

	userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.currentUser = [];

	$scope.avaterUser = async function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let user = await $scope.userAPI.getById(sessionUser);
		$scope.currentUser = user;
		console.log($scope.currentUser)
		$scope.$digest();

	}


	const commentStorage = {
		add: (cardId, comment) => {
			if ($scope.comments[cardId]) {
				$scope.comments[cardId].push(comment);
			} else {
				$scope.comments[cardId] = [comment];
			}
		},
		addAt: (cardId, index, comment) => {
			if ($scope.comments[cardId]) {
				if ($scope.comments[cardId].indexOf(comment) > -1) {
					$scope.comments[cardId] = insertIntoArray($scope.comments[cardId], index, comment);
				}
			} else {
				$scope.comments[cardId] = [comment];
			}
		}
	}
	const likeStorage = {
		add: (cardId, like) => {
			if ($scope.likes[cardId]) {
				$scope.likes[cardId].push(like);
			} else {
				$scope.likes[cardId] = [comment];
			}
		}
	}





	/**
	 * 7/11/2022
	 * haotn
	 * Copyright(c)
	 * Load post theo trang
	 */
	async function getPage(pagenum) {
		let page = await postAPI.getPage(pagenum, 5);
		const arr = page.content;
		$scope.posts = $scope.posts.concat(arr);
		$scope.$digest();
		// $timeout(function() {
		//     $scope.posts = page;
		// });
	}


	/**
	 * 8/11/2022
	 * haotn
	 * Copyright(c)
	 * Load current user
	 */
	async function loadCurrentUser() {
		const userId = window.userId();
		$scope.user = await userAPI.getById(userId);
		let userLikeds = await userAPI.getCollections($scope.user.id, GlobalAPI.namespace.LOVECARD);
		$scope.user.likes = userLikeds;
		$scope.$digest();
	}

	/**
	 * 8/11/2022
	 * haotn
	 * Copyright(c)
	 * Load addition ingredients
	 */
	async function loadAdditionIngredients(posts) {
		let postLength = posts.length;
		let userLikeds = $scope.user.likes;
		for (let i = 0; i < postLength; i++) {
			let post = posts[i];
			let likeCount = await cardAPI.getCountOfCollection(post.card.id, GlobalAPI.namespace.LOVECARD);
			let commentCount = await cardAPI.getCountOfCollection(post.card.id, GlobalAPI.namespace.COMMENT);
			let shareCount = await cardAPI.getCountOfCollection(post.card.id, GlobalAPI.namespace.SHARE);
			posts[i].card.comment = {
				show: false,
				count: commentCount,
				currentPage: -1,
			};
			posts[i].card.share = {
				count: shareCount,
				currentPage: -1,
			};
			posts[i].card.like = {
				count: likeCount,
				currentPage: -1,
			};
			let likeThisPost = userLikeds.find(o => o.card.id === post.card.id);
			if (likeThisPost) {
				posts[i].card.liked = true;
			} else {
				posts[i].card.liked = false;
			}
			let oldPost = $scope.listPost.find(o => o.id === posts[i].id);
			$scope.listPost.replace(oldPost, posts[i]);
		}
		$scope.$digest();
	}

	/**
	 * 8/11/2022
	 * haotn
	 * like post
	 */
	async function like(cardId) {
		let card = $scope.listPost.find(o => o.card.id === cardId).card;
		let data = {
			id: null,
			user: $scope.user,
			card: card,
			loveDate: new Date()
		}
		let like = await likeAPI.create(data);
		if (like) {
			$scope.listPost.find(o => o.card.id === cardId).card.liked = true;
			$scope.listPost.find(o => o.card.id === cardId).card.like.count++;
			// $scope.posts.find(o => o.id === postId).card.like.loaded.push(like);
			if ($scope.likes[cardId]) {
				$scope.likes[cardId].push(like);
			} else {
				$scope.likes[cardId] = [like];
			}

		}
	}


	/**
	 * 8/11/2022
	 * haotn
	 * unlike post
	 */
	async function unlike(cardId) {
		let card = $scope.listPost.find(o => o.card.id === cardId).card;
		let like = await likeAPI.getByProperties({
			'user.id': $scope.user.id,
			'card.id': card.id
		});
		let deleted = await likeAPI.delete(like.id);
		if (deleted) {
			const likeItem = $scope.likes[cardId].find(o => o.id === like.id);
			if (likeItem) {
				if ($scope.likes[cardId].includes(likeItem)) {
					$scope.likes[cardId] = removeItemFromArray($scope.likes[cardId], likeItem);
				}
			}
			// $scope.posts.find(o => o.id === postId).card.like.loaded.removeItem(likeItem);

			$scope.listPost.find(o => o.card.id === cardId).card.like.count--;
			$scope.listPost.find(o => o.card.id === cardId).card.liked = false;
		}
	}

	/**
	 * 8/11/2022
	 * haotn
	 * Xử lý hành động nhấn nút like
	 */

	$scope.likeAction = async function(event, cardId) {
		let likeIcon = event.currentTarget.querySelectorAll('.likeActive')[0];
		const liked = $scope.listPost.find(o => o.card.id === cardId).card.liked;
		if (liked == true) {
			await unlike(cardId);
			likeIcon.classList.remove('active');
		} else {
			await like(cardId);
			likeIcon.classList.add('active');
		}
		$scope.$digest();
	}


	/**
	 * 8/11/2022
	 * haotn
	 * Hiện thêm bình luận của bài viết
	 */
	$scope.loadMoreComment = async function(cardId) {
		const card = $scope.listPost.find(o => o.card.id === cardId).card;
		const commentPage = card.comment.currentPage + 1;
		const page = await cardAPI.getPageCollections(cardId, GlobalAPI.namespace.COMMENT, commentPage, 5);
		if (!page.last) {
			$scope.listPost.find(o => o.card.id === cardId).card.comment.currentPage = commentPage;
		}
		let loadedComments = $scope.comments[cardId];
		let commentLength = page.content.length;

		for (let i = 0; i < commentLength; i++) {
			let comment = page.content[i];
			comment.showReply = false;
			let replys = await commentAPI.getCollections(comment.id, GlobalAPI.namespace.REPCOMMENT);
			if ($scope.replys[comment.id]) {
				$scope.replys[comment.id] = $scope.replys[comment.id].concat(replys);
			} else {
				$scope.replys[comment.id] = replys;
			}
			if (loadedComments) {
				const exist = loadedComments.find(o => o.id === page.content[i].id);
				if (!exist) {
					commentStorage.add(cardId, page.content[i]);
				}
			} else {
				commentStorage.add(cardId, page.content[i]);
			}
		}
		console.log($scope.comments);
		$scope.listPost.find(o => o.card.id === cardId).card.comment.show = true;
		$scope.$digest();
	}


	/**
	 * 10/11/2022
	 * haotn
	 * Copyright(c)
	 * Ẩn hiện khối bình luận
	 */
	$scope.showComment = function(cardId) {
		let current = $scope.listPost.find(o => o.card.id === cardId).card.comment.show;
		$scope.listPost.find(o => o.card.id === cardId).card.comment.show = !current;
		$scope.$digest();
	}


	/**
	 * 9/11/2022
	 * haotn
	 * Load more posts when scroll near bottom
	 *  */
	$scope.scrollaction = async function(elm) {
		if ($(elm).scrollTop() + $(elm).innerHeight() >= $(elm)[0].scrollHeight - 100) {
			$scope.currentPostPage += 1;
			await getPage($scope.currentPostPage);
			loadAdditionIngredients($scope.posts);
			$scope.$digest();
		}
	}

	/**
	 * 9/11/2022
	 * haotn
	 * Copyright(c)
	 * Add comment
	 */
	$scope.addComment = async function(event, cardId) {
		let value = event.currentTarget.value;

		let card = $scope.listPost.find(o => o.card.id === cardId).card;
		let user = $scope.user;
		let data = {
			id: null,
			user: user,
			card: card,
			commentDate: new Date(),
			content: value
		};
		let comment = await commentAPI.create(data);
		commentStorage.addAt(cardId, 0, comment)
		// $scope.comments[cardId] = insertIntoArray(loadedComment, 0, comment);;
		$scope.listPost.find(o => o.card.id === cardId).card.comment.count++;
		$scope.listPost.find(o => o.card.id === cardId).card.comment.show = true;
		//event.currentTarget.value = '';
		$scope.$digest();
		$scope.resetForm();
	}


	/**
	 * 10/11/2022
	 * haotn
	 * Hiện input trả lời bình luận
	 */
	$scope.showReply = function(cardId, commentId) {
		let comment = $scope.comments[cardId].find(o => o.id===commentId);
		let currentStatus = comment.showReply;
		$scope.comments[cardId].find(o => o.id===commentId).showReply = !currentStatus;
	}

	/**
	 * 10/11/2022
	 * haotn
	 * Copyright(c)
	 * Thêm phản hồi cho bình luận
	 */
	$scope.addReply = async function(event, commentId) {
		let code = (event.keyCode ? event.keyCode : event.which);
		if (code == 13) {
			let input = event.currentTarget;
			input.parentElement.onsubmit = function(e) {
				e.preventDefault();
			}
			let comment = await commentAPI.getById(commentId);
			let userId = window.userId();
			let user = await userAPI.getById(userId);
			let data = {
				id: null,
				comment: comment,
				user: user,
				repcommentDate: new Date(),
				content: input.value
			};
			let reply = await repCommentAPI.create(data);
			if (reply) {
				if ($scope.replys[comment.id]) {
					$scope.replys[comment.id].push(reply);
				} else {
					$scope.replys[comment.id] = [reply];
				}
				input.value = '';
			}
			console.log(reply);
			$scope.$digest();
		}

	}







	/**
	 * 7/11/2022
	 * haotn
	 * Copyright(c)
	 * Initialize function
	 */
	async function initialize() {
		await loadCurrentUser();
		await getPage($scope.currentPostPage);
		await loadAdditionIngredients($scope.posts);
		await $scope.avaterUser();
		console.log($scope.posts);
		console.log("bình luân trả lời: ", $scope.replys);
		console.log("bình luận hiện tại: ", $scope.comments)
	}
	initialize();


	/// KHU VỰC QUẢN LÝ BÀI ĐĂNG
	$scope.checkTab = 1;
	$scope.changeTab = function(tab) {
		$scope.checkTab = tab;
		console.log(tab);
	}


	//load cbo category
	$scope.itemCates = [];
	$scope.loadCates = function() {
		$scope.categorieAPI.getCollections('categoryStatus', '').then(resp => {
			$scope.itemCates = resp;
		})
	}

	$scope.listPost = [];
	//load all post của user đang đăng nhập (post.card.isProduct: false, post.card.status: 3, post.card.hidden: false, hideCard của user đang login không có bài đăng đó)
	$scope.loadAllPost = async function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let resp = await $scope.postAPI.getByProperties({ loadAllPostOfUser: 1, idUser: sessionUser });
		$scope.listPost = resp;
		console.log($scope.listPost)
		//Tìm tất cả hideCard có idUser = user đang login
		let respH = await $scope.hidecardAPI.getByProperties({ 'idUser': sessionUser, 'loadAllHideCardWithUser': 1 });
		for (var i = 0; i < respH.length; i++) {
			for (var j = 0; j < $scope.listPost.length; j++) {
				if (respH[i].card.id == $scope.listPost[j].card.id) {
					$scope.listPost.splice(j, 1);
				}
			}
		}
		console.log($scope.listPost)
		$scope.$digest();

	}

	$scope.userLogin = {};
	$scope.myBcoin = {};
	$scope.myShop = {};
	$scope.myRegisteractive = {};
	$scope.myShopStatistical = {};
	$scope.myShopStatistical = {};
	$scope.getUserLogin = async function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let resp = await $scope.userAPI.getById(sessionUser);
		$scope.userLogin = resp;

		//get bcoin of user login
		let respB = await $scope.mybcoinAPI.getByProperties({ 'idUser': sessionUser });
		$scope.myBcoin = respB;

		//get intro of user login
		let respI = await $scope.userAPI.getCollections(sessionUser, 'shop');
		$scope.myShop = respI;

		//get intro of user login
		let respR = await $scope.userAPI.getCollections(sessionUser, 'registeractive');
		$scope.myRegisteractive = respR;

		//get statistical my shop
		let respS = await $scope.userAPI.getByProperties({ 'idUser': sessionUser, 'StatisticalInfoShop': 1 });
		$scope.myShopStatistical = respS[0];

		console.log($scope.myRegisteractive)

		$scope.$digest();
	}

	//hàm xử lý định dạng tiền Bcoin
	$scope.currencyBcoinFormat = function(bcoin) {
		const config = { maximumSignificantDigits: 3 }
		const formated = new Intl.NumberFormat('vi-VN', config).format(bcoin);
		return formated;
	}



	//************ KHU VỰC ĐĂNG BÀI ************//

	$scope.form = {};
	$scope.result;
	$scope.resultTabProduct;
	$scope.checkQuantityImageUpload = 0;
	$scope.checkQuantityImageUploadTabProduct = 0;
	$scope.imageWhenSelectMulti;
	$scope.imageWhenSelectMultiTabProduct;

	//Chọn ảnh tab bài viết
	$scope.imageChanged = function(files) {
		var data = new FormData();
		for (var i = 0; i < files.length; i++) {
			data.append('file', files[i]);
		}

		GlobalAPI.uploadFile('folder', data).then(resp => {
			let path = resp.data;
			$scope.form.banner = path;
			if ($scope.form.banner.length > 1) {
				$scope.result = $scope.form.banner.length + " ảnh";
				$scope.checkQuantityImageUpload = 2;
				$scope.imageWhenSelectMulti = $scope.form.banner;
				$scope.$digest();
			} else {
				$scope.result = $scope.form.banner[0].slice(-26);
				$scope.checkQuantityImageUpload = 1;
				$scope.$digest();
			}
		}).catch(error => {
			console.log("Lỗi poster", error)
		})
	}

	//Chọn ảnh tab sản phẩm
	$scope.imageChangedProduct = async function(files) {
		var data = new FormData();
		for (var i = 0; i < files.length; i++) {
			data.append('file', files[i]);
		}
		await GlobalAPI.uploadFile('folder', data).then(resp => {
			let path = resp.data;
			$scope.form.bannerProduct = path;
			if ($scope.form.bannerProduct.length > 1) {
				$scope.resultTabProduct = $scope.form.bannerProduct.length + " ảnh";
				$scope.checkQuantityImageUploadTabProduct = 2;
				$scope.imageWhenSelectMultiTabProduct = $scope.form.bannerProduct;
				$scope.$digest();
			} else {
				$scope.resultTabProduct = $scope.form.bannerProduct[0].slice(-26);
				$scope.checkQuantityImageUploadTabProduct = 1;
				$scope.$digest();
			}
			document.querySelector(".messageResultBannerProduct").innerHTML = "";
		}).catch(error => {
			console.log("Lỗi poster" + error)
		})
	}


	//Thêm bài đăng và sản phẩm
	$scope.checkClick = 1;

	$scope.changeClick = function(tab) {
		$scope.checkClick = tab;
		$scope.checkValidator();
	}

	$scope.createCard = function() {
		let data = angular.copy($scope.form);
		let obj_card = {};
		let obj_post = {};
		let obj_product = {};
		let obj_subBanner = {};

		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		obj_card.user = { id: sessionUser };
		obj_card.postAt = new Date();
		if ($scope.checkClick == 1) {
			obj_card.isProduct = 0;
			obj_card.status = { id: 3 };
			obj_card.hidden = data.hiddenPost;
			obj_card.title = data.title;
			obj_card.content = data.contentPost;

			if ($scope.checkQuantityImageUpload > 1) {
				obj_card.banner = $scope.imageWhenSelectMulti[0].slice(-26);
				obj_card.subBanner = $scope.imageWhenSelectMulti.slice(1, $scope.imageWhenSelectMulti.length);
			} else {
				obj_card.banner = $scope.result;
			}

			if (obj_card.title == undefined || obj_card.title == '' || obj_card.content == undefined || obj_card.content == '') {
				//Nểu rỗng một trong các trường trên thì không làm gì hết
			} else {
				$scope.cardAPI.create(obj_card).then(resp => {
					let dataCard = resp;
					obj_post.card = dataCard;
					obj_post.title = obj_card.title;
					obj_post.content = obj_card.content;
					obj_post.banner = obj_card.banner;
					$scope.postAPI.create(obj_post).then(async (resp) => {
						if ($scope.checkQuantityImageUpload > 1) {
							for (var i = 0; i < obj_card.subBanner.length; i++) {
								obj_subBanner.card = dataCard;
								obj_subBanner.subbanner = obj_card.subBanner[i].slice(-26);
								console.log(obj_subBanner)
								await $scope.cardSubbannerAPI.create(obj_subBanner).then(resp => {
									console.log("Đã add thành công Subbanner");
								})
							}
						}

						$rootScope.Alert(true, "success", "Đăng bài viết thành công!", 3);
						var btn_close = document.querySelector('#btn_close');
						btn_close.click();
						$scope.resetForm();
						$scope.initialize();
					})
				})
			}

		} else {
			obj_card.isProduct = 1;
			obj_card.status = { id: 1 };
			obj_card.hidden = data.hiddenProduct;
			obj_card.product = data.productName;
			obj_card.category = { id: data.category }
			obj_card.price = data.price;
			obj_card.pricePercent = 1.0;
			obj_card.available = data.available;
			obj_card.description = data.description;

			if ($scope.checkQuantityImageUploadTabProduct > 1) {
				obj_card.banner = $scope.imageWhenSelectMultiTabProduct[0].slice(-26);
				obj_card.subBanner = $scope.imageWhenSelectMultiTabProduct.slice(1, $scope.imageWhenSelectMultiTabProduct.length);
			} else {
				obj_card.banner = $scope.resultTabProduct;
			}

			if (obj_card.product == undefined || obj_card.product == '' ||
				obj_card.price == undefined || obj_card.price == '' ||
				obj_card.available == undefined || obj_card.available == '' || obj_card.banner == undefined || obj_card.banner == '') {
				if (obj_card.banner == undefined || obj_card.banner == '') {
					document.querySelector(".messageResultBannerProduct").innerHTML = "Vui lòng chọn ảnh!";
				}
			} else {
				$scope.cardAPI.create(obj_card).then(resp => {
					let dataCard = resp;
					obj_product.card = dataCard;
					obj_product.product = obj_card.product;
					obj_product.category = obj_card.category;
					obj_product.price = obj_card.price;
					obj_product.pricePercent = obj_card.pricePercent;
					obj_product.available = obj_card.available;
					obj_product.description = obj_card.description;
					obj_product.banner = obj_card.banner;
					$scope.productAPI.create(obj_product).then(async (resp) => {
						if ($scope.checkQuantityImageUploadTabProduct > 1) {
							for (var i = 0; i < obj_card.subBanner.length; i++) {
								obj_subBanner.card = dataCard;
								obj_subBanner.subbanner = obj_card.subBanner[i].slice(-26);
								console.log(obj_subBanner)
								await $scope.cardSubbannerAPI.create(obj_subBanner).then(resp => {
									console.log("Đã add thành công Subbanner");
								})
							}
						}
						$rootScope.Alert(true, "success", "Đăng sản phẩm thành công! Vui lòng chờ duyệt...", 3);
						var btn_close = document.querySelector('#btn_close');
						btn_close.click();
						$scope.resetForm();
						$scope.initialize();
					})
				})
			}

		}

	}

	$scope.resetForm = function() {
		document.getElementById("comment--text").value = '';
		document.getElementById("formCard").reset();
		$scope.result = "";
		$scope.resultTabProduct = "";
		$scope.form = {};
		document.querySelector(".messageResultBannerProduct").innerHTML = ""
		var a = document.getElementsByClassName('form-message');
		for (var i = 0; i < a.length; i++) {
			a[i].innerHTML = '';
		}

		//set giá trị select mặc định của Combobox
		$scope.form.hiddenPost = 0;
		$scope.form.hiddenProduct = 0;
		$scope.form.category = 1;
	}

	$scope.initialize = function() {
		$scope.loadCates();
		$scope.checkValidator();
		$scope.loadAllPost();
		$scope.getUserLogin();

		//set giá trị select mặc định của Combobox
		$scope.form.hiddenPost = 0;
		$scope.form.hiddenProduct = 0;
		$scope.form.category = 1;
	};

	$scope.checkValidator = function() {
		if ($scope.checkClick == 1) {
			Validator({
				form: '#formCard',
				formGroupSelector: '.form-group',
				errorSelector: '.form-message',
				invalidSelector: '.invalid',
				rules: [
					Validator.isRequired("#title", 'Vui lòng nhập tiêu đề!'),
					Validator.isRequired("#contentPost", 'Vui lòng nhập nội dung!'),
				],
				onSubmit: function(data) { }
			});
		} else {
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

		}
	}



	//Ẩn bài đăng
	$scope.hiddenCard = function() {
		let object_hideCard = {};
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		object_hideCard.user = { id: sessionUser };
		object_hideCard.card = $scope.postGeted.card;
		object_hideCard.hideDate = new Date();
		$scope.hidecardAPI.create(object_hideCard).then(resp => {
			$rootScope.Alert(true, "success", "Ẩn bài đăng thành công!", 3);
			var btn_close = document.querySelector('#btn_hideCard_confirm');
			btn_close.click();
			$scope.initialize();
		})
	}


	$scope.postGeted = {};
	//Get ID Post
	$scope.getPost = function(idPost) {
		$scope.postGeted = $scope.listPost.find(o => o.id == idPost);
		console.log($scope.postGeted)

	}

	//Chia sẻ bài viết
	$scope.shareCard = function() {
		let card = $scope.postGeted.card;
		let obj_shareCard = {};
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		obj_shareCard.user = { id: sessionUser };
		obj_shareCard.card = card;
		obj_shareCard.shareDate = new Date();
		let e_hidden = document.getElementById("share_hidden");
		obj_shareCard.hidden = e_hidden.value;
		obj_shareCard.content = document.getElementById("share_content").value;
		$scope.shareAPI.create(obj_shareCard).then(resp => {
			var btn_close = document.querySelector('#close_sharePost');
			btn_close.click();
		})
		$rootScope.Alert(true, "success", "Chia sẻ thành công!", 3);
	}


	//Xóa bài viết
	$scope.deletePost = function() {
		$scope.postAPI.delete($scope.postGeted.id).then(resp => {
			$rootScope.Alert(true, "success", "Xóa thành công!", 3);
			var btn_close = document.querySelector('#btn_delete_close');
			btn_close.click();
			$scope.initialize();
		})
	}


	$scope.formEditPost = {};
	$scope.showAllImagePostEdit = {};
	$scope.checkPostOrigin = 0;
	$scope.listSubbanerEditPost = [];

	//Chỉnh sửa bài viết
	$scope.showEditPost = function() {
		$scope.formEditPost = $scope.postGeted;
		if ($scope.formEditPost.banner == null || $scope.formEditPost.banner == undefined || $scope.formEditPost.banner == '') {
			$scope.showAllImagePostEdit = 'Chưa có ảnh nào';
		} else {
			$scope.cardAPI.getCollections($scope.formEditPost.card.id, 'subbanners').then(resp => {
				let listSubbaners = resp;
				if (listSubbaners != undefined && listSubbaners != null && listSubbaners != '') {
					$scope.showAllImagePostEdit = listSubbaners.length + 1 + ' ảnh';
					$scope.checkPostOrigin = 1;
				} else {
					$scope.showAllImagePostEdit = $scope.formEditPost.banner;
					$scope.checkPostOrigin = 0;
				}
				$scope.$digest();
			})
		}
	}

	$scope.editPost = function() {
		$scope.cardAPI.update($scope.formEditPost.card.id, $scope.formEditPost.card).then(resp => {
			if ($scope.checkClickImageEditPost == 1) {
				$scope.formEditPost.banner = $scope.formEditPost.banner[0].slice(-26);
				$scope.postAPI.update($scope.formEditPost.id, $scope.formEditPost).then(respPost => {
					if ($scope.checkPostOrigin == 1) {
						$scope.cardSubbannerAPI.deleteCollection($scope.formEditPost.card.id, GlobalAPI.namespace.CARD_SUBBANNER).then(respDelSubbaner => { })
					}
					$rootScope.Alert(true, "success", "Cập nhật thành công!", 3);
					$scope.initialize();
					var btn_close = document.querySelector('#btn_close_editPost');
					btn_close.click();
				})
			} else if ($scope.checkClickImageEditPost == 2) {
				let obj_listSubbaner = {};
				$scope.listSubbanerEditPost = $scope.formEditPost.banner.slice(1, $scope.formEditPost.banner.length);
				$scope.formEditPost.banner = $scope.formEditPost.banner[0].slice(-26);
				$scope.postAPI.update($scope.formEditPost.id, $scope.formEditPost).then(async (respPost) => {
					if ($scope.checkPostOrigin == 1) {
						$scope.cardSubbannerAPI.deleteCollection($scope.formEditPost.card.id, GlobalAPI.namespace.CARD_SUBBANNER).then(async (respDelSubbaner) => {
							for (var i = 0; i < $scope.listSubbanerEditPost.length; i++) {
								obj_listSubbaner.card = $scope.formEditPost.card;
								obj_listSubbaner.subbanner = $scope.listSubbanerEditPost[i].slice(-26);
								await $scope.cardSubbannerAPI.create(obj_listSubbaner).then(resp => {
									console.log("Đã add thành công Subbanner");
								})
							}
						})
					} else {
						for (var i = 0; i < $scope.listSubbanerEditPost.length; i++) {
							obj_listSubbaner.card = $scope.formEditPost.card;
							obj_listSubbaner.subbanner = $scope.listSubbanerEditPost[i].slice(-26);
							await $scope.cardSubbannerAPI.create(obj_listSubbaner).then(resp => {
								console.log("Đã add thành công Subbanner");
							})
						}
					}
					$rootScope.Alert(true, "success", "Cập nhật thành công!", 3);
					$scope.initialize();
					var btn_close = document.querySelector('#btn_close_editPost');
					btn_close.click();
				})
			} else {
				$scope.postAPI.update($scope.formEditPost.id, $scope.formEditPost).then(respPostt => {
					$rootScope.Alert(true, "success", "Cập nhật thành công!", 3);
					$scope.initialize();
					var btn_close = document.querySelector('#btn_close_editPost');
					btn_close.click();
				})
			}
		})
	}

	$scope.checkClickImageEditPost = 0; //0 là chưa bấm vào, 1 là chọn 1 hình, 2 là chọn nhiều hình
	//Chọn ảnh khi edit bài viết
	$scope.imageChangedEditPost = async function(files) {
		var data = new FormData();
		for (var i = 0; i < files.length; i++) {
			data.append('file', files[i]);
		}
		await GlobalAPI.uploadFile('folder', data).then(resp => {
			let path = resp.data;
			$scope.formEditPost.banner = path;
			if ($scope.formEditPost.banner.length > 1) {
				$scope.checkClickImageEditPost = 2;
				$scope.showAllImagePostEdit = $scope.formEditPost.banner.length + ' ảnh';
				$scope.$digest();
			} else {
				$scope.checkClickImageEditPost = 1;
				$scope.showAllImagePostEdit = $scope.formEditPost.banner[0].slice(-26);
				$scope.$digest();
			}
		}).catch(error => {
			console.log("Lỗi poster" + error)
		})
	}

	$scope.initialize();

});