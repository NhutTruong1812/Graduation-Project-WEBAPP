var tabs_1 = document.querySelector('.nav-link__1')
var tabs_2 = document.querySelector('.nav-link__2')
var tabs_3 = document.querySelector('.nav-link__3')
var tabs_4 = document.querySelector('.nav-link__4')
var tabs_5 = document.querySelector('.nav-link__5')
var tabs_6 = document.querySelector('.nav-link__6')

function tabs__1(){
    tabs_1.classList.add('active')
    tabs_2.classList.remove('active')
    tabs_3.classList.remove('active')
    tabs_4.classList.remove('active')
    tabs_5.classList.remove('active')
    tabs_6.classList.remove('active')
}

function tabs__2(){
    tabs_2.classList.add('active')
    tabs_1.classList.remove('active')
    tabs_3.classList.remove('active')
    tabs_4.classList.remove('active')
    tabs_5.classList.remove('active')
    tabs_6.classList.remove('active')
}

function tabs__3(){
    tabs_3.classList.add('active')
    tabs_1.classList.remove('active')
    tabs_2.classList.remove('active')
    tabs_4.classList.remove('active')
    tabs_5.classList.remove('active')
    tabs_6.classList.remove('active')
}

function tabs__4(){
    tabs_4.classList.add('active')
    tabs_1.classList.remove('active')
    tabs_3.classList.remove('active')
    tabs_2.classList.remove('active')
    tabs_5.classList.remove('active')
    tabs_6.classList.remove('active')
}

function tabs__5(){
    tabs_5.classList.add('active')
    tabs_1.classList.remove('active')
    tabs_3.classList.remove('active')
    tabs_4.classList.remove('active')
    tabs_2.classList.remove('active')
    tabs_6.classList.remove('active')
}

function tabs__6(){
    tabs_6.classList.add('active')
    tabs_1.classList.remove('active')
    tabs_3.classList.remove('active')
    tabs_4.classList.remove('active')
    tabs_2.classList.remove('active')
    tabs_5.classList.remove('active')
}

/* menu trái xembaidangcuahangkhac */ 
var cuaHang_1 = document.querySelector('.cuahang__1')
var cuaHang_2 = document.querySelector('.cuahang__2')

function CuaHang_1(){
    cuaHang_1.classList.add('active')
    cuaHang_2.classList.remove('active')
}

function CuaHang_2(){
    cuaHang_2.classList.add('active')
    cuaHang_1.classList.remove('active')
}
