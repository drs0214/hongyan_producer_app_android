window.onload=function(){
    // 控制rem值    
    
    var width = document.documentElement.clientWidth!=0?document.documentElement.clientWidth:window.screen.availWidth;
    document.documentElement.style.fontSize=width/7.2+'px';
}