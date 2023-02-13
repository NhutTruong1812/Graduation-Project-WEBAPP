var app = angular.module("user-app", []);
app.controller("quanlyhoadon-ctrl", function ($scope) {
    $scope.demo1z = 'demo zz';
})
// BÁN > TẠO QR XÁC NHẬN /////////////////////////////////////////////////
//
var qrban = document.getElementsByClassName('qrban');
if (qrban.length > 0) {
    var fullcvr = document.getElementsByClassName('full-cvr')[0];
    hiddenit(fullcvr);
    for (let k = 0; k < qrban.length; k++) {
        taoqrban(qrban[k], fullcvr);
    }
} else {
    console.log('none of qrban')
}
//
function taoqrban(qrban, fullcvr) {
    let qrban4 = qrban.getElementsByClassName('qrban4')[0];
    let qrapi = qrban.getElementsByClassName('qrapi')[0];
    if (qrban4.innerText == 'Đang giao dịch' || qrban4.innerText == 'đang giao dịch') {
        let qrban1 = qrban.getElementsByClassName('qrban1')[0];
        let qrapitemp = '';
        //
        if (qrban1.getAttribute('qrdata') != '') {
            qrapitemp = qrapi.getAttribute('hreftemp') + qrapitemp + qrban1.getAttribute('qrdata') + 'xacnhan';
        } else {
            console.log('out game');
        }
        //
        if (qrapitemp != '') {
            qrapi.onclick = function () {
                hiddenit(fullcvr);
                taoqrcode(qrapitemp);
            };
            // qrapi.href = qrapitemp;
            qrapi.innerText = 'TẠO'
        } else {
            qrapi.href = '#';
            qrapi.innerText = 'kết thúc giao dịch'
        }
    } else {
        qrapi.href = '#';
        qrapi.innerText = '[x]'
    }
}
//
var fullcvr = document.getElementsByClassName('full-cvr');
if (qrban.length > 0) {
    for (let k = 0; k < fullcvr.length; k++) {
        fullcvr[k].onclick = function () {
            hiddenit(fullcvr[k]);
        };
    }
} else {
    console.log('none of fullcvr')
}
//
function hiddenit(item) {
    let wrapitem = item.parentNode;
    if (wrapitem.style.display == '') {
        wrapitem.style.display = 'none';
    } else {
        wrapitem.style.display = '';
    }
}
//
var qrbanimg = document.getElementById('qrban-img');
function taoqrcode(qrdata) {
    qrbanimg.src = qrbanimg.getAttribute('tempapi') + qrdata;
}
// BÁN ^ TẠO QR XÁC NHẬN /////////////////////////////////////////////////
//
