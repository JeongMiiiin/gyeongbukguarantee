/// 에러메시지 포멧 정의 ///
var NO_BLANK = "{name+은는} 필수항목입니다. 반드시 입력하십시요.";
var NOT_VALID = "{name+이가} 올바르지 않습니다. 다시 한 번 확인해 주십시요.";
var TOO_LONG = "{name}의 길이가 초과되었습니다.";

/// 스트링 객체에 메소드 추가 ///
String.prototype.trim = function(str) {
  str = this != window ? this : str;
  return str.replace(/^\s+/g,'').replace(/\s+$/g,'');
}

String.prototype.hasFinalConsonant = function(str) {
  str = this != window ? this : str;
  var strTemp = str.substr(str.length-1);
  return ((strTemp.charCodeAt(0)-16)%28!=0);
}

function josa(str,tail) {
  return (str.hasFinalConsonant()) ? tail.substring(0,1) : tail.substring(1,2);
}

function validate(form) {

	//var browser = navigator.userAgent; // 브라우져 확인

  for (i = 0; i < form.elements.length; i++ ) {
    var el = form.elements[i];
    el.value = el.value.trim(); // 크롬에서 파일업로드시 오류(확인필요-아래내용)

		// TODO : 트림 함수가 크롬에서 오류 나서 크롬이외에만 적용되도록 함(크롬에서 그대신 공백 한칸도 넘어감)
		//if (/chrome/i.test(browser)) // 크롬
		//{
			//크롬에서 아래 스크립트 오류 남
		//}else {
		//		el.value = el.value.trim();
		//}

    if (el.getAttribute("REQUIRED_CHK") != null) {
      if (el.value == null || el.value == "") {
        return doError(el,NO_BLANK);
      }
    }

    if (el.getAttribute("MAXBYTE") != null && el.value != "") {
      var len = 0;
      for(j=0; j<el.value.length; j++) {
        var str = el.value.charAt(j);
        len += (str.charCodeAt() > 128) ? 2 : 1
      }
      if (len > parseInt(el.getAttribute("MAXBYTE"))) {
        maxbyte = el.getAttribute("MAXBYTE");
        return doError(el,TOO_LONG);
      }
    }

    if (el.getAttribute("OPTION") != null && el.value != "") {
      if (!funcs[el.getAttribute("OPTION")](el)) return false;
    }
  }
  return true;
}

function doError(el,type) {
  var pattern = /{([a-zA-Z0-9_]+)\+?([가-힣]{2})?}/;
  var name = (hname = el.getAttribute("HNAME")) ? hname : el.getAttribute("NAME");
  pattern.exec(type);
  var tail = (RegExp.$2) ? josa(eval(RegExp.$1),RegExp.$2) : "";
  alert(type.replace(pattern,eval(RegExp.$1) + tail));

  focusType = el.getAttribute("FTYPE");
  if (focusType == null || focusType == "focus")
  {
    el.focus();
  }
  else if (focusType == "select")
  {
    el.select();
  }
  else if (focusType == "delete")
  {
    el.value = "";
  }
  else if (focusType == "none")
  {
  }

  return false;
}

/// 특수 패턴 검사 함수 매핑 ///
var funcs = new Array();
funcs['email'] = isValidEmail;
funcs['phone'] = isValidPhone;
funcs['userid'] = isValidUserid;
funcs['hangul'] = hasHangul;
funcs['number'] = isNumeric;
funcs['engonly'] = alphaOnly;
funcs['jumin'] = isValidJumin;
funcs['bizno'] = isValidBizNo;
funcs['num_per_page'] = isNumeric;
funcs['page_per_block'] = isNumeric;
funcs['table_width'] = isNumeric;
funcs['new_time'] = isNumeric;
funcs['jumin2'] = isValidJumin2;

/// 패턴 검사 함수들 ///
function isValidEmail(el) {
  var pattern = /^[_a-zA-Z0-9-\.]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$/;
  return (pattern.test(el.value)) ? true : doError(el,NOT_VALID);
}

function isValidUserid(el) {
  var pattern = /^[a-zA-Z]{1}[a-zA-Z0-9_]{4,10}$/;
  return (pattern.test(el.value)) ? true : doError(el,"{name+은는} 5자이상 11자 미만이어야 하고,\n 영문,숫자, _ 문자만 사용할 수 있습니다");
}

function hasHangul(el) {
  var pattern = /[가-힣]/;
  return (pattern.test(el.value)) ? true : doError(el,"{name+은는} 반드시 한글을 포함해야 합니다");
}

