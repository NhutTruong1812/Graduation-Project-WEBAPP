$(document).ready(function(){
    $('#dropdown__list').click(function(){ 
        $('.header__profile_list').slideToggle();  
        var click = document.getElementById("icon__down");  
        var up = document.getElementById("icon__up");  
         if(click.style.display ==="none") {  
            click.style.display ="block";  
            up.style.display ="none";
         } else {  
            click.style.display ="none";  
            up.style.display ="block";
        }   
    }); 
 

    // $('#main__center__panel__dropdown').click(function(){ 
    //     $('.main__center__panel__dropdown__list').slideToggle();   
    // }); 
})