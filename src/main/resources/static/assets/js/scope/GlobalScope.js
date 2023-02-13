window.serverAddress = 'http://localhost:8080/biglobby';

window.userApi = new GlobalAPI();
window.userApi.setNamespace(GlobalAPI.namespace.USER);

window.actionApi = new GlobalAPI();
window.actionApi.setNamespace(GlobalAPI.namespace.ACTION);

window.addressApi = new GlobalAPI();
window.addressApi.setNamespace(GlobalAPI.namespace.ADDRESS);

window.bcoinhistoryApi = new GlobalAPI();
window.bcoinhistoryApi.setNamespace(GlobalAPI.namespace.BCOIN_HISTORY)

window.bserviceHistoryApi = new GlobalAPI();
window.bserviceHistoryApi.setNamespace(GlobalAPI.namespace.BSERVICE_HISTORY);

window.bservicePriceApi = new GlobalAPI();
window.bservicePriceApi.setNamespace(GlobalAPI.namespace.BSERVICE_PRICE);

window.bserviceSubbannerApi = new GlobalAPI();
window.bserviceSubbannerApi.setNamespace(GlobalAPI.namespace.BSERVICE_SUBBANNER);

window.cardApi = new GlobalAPI();
window.cardApi.setNamespace(GlobalAPI.namespace.CARD);

window.cardSubbannerApi = new GlobalAPI();
window.cardSubbannerApi.setNamespace(GlobalAPI.namespace.CARD_SUBBANNER);

window.cartApi = new GlobalAPI();
window.cartApi.setNamespace(GlobalAPI.namespace.CART);

window.categoryApi = new GlobalAPI();
window.categoryApi.setNamespace(GlobalAPI.namespace.CATEGORY);

window.commentApi = new GlobalAPI();
window.commentApi.setNamespace(GlobalAPI.namespace.COMMENT);

window.displayfeehistoryApi = new GlobalAPI();
window.displayfeehistoryApi.setNamespace(GlobalAPI.namespace.DISPLAYFEE_HISTORY);

window.displayfeeApi = new GlobalAPI();
window.displayfeeApi.setNamespace(GlobalAPI.namespace.DISPLAYFEE);

window.failHistoryApi = new GlobalAPI();
window.failHistoryApi.setNamespace(GlobalAPI.namespace.FAIL_HISTORY);

window.followShopApi = new GlobalAPI();
window.followShopApi.setNamespace(GlobalAPI.namespace.FOLLOW_SHOP);

window.hideCardApi = new GlobalAPI();
window.hideCardApi.setNamespace(GlobalAPI.namespace.HIDECARD);

window.loveCardApi = new GlobalAPI();
window.loveCardApi.setNamespace(GlobalAPI.namespace.LOVECARD);

window.mybcoinApi = new GlobalAPI();
window.mybcoinApi.setNamespace(GlobalAPI.namespace.MYBCOIN);

window.mybserviceApi = new GlobalAPI();
window.mybserviceApi.setNamespace(GlobalAPI.namespace.MYBSERVICE);

window.myShopApi = new GlobalAPI();
window.myShopApi.setNamespace(GlobalAPI.namespace.MYSHOP);

window.newsApi = new GlobalAPI();
window.newsApi.setNamespace(GlobalAPI.namespace.NEWS);

window.orderDetailApi = new GlobalAPI();
window.orderDetailApi.setNamespace(GlobalAPI.namespace.ORDERDETAIL);

window.orderApi = new GlobalAPI();
window.orderApi.setNamespace(GlobalAPI.namespace.ORDER);

window.postApi = new GlobalAPI();
window.postApi.setNamespace(GlobalAPI.namespace.POST);

window.problemApi = new GlobalAPI();
window.problemApi.setNamespace(GlobalAPI.namespace.PROBLEM);

window.productApi = new GlobalAPI();
window.productApi.setNamespace(GlobalAPI.namespace.PRODUCT);