function alphaOnly(el) {
  var pattern = /^[a-zA-Z]+$/;
  return (pattern.test(el.value)) ? true : doError(el,NOT_VALID);
}

function isNumeric(el) {
  var pattern = /^[0-9]+$/;
  return (pattern.test(el.value)) ? true : doError(el,"{name+은는} 반드시 숫자로만 입력해야 합니다");
}


function isValidJumin(el) {
    var pattern = /^([0-9]{6})-?([0-9]{7})$/;
  var num = el.value;
    if (!pattern.test(num)) return doError(el,NOT_VALID);
    num = RegExp.$1 + RegExp.$2;

  var sum = 0;
  var last = num.charCodeAt(12) - 0x30;
  var bases = "234567892345";
  for (i=0; i<12; i++) {
    if (isNaN(num.substring(i,i+1))) return doError(el,NOT_VALID);
    sum += (num.charCodeAt(i) - 0x30) * (bases.charCodeAt(i) - 0x30);
  }
  var mod = sum % 11;
  return ((11 - mod) % 10 == last) ? true : doError(el,NOT_VALID);
}

function isValidJumin2(el) {
    var pattern = /^([0-9]{6})-?([0-9]{7})$/;
  var j1 = document.MakeForm.jumin1.value;
  var j2 = document.MakeForm.jumin2.value;
  //var num = el.value;
  var num = j1 + j2;
    if (!pattern.test(num)) return doError(el,NOT_VALID);
    num = RegExp.$1 + RegExp.$2;

  var sum = 0;
  var last = num.charCodeAt(12) - 0x30;
  var bases = "234567892345";
  for (i=0; i<12; i++) {
    if (isNaN(num.substring(i,i+1))) return doError(el,NOT_VALID);
    sum += (num.charCodeAt(i) - 0x30) * (bases.charCodeAt(i) - 0x30);
  }
  var mod = sum % 11;
  return ((11 - mod) % 10 == last) ? true : doError(el,NOT_VALID);
}

function isValidBizNo(el) {
    var pattern = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/;
  var num = el.value;
    if (!pattern.test(num)) return doError(el,NOT_VALID);
    num = RegExp.$1 + RegExp.$2 + RegExp.$3;
    var cVal = 0;
    for (var i=0; i<8; i++) {
        var cKeyNum = parseInt(((_tmp = i % 3) == 0) ? 1 : ( _tmp  == 1 ) ? 3 : 7);
        cVal += (parseFloat(num.substring(i,i+1)) * cKeyNum) % 10;
    }
    var li_temp = parseFloat(num.substring(i,i+1)) * 5 + '0';
    cVal += parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2));
    return (parseInt(num.substring(9,10)) == 10-(cVal % 10)%10) ? true : doError(el,NOT_VALID);
}

function isValidPhone(el) {
  var pattern = /^([0]{1}[0-9]{1,2})-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
  if (pattern.exec(el.value)) {
    if(RegExp.$1 == "011" || RegExp.$1 == "016" || RegExp.$1 == "017" || RegExp.$1 == "018" || RegExp.$1 == "019") {
      el.value = RegExp.$1 + "-" + RegExp.$2 + "-" + RegExp.$3;
    }
    return true;
  } else {
    return doError(el,NOT_VALID);
  }
}

/***************** sample form ******************************************************************************************************
<script language="javascript" src="../js/validate.js"></script>
<form name="test" action="regist.php" method="post" onSubmit="return validate(this)">
<input type="hidden" name="test" value="" FTYPE="none"> <!-- 포커스 형태 -->
이름 : <input type='text' name="username" size='13'maxbyte='10' REQUIRED_CHK  HNAME='이름'>&nbsp;<br>
email : <input type='text' name="email" size='40' maxbyte='40' option='email' REQUIRED_CHK  HNAME='이메일'>&nbsp;<br>
주민번호 : <input type='text' name="jumin" size='13' maxlength='13' hname='주민번호'  maxbyte='13' option="jumin" REQUIRED_CHK>&nbsp;<br>
<input type=image src='images/write.gif' onClick='submit' align=absmiddle>
</FORM>

<fieldset> 태그 들어가면 본 스크립트 처리 안됨

# 스크립트 submit
<input type="button" name="ok" value="저장" class="btn1_blue"  onClick="return check(document.sendFrm);" />
function check(frm){
	 if(!validate(frm)) return false;
	 frm.submit();
}
************************************************************************************************************************************/