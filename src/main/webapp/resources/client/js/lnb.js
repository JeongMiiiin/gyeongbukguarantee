$(function() {
    $('.lnb .lnb_small').css('display', 'none');
    $('.lnb>li.on>a').siblings('.lnb_small').css('display', 'block');

    $(".lnb>li>a").click(function() {
        $(this).next().stop().slideToggle(300);
        $("lnb>li>a").not(this).next().stop().slideUp(300);
        return false;
    });

    $("lnb>li>a").eq(0).trigger("click");
});