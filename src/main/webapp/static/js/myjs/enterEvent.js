//按下Enter键调用的函数
document.onkeydown = function(e){
    var ev = document.all ? window.event : e;
    if(ev.keyCode == 13) {
    	document.getElementById("search").click();
    }
}