window.productHistoryApi = new GlobalAPI();
window.productHistoryApi.setNamespace(GlobalAPI.namespace.PRODUCT_HISTORY);

window.registerActiveApi = new GlobalAPI();
window.registerActiveApi.setNamespace(GlobalAPI.namespace.REGISTE_RACTIVE);

window.repCommentApi = new GlobalAPI();
window.repCommentApi.setNamespace(GlobalAPI.namespace.REPCOMMENT);

window.reportApi = new GlobalAPI();
window.reportApi.setNamespace(GlobalAPI.namespace.REPORT);

window.roleApi = new GlobalAPI();
window.roleApi.setNamespace(GlobalAPI.namespace.ROLE);

window.shareApi = new GlobalAPI();
window.shareApi.setNamespace(GlobalAPI.namespace.SHARE);

window.statusApi = new GlobalAPI();
window.statusApi.setNamespace(GlobalAPI.namespace.STATUS);

window.reviewApi = new GlobalAPI();
window.reviewApi.setNamespace(GlobalAPI.namespace.REVIEW);

window.repReviewApi = new GlobalAPI();
window.repReviewApi.setNamespace(GlobalAPI.namespace.REP_REVIEW);

window.mailApi = new GlobalAPI();
window.mailApi.setNamespace(GlobalAPI.namespace.MAIL);

window.uploadApi = new GlobalAPI();
window.uploadApi.setNamespace(GlobalAPI.namespace.UPLOAD);

window.productApi = new GlobalAPI();
window.productApi.setNamespace(GlobalAPI.namespace.PRODUCT);

window.userSchemaApi = new GlobalAPI();
window.userSchemaApi.setNamespace(GlobalAPI.namespace.USERSCHEMA);

window.chatRoomApi = new GlobalAPI();
window.chatRoomApi.setNamespace(GlobalAPI.namespace.CHAT_ROOM);

window.messageApi = new GlobalAPI();
window.messageApi.setNamespace(GlobalAPI.namespace.MESSAGES);


window.currentUser = async function() {
    const userId = JSON.parse(sessionStorage.getItem('usersession'));
    let user = await userApi.getById(userId).catch(err => {
        console.log(err);
    });
    console.log(user);
    return user;
}





window.removeItemFromArray = function(arr, item) {
    const index = arr.indexOf(item);
    if (index > -1) {
        arr.splice(index, 1);
    }
    return arr;
}

window.socket = new SocketClient();

window.setCookie = function(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

window.getCookie = function(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

async function checkCookie() {
    let login = sessionStorage.getItem('usersession');
    let shopSess = sessionStorage.getItem('shopsession');
    let token = getCookie('_biglobby_sesstoken_');
    let userData = window.getCookie('_biglobby_udata_');

    if (userData && userData.length > 0) {
        let res = await GlobalAPI.validateUserData({ '_biglobby_udata_': userData }).catch(err => {
            if (err.status === 401) {
                setCookie('_biglobby_udata_', '', -30);
                sessionStorage.removeItem("usersession");
                sessionStorage.removeItem("token");
                location.href = 'http://localhost:8080/user/index.html#!/biglobby/dangnhap';
                return;
            }
        });
        if (!res) {
            return;
        }
        let user = res.user;
        let rooms = res.rooms;
        if (user) {
            sessionStorage.setItem('usersession', JSON.stringify(user.id));
            window.socket.login(user.username, rooms);
            let shop = await userApi.getCollections(user.id, 'shop');
            sessionStorage.setItem('shopsession', JSON.stringify(shop));
            return;
        }
        sessionStorage.removeItem("shopsession");
        sessionStorage.removeItem("usersession");
        location.href = 'http://localhost:8080/user/index.html#!/biglobby/dangnhap';
        return;
    }

    if (!token || !login || !shopSess) {
        sessionStorage.removeItem("usersession");
        sessionStorage.removeItem("shopsession");
        location.href = 'http://localhost:8080/user/index.html#!/biglobby/dangnhap';
        return;
    }

}
checkCookie();






/**
 * Chat seperator
 */