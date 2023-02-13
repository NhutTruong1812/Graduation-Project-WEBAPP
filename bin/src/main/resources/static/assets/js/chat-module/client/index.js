let socket = io.connect("ws://localhost:3000/messages");

socket.on("connect", () => {
    console.log("connection established");
})
document.querySelector("#btnJoin").addEventListener("click", () => {
    let room = document.querySelector("#room").value;
    socket.emit("joinRoom", {
        room: room,
    });
});

document.querySelector("#btnSend").addEventListener("click", () => {
    let message = document.querySelector("#message").value;
    let room = document.querySelector("#room").value;
    socket.emit("sendMessage", {
        room: room,
        message: message
    });
});

socket.on("receivedMessage", (message) => {
    console.log("someone sent you: " + message);
    document.querySelector("#messages").append(message + "\n");
});