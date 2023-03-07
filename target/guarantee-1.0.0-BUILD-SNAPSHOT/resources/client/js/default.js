//// 숫자에 콤마 표시 및 숫자체킹
 function commaChk(obj){

    num = obj.value;
    num=new String(num);
    num=num.replace(/,/gi,"");
    return comma(obj,num);
 }

 function comma(obj,num){

        var sign="";
        if(isNaN(num)) { alert("숫자만 입력 가능합니다."); obj.value=''; obj.focus(); return 0}

        if(num==0) return num

        if(num<0){
            num=num*(-1)
            sign="-"
        }
        else{
            num=num*1 //처음 입력값이 0부터 시작할때 이것을 제거한다.
        }

        num = new String(num)
        temp=""
        co=3
        num_len=num.length

      while (num_len>0){

          num_len=num_len-co;

         if(num_len<0){
              co=num_len+co;num_len=0
         }
         temp=","+num.substr(num_len,co)+temp

      }

       obj.value = sign+temp.substr(1);
 }

//// 숫자에 콤마 표시 및 숫자체킹 끝

 function checkNum(obj) // 아라비아 숫자 체크....
 {
      for(var i=0; i<obj.value.length;i++)
      {
            if(obj.value.substring(i,i+1) < "0" || obj.value.substring(i,i+1) > "9")
            {
                 alert("숫자만 입력 가능합니다.");
                 obj.value = '';
                 obj.focus();
                 return false;
            }
      }//end for

      return true;
 }

//콤마 찍기
function commaN(str) {
 str = String(str);
return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}
 
// 콤마 제거
function uncommaN(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}