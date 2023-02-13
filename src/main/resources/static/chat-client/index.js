let client = new ChatClient(); //("https://biglobby.herokuapp.com/biglobby");
let currentUser = {};
let pageConversation = 0;
let selectedRoom = {};

function initChatRoom(othId) {
    let data = {
        initator: currentUser.id,
        members: [currentUser.id, othId],
        createAt: new Date(),
        updateAt: new Date(),
    };
    client.initChatRoom(data);
}


function fillFormData(selector, data) {
    let form = document.querySelector(selector);
    let keys = Object.keys(data);
    let keyLength = keys.length;
    for (let i = 0; i < keyLength; i++) {
        if (keys[i] === '$$hashKey') {
            continue;
        }
        let el = form.querySelector(`[name="${keys[i]}"]`);
        el.value = data[keys[i]];
    }
}

function getFormData(selector) {
    return $(selector).serializeArray().reduce(function(obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}

async function register() {
    const data = getFormData('#formRegister');
    const res = await client.register(data);
    if (res.username) {
        alert("Đăng ký thành công!");
    } else {
        alert("Đăng ký thất bại!");
    }
    console.log(res);
}

async function login() {
    const data = getFormData('#formLogin');
    let res = await client.login(data.username);
    console.log(res);
    if (res.status === 200) {
        currentUser = {
            id: res.userId,
            username: res.username
        };
        alert("Đăng nhập thành công!");
    } else {
        alert("Đăng nhập thất bại!");
    }
}

function sendMessage() {
    let messageVal = $('#message').val();
    let data = {
        message: messageVal,
        type: 'TEXT',
        senderId: currentUser.id,
        roomId: selectedRoom.id,

    };
    readByRecipients: [currentUser.id],
        createAt: new Date(),
        updateAt: new Date()

    client.sendMessage(data);

    $('#messages').append(
        `
        <div class="pop cur">
            <span class="sender">
                ${data.senderId}
            </span>
            <span class="content">
                ${data.message}
            </span>
            <span class="time">
                ${new Date(data.createAt).toLocaleString()}
            </span>
        </div>
    `
    );
    $('#message').val('');
}


$(function() {

    $('#formRegister').submit(async function(e) {
        e.preventDefault();
        await register();
        fillFormData('#formRegister', { username: '', fullname: '', email: '' });
    });

    $('#formLogin').submit(async function(e) {
        e.preventDefault();
        await login();
        fillFormData('#formLogin', { username: '' });
    });

    $('#btnRefresh').click(async function(e) {
        let users = await client.getUsers();
        users = users.filter(o => o.id !== currentUser.id);
        let htmls = users.map(o => {
            return `
                <li> ${o.fullname} <button class=" mx-2 btn btn-success" onclick="initChatRoom('${o.id}')">Chat</button></li>
            `;
        });
        $('#users').html(htmls);
    });

    $('#btnSend').click(function(e) {
        sendMessage();
    });

    $('#joinSomething').click(async function() {
        client.sb('asdfasdf', function(res) {
            alert('asdfasdf');
        })
    });

});









// client.setOnLogin(function(data) {
//     if (data.status === 200) {
//         currentUser = {
//             id: data.userId,
//             username: data.username
//         };
//         alert("Đăng nhập thành công!");
//     } else {
//         alert("Đăng nhập thất bại!");
//     }

// });

client.setOnInitChatRoom(async function(data) {
    $('#chat-title').text(data.id);
    let messages = await client.getConversation(data.id, pageConversation, 10);
    let getMessages = messages;
    messages = getMessages.reverse();
    let size = messages.length;
    let htmls = '';
    for (let i = 0; i < size; i++) {
        let time = messages[i].createAt;
        messages[i].createAt = new Date(time).toLocaleString();
        if (messages[i].senderId === currentUser.id) {
            htmls += `
                <div class="pop cur">
                    <span class="sender">
                        ${messages[i].senderId}
                    </span>
                    <span class="content">
                        ${messages[i].message}
                    </span>
                    <span class="time">
                        ${messages[i].createAt}
                    </span>
                </div>
            `;
        } else {
            htmls += `
                <div class="pop oth">
                    <span class="sender">
                        ${messages[i].senderId}
                    </span>
                    <span class="content">
                        ${messages[i].message}
                    </span>
                    <span class="time">
                        ${messages[i].createAt}
                    </span>
                </div>
                `;
        }
    }
    $('#messages').html(htmls);
    selectedRoom = data;
});


client.setOnChatAction({

    receive: (data) => {
        console.log(data);
        if (data.senderId !== currentUser.id) {
            $('#messages').append(
                `
                <div class="pop oth">
                    <span class="sender">
                        ${data.senderId}
                    </span>
                    <span class="content">
                        ${data.message}
                    </span>
                    <span class="time">
                        ${data.createAt}
                    </span>
                </div>
            `
            );
        }
    }
});