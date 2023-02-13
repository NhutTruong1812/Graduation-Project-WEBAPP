app.controller('xembaidangcuahangkhac-ctrl', function($scope, $rootScope, $http) {
	
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

	/**KHAI BÁO API */
	$scope.productAPI = new GlobalAPI();
	$scope.productAPI.setNamespace(GlobalAPI.namespace.PRODUCT);

	$scope.cardAPI = new GlobalAPI();
	$scope.cardAPI.setNamespace(GlobalAPI.namespace.CARD);

	$scope.categorieAPI = new GlobalAPI();
	$scope.categorieAPI.setNamespace(GlobalAPI.namespace.CATEGORY);

	$scope.failhistorieAPI = new GlobalAPI();
	$scope.failhistorieAPI.setNamespace(GlobalAPI.namespace.FAIL_HISTORY);

	$scope.producthistorieAPI = new GlobalAPI();
	$scope.producthistorieAPI.setNamespace(GlobalAPI.namespace.PRODUCT_HISTORY);

	$scope.newAPI = new GlobalAPI();
	$scope.newAPI.setNamespace(GlobalAPI.namespace.NEWS);

	$scope.shopAPI = new GlobalAPI();
	$scope.shopAPI.setNamespace(GlobalAPI.namespace.MYSHOP);

	$scope.loveCardAPI = new GlobalAPI();
	$scope.loveCardAPI.setNamespace(GlobalAPI.namespace.LOVECARD);

	$scope.problemAPI = new GlobalAPI();
	$scope.problemAPI.setNamespace(GlobalAPI.namespace.PROBLEM);

	$scope.hidecardAPI = new GlobalAPI();
	$scope.hidecardAPI.setNamespace(GlobalAPI.namespace.HIDECARD);

	$scope.reportcardAPI = new GlobalAPI();
	$scope.reportcardAPI.setNamespace(GlobalAPI.namespace.REPORT);

	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	$scope.registerAPI = new GlobalAPI();
	$scope.registerAPI.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);

	$scope.followshopAPI = new GlobalAPI();
	$scope.followshopAPI.setNamespace(GlobalAPI.namespace.FOLLOW_SHOP);

	$scope.orderdetailAPI = new GlobalAPI();
	$scope.orderdetailAPI.setNamespace(GlobalAPI.namespace.ORDERDETAIL);

	$scope.postAPI = new GlobalAPI();
	$scope.postAPI.setNamespace(GlobalAPI.namespace.POST);

	$scope.shareAPI = new GlobalAPI();
	$scope.shareAPI.setNamespace(GlobalAPI.namespace.SHARE);



	/**truongnvn */
	//19. get register
	$scope.register = {};

	//21. follow shop all
	$scope.pageFollowShopALl = [];

	//22. sô lượng đã bán
	$scope.sumQuantitySaleOfShop = 0;

	//23. obj_follow
	$scope.obj_follow = {};

	$scope.s = {};

	$scope.userview = JSON.parse(sessionStorage.usersession);

	$scope.pageProductActiveShop = [];

	//get Profile
	$scope.getProfileShow = async function() {
		let userview = JSON.parse(sessionStorage.usersession);
		let get_user_data = await $scope.userAPI.getCollections(userview, "followshops")

		let list_follow = get_user_data;
		for (let i = 0; i < list_follow.length; i++) {
			if (list_follow[i].userFollow.id === userview) {
				$scope.obj_follow = list_follow[i];
				break;
			} else {
				$scope.obj_follow = {};
			}
		}
		$scope.$digest();
	}

	//get Follow
	$scope.getFollowAllShow = async function() {
		//console.log($scope.s.user.id)
		let get_regis_data = await $scope.registerAPI.getByProperty("user.id", $scope.s.user.id)
			.catch(Error => {
				get_regis_data = {};
			})
		$scope.register = get_regis_data;
		//console.log($scope.register)
		//$scope.pageFollowShopALl = await $scope.followshopAPI.getUrlTemp("user/page/1/6/2", "user", $scope.s.id)
		$scope.pageFollowShopALl = await $scope.followshopAPI.getPageByProperties(1, 6, { 'pagewhere': 2, 'user': $scope.s.id });
		$scope.sumQuantitySaleOfShop = await $scope.orderdetailAPI.getCollections("sum/quantity/1", $scope.register.user.id)
			.catch(Error => {
				$scope.sumQuantitySaleOfShop = 0;
			})
		$scope.$digest();
	}


	//follow-un folow
	//follow
	$scope.Follow = async function() {
		let userview = JSON.parse(sessionStorage.usersession);
		let obj_follow_add = {
			userFollow: { id: userview },
			shop: $scope.s,
			followDate: new Date(),
		}
		$scope.obj_follow = await $scope.followshopAPI.create(obj_follow_add)
			.catch(Error => {
				alert("Theo dõi thất bại!");
			})
		await $scope.getFollowAllShow();
	}
	//follow
	$scope.unFollow = async function() {
		await $scope.followshopAPI.delete($scope.obj_follow.id)
		$scope.obj_follow = {};
		await $scope.getFollowAllShow();
	}

	//init chat
	$scope.initChat = async function(username) {
		let userview = JSON.parse(sessionStorage.usersession);
		let user = await $scope.userAPI.getById(userview)

		let room = {
			//Người đang đăng nhập
			initator: user.username,
			//Người đang đăng nhập và người được chọn để nhắn tin
			members: [user.username, username],
			//Thời gian
			createAt: new Date(),
			updateAt: new Date()
		}

		//Open Chat Romm
		window.socket.initChat(room);

		location.href = "#!biglobby/nhantin"
	}

	$scope.initializeRight = async function() {
		$scope.IdShop = JSON.parse(localStorage.idShopDetail);
		let data_get = await $scope.productAPI.getPageByProperties(1, 6, { 'pagewhere': 2, 'key': "", 'user': $scope.IdShop });
		$scope.pageProductActiveShop = data_get;
		let get_shop_data = await $scope.shopAPI.getAll();
		$scope.shop = get_shop_data;
		for (let i = 0; i < $scope.shop.length; i++) {
			if ($scope.shop[i].user.id === $scope.IdShop) {
				//console.log($scope.shop[i])
				$scope.s = $scope.shop[i];
				break;
			}
		}
		//get follow show 
		await $scope.getFollowAllShow();

		//get prodile show
		await $scope.getProfileShow();

	}

	$scope.listPost = [];
	//load all post của user mình đang xem (post.card.isProduct: false, post.card.status: 3, post.card.hidden: false, hideCard của user đang login không có bài đăng đó)
	$scope.loadAllPost = async function() {
		console.log("vao all post")
		let idUserView = JSON.parse(localStorage.idShopDetail);
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let resp = await $scope.postAPI.getByProperties({ loadAllPostOfUser: 1, idUser: idUserView });
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
		console.log("vao share card")
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


	//Báo cáo bài viết
	$scope.reportCard = function() {
		let card = $scope.postGeted.card;
		let obj_reportCard = {};
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		obj_reportCard.user = { id: sessionUser };
		obj_reportCard.card = card;
		obj_reportCard.reportDate = new Date();
		obj_reportCard.status = { id: 12 };

		var getSelectedValue = document.querySelector(
			'input[name="report_content"]:checked');
		if (getSelectedValue != null) {
			$rootScope.Alert(true, "success", "Gửi báo cáo thành công!", 3);
			obj_reportCard.problem = { id: getSelectedValue.value }
			$scope.reportcardAPI.create(obj_reportCard).then(resp => {
				console.log("gui bao cao thanh cong")
				var btn_close = document.querySelector('#close_reportModal');
				btn_close.click();
			})
		} else {
			$rootScope.Alert(true, "danger", "Vui lòng chọn vấn đề!", 3);
		}

	}
	
	$scope.userLogin = {};
	$scope.myBcoin = {};
	$scope.getUserLogin = async function() {
		let sessionUser = JSON.parse(sessionStorage.getItem("usersession"));
		let resp = await $scope.userAPI.getById(sessionUser);
		$scope.userLogin = resp;

		//get bcoin of user login
		let respB = await $scope.mybcoinAPI.getByProperties({ 'idUser': sessionUser });
		$scope.myBcoin = respB;
		$scope.$digest();


	}

	$scope.initialize = function() {
		$scope.loadAllPost();
		$scope.getUserLogin();
	}

	$scope.initializeRight();
	$scope.initialize();
	/**truongnvn */
	
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

	

});
