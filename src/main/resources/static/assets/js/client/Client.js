class SocketClient {
    static enpoint = 'biglobby';
    static appPrefix = 'app';
    subscribs = [];
    socket;
    /**
     * Brokers to listen for change
     */
    BROKER = {
        CHAT: 'chat',
        USER: 'users',
        CARD: 'card',
        PRODUCT: 'product',
        POST: 'post',
        NEWS: 'news',
        ORDER: 'order',
        LOVECARD: 'lovecard',
        COMMENT: 'comment',
        REPCOMMENT: 'repcomment',
        SHARE: 'share',
        FOLLOW_SHOP: 'followshop',
        FAIL_HISTORY: 'failhistory',
        REPORT_CARD: 'report',
        SHOP: 'shop'
    };

    /**
     * Action of brokers
     */
    ACTION = {
        create: 'onCreate',
        update: 'onUpdate',
        delete: 'onDelete',
    };

    users = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    news = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    product = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    post = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    card = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    order = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    lovecard = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    comment = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    repcomment = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    share = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };
    //truongnvn
    followshop = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    failhistory = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    report = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    shop = {
        onCreate: undefined,
        onUpdate: undefined,
        onDelete: undefined
    };

    /**
     * Chat action
     */
    chat = {
        onInit: undefined,
        onMessage: undefined,
    };

    constructor(hostAddress = window.serverAddress) {
        this.hostAddress = hostAddress;
    }

    setOnUser(funcs) {
        this.users = funcs;
    }

    setOnNews(funcs) {
        this.news = funcs;
    }

    setOnCard(funcs) {
        this.card = funcs
    }

    setOnProduct(funcs) {
        this.product = funcs;
    }

    setOnPost(funcs) {
        this.post = funcs;
    }

    setOnOrder(funcs) {
        this.order = funcs;
    }

    setOnLoveCard(funcs) {
        this.lovecard = funcs;
    }

    setOnComment(funcs) {
        this.comment = funcs;
    }

    setOnRepComment(funcs) {
        this.repcomment = funcs;
    }

    setOnShare(funcs) {
        this.share = funcs;
    }

    setOnFollow(funcs) {
        //truongnvn
        this.followshop = funcs;
    }

    setOnFailHistory(funcs) {
        this.failhistory = funcs;
    }

    setOnReport(funcs) {
        this.report = funcs;
    }

    setOnShop(funcs) {
        this.shop = funcs;
    }

    setOnChat(funcs) {
        this.chat = funcs;
    }

    send(destination, header = {}, data = {}) {
        this.socket.send(destination, header, JSON.stringify(data));
    }

    sendMessage(data) {
        this.send(`/${SocketClient.appPrefix}/${this.BROKER.CHAT}/room`, {}, data);
    }

    initChat(data) {  
		console.log(`/${SocketClient.appPrefix}/${this.BROKER.CHAT}/initChat`)
        this.send(`/${SocketClient.appPrefix}/${this.BROKER.CHAT}/initChat`, {}, data);
    }

    subscribe(destination, callback) {
        let subscribed = this.subscribs.find(o => o.destination === destination);
        if (!subscribed) {
            let subscribe = this.socket.subscribe(destination, callback);
            this.subscribs.push({
                subscribe: subscribe,
                destination: destination
            });
        	
        }
    }

    unsubscribe(destination, callback = undefined) {
        let subscribe = this.subscribs.find(o => o.destination === destination);
        if (subscribe) {
            subscribe.unsubscribe();
            this.subscribs = removeItemFromArray(this.subscribs, subscribe);
        }
        if (callback && typeof callback === 'function') {
            callback();
        }
    }

    eventListener(broker) {
        const parent = this;
        for (const [key, value] of Object.entries(parent.ACTION)) {
            parent.subscribe(`/${broker}/${key}`, function(res) {
                const data = JSON.parse(res.body);
                parent[broker][value](data);
            });
        }
    }

    /**
     * When connection opened
     */
    connection(frame, username, rooms) {
        const parent = this;
        if (rooms) {
            let roomLength = rooms.length;
            for (let i = 0; i < roomLength; i++) {
                parent.subscribe(`/${parent.BROKER.CHAT}/room/${rooms[i].id}`, function(res) {
                    const data = JSON.parse(res.body);
                    //Action when event is triggered 
                    parent.chat.onMessage(data);
                });
            }
        }


        /**
         * Listen for chat messages in all rooms joined
         */

        /**
         * Listen for someone invite into room
         */
        parent.subscribe(`/${parent.BROKER.CHAT}/invite/${username}`, function(res) {
            const data = JSON.parse(res.body);
            //Listen for message in room just joined
            parent.subscribe(`/${parent.BROKER.CHAT}/room/${data.id}`, function(res) {
                const subdata = JSON.parse(res.body);
                //Action when event is triggered
                parent.chat.onMessage(subdata);
            });
            //Action when event is triggered
            parent.chat.onInit(data);
        });


        /**
         * Listen for broker action
         */
        for (const [key, value] of Object.entries(this.BROKER)) {
            if (value === this.BROKER.CHAT) {
                continue;
            }
            this.eventListener(value);
        }
    }

    /**
     * Login to server
     */
    login(uname, rooms) {
        const sockJS = new SockJS(this.hostAddress);
        this.socket = Stomp.over(sockJS);
        this.socket.reconnect_delay = 5000;
        this.socket.debug = () => {}
        const parent = this;
        this.socket.connect({
            username: uname
        }, function(frame) {
            parent.connection(frame, uname, rooms);
        });
    }
}