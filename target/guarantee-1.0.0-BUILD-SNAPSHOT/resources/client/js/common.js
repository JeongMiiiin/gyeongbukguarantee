let isMobile = /iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson|LG|SAMSUNG|Samsung/i.test(navigator.userAgent)

function detectBrowser(){
    // 브라우저 및 버전을 구하기 위한 변수들.
    var agent = navigator.userAgent.toLowerCase(),
        name = navigator.appName,
        agentType, deviceClass;
    
    // MS 계열 브라우저를 구분하기 위함.
    if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1 || agent.indexOf('edge/') > -1) {
        agentType = 'ie';
        if(name === 'Microsoft Internet Explorer') { // IE old version (IE 10 or Lower)
            agent = /msie ([0-9]{1,}[\.0-9]{0,})/.exec(agent);
            //agentType += parseInt(agent[1]);
        } else { // IE 11+
            if(agent.indexOf('trident') > -1) { // IE 11
                //agentType += 11;
            } else if(agent.indexOf('edge/') > -1) { // Edge
                agentType = 'edge';
            }
        }
    } else if(agent.indexOf('safari') > -1) { // Chrome or Safari
        if(agent.indexOf('opr') > -1) { // Opera
            agentType = 'opera';
        } else if(agent.indexOf('chrome') > -1) { // Chrome
            agentType = 'chrome';
            if(agent.indexOf('samsungbrowser') != -1){
                agentType = 'SamsungBrowser';
            }
            if(agent.indexOf('whale') != -1){
            	agentType = 'whale';
            }
            
        } else { // Safari
            //agentType = 'safari';
            if(agent.indexOf("iphone") != -1){
                agentType = 'iphone';
            }else if(agent.indexOf("ipad") != -1 || (navigator.platform === "MacIntel" && typeof navigator.standalone !== "undefined")){
                agentType = 'ipad';
            }else if(agent.indexOf("mac") != -1){
                if(navigator.vendor.indexOf('Apple Computer, Inc.') != -1 ){
                	agentType = 'Mac';
                } else if (navigator.vendor.indexOf('Google Inc.') != -1 ){
                	agentType = 'MacChrome';
                }
            }
        }
    } else if(agent.indexOf('firefox') > -1) { // Firefox
        agentType = 'firefox';
    }
    // IE: ie7~ie11, Edge: edge, Chrome: chrome, Firefox: firefox, Safari: safari, Opera: opera
    //document.getElementsByTagName('html')[0].className = agentType;

    //set attribute - browser type
    $( "body" ).attr( "data-agent" , agentType );

    //set attribute - pc/mobile type
    deviceClass = isMobile ? "mobile" : "pc";
    if(agentType == 'ipad'){
        deviceClass = 'mobile';
    }
    $( "body" ).attr( "data-pc" , deviceClass );

    return agentType;

}

$(function(){
	detectBrowser();
})