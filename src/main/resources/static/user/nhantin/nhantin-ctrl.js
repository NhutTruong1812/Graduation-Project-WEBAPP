app.controller('nhantin-ctrl', function($scope, $http) {
	/**
	 * 4/12/2022
	 * haotn
	 */

	/*
	 * 5/12/22
	 *truongnvn
	 * code backend gửi tin nhắn, front=end gửi tin nhắn
	 */

	//Khai Báo API
	$scope.userAPI = new GlobalAPI();
	$scope.userAPI.setNamespace(GlobalAPI.namespace.USER);

	/**BEGIN VAIRIABLE*/
	//obj_user login - người gửi
	$scope.sender = {};

	//obj_người nhận - người nhận
	$scope.receiver = {};

	//obj room chat
	$scope.roomChatActive = {};

	//obj message chat 
	$scope.messageContent = {};

	//Dữ liêu page tin nhắn
	let roomPagenum = 1;

	//Trang dữ liệu tổng
	$scope.rooms;

	//Cho phép gửi hay không
	$scope.canSend = false;

	/**END VAIRIABLE */


	/**  ---- CENTER SETTING ---- */
	window.socket.setOnChat({
		//CONFIG ROOM
		onInit: async (data) => {
			console.log("Yêu Cầu Mở Phòng Chat")
			console.log(data)
			/*if ($scope.rooms.numberOfElements >= 1) {*/
			let roomFind = $scope.rooms.find(o => o.id === data.id);
			if (roomFind) {
				let index = $scope.rooms.indexOf(roomFind);
				$scope.rooms.splice(index, 1);
				roomFind.updateAt = new Date();
				$scope.rooms.unshift(roomFind);
			} else {
				let room = await chatRoomApi.getById(data.id)
				if (room) {
					room.messagePagenum = 1;
					let pageMessage = await loadMessageForRoom(room.id);
					room.messages = pageMessage.content.reverse();
					let memberLength = room.members.length;
					let members = [];
					for (let j = 0; j < memberLength; j++) {
						let user = await userApi.getByProperties({ "username": room.members[j] });
						members.push(user);
						if (user.id !== $scope.sender.id) {
							room.partner = user;
						}
					}
					room.members = members;
					$scope.rooms.unshift(room);
				}
			}
			/*} else {
				
				$scope.rooms.push(data);
			}
			*/
			$scope.$digest();
		},
		//CONFIG MESSAGE
		onMessage: async (data) => {
			console.log("Yêu Cầu Gửi tin nhắn")
			console.log(data)
			let roomFind = $scope.rooms.find(o => o.id === data.roomId);
			if (roomFind) {
				roomFind.messages.push(data);
				let index = $scope.rooms.indexOf(roomFind);
				$scope.rooms.splice(index, 1);
				roomFind.updateAt = new Date();
				$scope.rooms.unshift(roomFind);
			} else {
				let room = await chatRoomApi.getById(data.roomId)
				if (room) {
					room.messagePagenum = 1;
					let pageMessage = await loadMessageForRoom(room.id);
					room.messages = pageMessage.content.reverse();

					let memberLength = room.members.length;
					let members = [];
					for (let j = 0; j < memberLength; j++) {
						let user = await userApi.getByProperties({ "username": room.members[j] });
						members.push(user);
						if (user.id !== $scope.sender.id) {
							room.partner = user;
						}
					}
					room.members = members;
					$scope.rooms.unshift(room);
				}
			}

			$scope.$digest();
			/*for(let i = 0; i < $scope.rooms.numberOfElements; i++){
				if($scope.rooms[i].id == data.roomId){
					$scope.rooms[i].messages.push(data);
					$scope.$digest();
					break;
				}
			}*/
		}
	});


	/** --- SOCKET EVENT--- */
	//gửi tin nhắn
	$scope.sendMessage = function() {
		/**Begin Content send*/
		let sender = $scope.sender;
		let messageContent = $scope.messageContent;
		let messageType = "TEXT";
		let roomChat = $scope.roomChatActive.id;
		/**End Content Send*/

		//'638da1341a56692f480c670b'
		let message = {
			//nội dung gửi tin nhắn
			message: messageContent.value,
			//kiểu dữ liệu gửi
			type: messageType,
			//người gửi
			sender: sender.username,
			//phòng chat
			roomId: roomChat,
			//người seen tin nhắn
			readByRecipients: [sender.username],
			//thời gian
			createAt: new Date(),
			updateAt: new Date(),
		}
		//gửi
		window.socket.sendMessage(message);
	}

	//Mở phòng chat
	$scope.initChat = function() {
		/** */
		let sender = $scope.sender;
		let receiver = $scope.receiver;
		/** */
		let room = {
			//Người đang đăng nhập
			initator: sender.username,
			//Người đang đăng nhập và người được chọn để nhắn tin
			members: [sender.username, receiver.username],
			//Thời gian
			createAt: new Date(),
			updateAt: new Date()
		}

		//Open Chat Romm
		window.socket.initChat(room);
	}

	/**END SOCKET EVENT */




	/** DATA - LOAD DATA*/
	//LOAD ROOM   
	async function loadRooms() {
		const userId = JSON.parse(sessionStorage.getItem('usersession'));
		const user = await userApi.getById(userId);
		let page = await chatRoomApi.getPageByProperties(roomPagenum, 6, {
			"user.username": user.username
		});
		let rooms = page.content;
		let roomLength = rooms.length;
		for (let i = 0; i < roomLength; i++) {
			let room = rooms[i];
			room.messagePagenum = 1;
			let memberLength = room.members.length;
			let members = [];
			for (let j = 0; j < memberLength; j++) {
				let user = await userApi.getByProperties({ "username": room.members[j] });
				members.push(user);
				if (user.id !== userId) {
					room.partner = user;
				}
			}
			room.members = members;
		}
		roomPagenum++;
		return page.content;
	}

	async function loadMessageForRoom(room) {
		let page = await messageApi.getPageByProperties(room.messagePagenum, 10, {
			'room.id': room.id
		});
		room.messages = page.content.reverse();
		room.messagePagenum++;
		return room;
	}


	//LOAD MESSAGE
	async function loadMessageForRooms(rooms) {
		let roomLength = rooms.length;
		for (let i = 0; i < roomLength; i++) {
			let room = rooms[i];
			room = await loadMessageForRoom(room);
		}
		return rooms;
	}



	/**FUNCTION XỬ LÝ */
	$scope.getSendOrReceiver = function() {

	}


	/** -- EVENT */
	//Scroll List Room
	$scope.scrollRoom = function(obj_room) {

	}

	//Scroll Box message
	$scope.scrollMessage = function(obj_room) {

	}

	//Click Room 
	$scope.goRoom = function(id_room) {
		//Set roomChatActive
		$scope.roomChatActive = $scope.rooms.find(o => o.id === id_room);;
		console.log("nhantin__goRoom__setRoomActive")
		console.log($scope.roomChatActive)
		//Set receiver
		$scope.receiver = $scope.roomChatActive.partner;
		console.log("nhantin__goRoom__setReceiver")
		console.log($scope.receiver)

		//Mở Room Chat
		//$scope.initChat(); 
	}

	//Click Send
	$scope.goSend = function() {
		//Get nội dung tin nhắn
		let messageContent = angular.copy($scope.messageContent.value);
		console.log("nhantin__goSend__messageContent.value")
		console.log(messageContent)

		//Gửi tin nhắn
		$scope.sendMessage();

		//Render lên giao diện

		//Clear textbox chat
		$scope.messageContent.value = null;
	}
	//Keyup Message Content Send
	$scope.changeValueMessage = function() {
		let messageContent = angular.copy($scope.messageContent.value);
		//console.log("nhantin__changevalue__messageContent.value")
		//console.log(messageContent)
		if ((messageContent == "") | (messageContent == null)) {
			$scope.canSend = false;
		} else {
			$scope.canSend = true;
		}
	}

	/** -- INITIALIZE --*/
	$scope.initialize = async function() {
		// Dữ liệu người gửi
		$scope.sender = await $scope.userAPI.getById(JSON.parse(sessionStorage.getItem('usersession')))
		console.log("nhantin__init__sender")
		console.log($scope.sender);
		//Page All Chat
		let rooms = await loadRooms();
		//Room default
		if (rooms.length > 0) {
			//Room Active
			$scope.roomChatActive = rooms[0];
			//Receiver Active
			for (let m = 0; m < rooms[0].members.length; m++) {
				if (rooms[0].members[m].username != $scope.sender.username) {
					$scope.receiver = rooms[0].members[m];
					break;
				}
			}
		}
		console.log("nhantin__init__roomdefault(roomActive)")
		console.log($scope.roomChatActive)

		$scope.rooms = await loadMessageForRooms(rooms);
		$scope.$digest();
	}
	$('#listRoom').scroll(async function(e) {
		let elm = e.currentTarget
		if ($(e.currentTarget).scrollTop() + $(e.currentTarget).innerHeight() >= $(e.currentTarget)[0].scrollHeight - 2) {
			let rooms = await loadRooms();
			let roomTemps = await loadMessageForRooms(rooms);
			$scope.rooms = $scope.rooms.concat(roomTemps);
			$scope.$digest();
		}
	})

	$('#chatFrame').scroll(async function(e) {
		alert('asdfasd')
		console.log($(e.currentTarget).scrollTop() + $(e.currentTarget).innerHeight())
		console.log($(e.currentTarget)[0].scrollHeight)
		if ($(e.currentTarget).scrollTop() + $(e.currentTarget).innerHeight() <= $(e.currentTarget)[0].scrollHeight) {
			let rooms = await loadRooms();
			let roomTemps = await loadMessageForRooms(rooms);
			$scope.rooms = $scope.rooms.concat(roomTemps);
			$scope.$digest();
		}
	})

	 
	/*Phần chạy mặc định*/
	$scope.initialize();
});