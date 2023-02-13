const cors = require('cors');
const bodyParser = require("body-parser");
const app = require("express")();
app.use(cors({ origin: '*' }));
app.use(bodyParser.json());

app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});
const httpServer = require("http").createServer(app);
const options = {
    cors: {
        origin: "*",
        methods: ["GET", "POST", "PUT", "PATCH", "DELETE"]
    }
};
const io = require("socket.io")(httpServer, options);
const np = {
    users: io.of("/users"),
    messages: io.of("/messages"),
    rooms: io.of("/rooms"),
}

np.users.on("connection", socket => {
    console.log("connect to users");

    socket.on("findUserById", (data) => {

    });
    socket.on("findAllUser", (data) => {

    })

    socket.on("createUser", (data) => {

    });
    socket.on("updateUser", (data) => {

    });
    socket.on("deleteUser", (data) => {

    });

});

np.messages.on("connection", socket => {
    console.log("connection");
    socket.on("sendMessage", (data) => {
        np.messages.to(data.room).emit("receivedMessage", data.message);
        console.log("sent messages " + data.message);
    });

    socket.on("sendMessage", (data) => {
        io.sockets.in(data.room).emit("receivedMessage", data.message);
        console.log("someone sent message " + data.message);
    })

    socket.on('send info', (message) => {
        console.log(message);
    })
});

np.rooms.on('connection', socket => {

    socket.on("initiate", (data) => {
        console.log("init room");
    });
    socket.on("joinRoom", (data) => {
        socket.join(data.room);
        console.log("joinroom");
    });

    socket.on("subscribeRoom", (data) => {

    });

    socket.on("unsubscribeRoom", (data) => {

    });
});

httpServer.listen(3000, () => console.log("listening on port 3000"));