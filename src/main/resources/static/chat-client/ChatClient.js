class ChatClient {
    subscribed = [];
    client;
    userId;
    onInitChatRoom;
    onReceiveMessage;
    chat = {
        receive: undefined
    }
    connection;
    constructor(hostAddress = 'http://localhost:8080/biglobby') {
        this.hostAddress = hostAddress;
    }

    setOnInitChatRoom(func) {
        this.onInitChatRoom = func;
    }

    setOnChatAction(funcs) {
        this.chat.receive = funcs.receive;
    }

    async register(data) {
        const res = fetch(`${this.hostAddress}/chat/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(res => {
            return res.json();
        }).catch(err => {
            throw err;
        });
        return res;
    }

    sendMessage(data) {
        this.client.send('/app/chat/sendMessage', {}, JSON.stringify(data));
    }

    sb(des, func) {
        this.client.subscribe(des, function(res) {
            func();
        });
    }


    async login(uname) {
        const res = await fetch(`${this.hostAddress}/chat/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: uname })
        }).then(res => res.json()).then(data => {
            if (data.status === 200) {
                const socket = new SockJS(this.hostAddress);
                this.client = Stomp.over(socket);
                const parent = this;
                console.log("connecting........");
                this.client.connect({}, function(frame) {
                    let rooms = data.rooms;
                    let roomLength = rooms.length;
                    for (let i = 0; i < roomLength; i++) {
                        let address = `/chat/room/${rooms[i].id}`;
                        if (!parent.subscribed.includes(address)) {
                            parent.client.subscribe(`/chat/room/${rooms[i].id}`, function(res) {
                                const subdata = JSON.parse(res.body);
                                parent.chat.receive(subdata);
                            });
                            parent.subscribed.push(address);
                        }
                    }
                    parent.client.subscribe(`/chat/initChatRoomResponse/${data.userId}`, function(res) {
                        const subdata = JSON.parse(res.body);
                        let address = `/chat/room/${subdata.id}`;
                        if (!parent.subscribed.includes(address)) {
                            parent.client.subscribe(`/chat/room/${subdata.id}`, function(res) {
                                const subbdata = JSON.parse(res.body);
                                parent.chat.receive(subbdata);
                            });
                            parent.subscribed.push(address);
                        }
                        parent.onInitChatRoom(subdata);
                    });
                });
            }
            return data;
        }).catch(err => {
            throw err;
        });
        return res;
    }

    initChatRoom(data) {
        this.client.send('/app/chat/initChatRoom', {}, JSON.stringify(data));
    }

    getUserById(uid) {
        this.client.send('/app/chat/initchat', {}, JSON.stringify({ userId: uid }));
    }

    async getUsers() {
        const res = await fetch(`${this.hostAddress}/chat/users`).then(res => res.json()).catch(err => {
            throw err;
        });
        return res;
    }

    async getConversation(roomId, page, limit) {
        const res = await fetch(`${this.hostAddress}/chat/rooms/${roomId}/conversation?page=${page}&limit=${limit}`)
            .then(res => {
                return res.json();
            })
            .catch(err => {
                throw err;
            });
        return res;
    }

}