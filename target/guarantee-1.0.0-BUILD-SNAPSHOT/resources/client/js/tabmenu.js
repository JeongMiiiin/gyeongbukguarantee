// 20201211-tabmenu.js
$("document").ready(function(){
    
    $(".tab-content").eq(0).show();
    $(".tabmenu ul li").eq(0).addClass("on")

    $(".tabmenu ul.tab_box li.tab_btn").click(function(){
        let i = $(this).index();
        $(".tab-content").hide().eq(i).show();
        $(".tabmenu ul li").removeClass("on").eq(i).addClass("on")

    })

})