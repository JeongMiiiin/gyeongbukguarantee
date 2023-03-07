parsingControl = {
	
	trim: function (str) {
        return str ? str.replace(/(^\s*)|(\s*$)/g, '') : str;
    }
	
	//문자열형태의 Obj -> object로 변환
	,parseStrToObj : function( str, obj ){

        if( !obj ) {
            str = $.trim(str);
            obj = {};
            str = str.substr( 1, str.length-2 ); // 맨앞, 맨뒤 {} 제거.
        }

        // 공백제거
        let mark1 = /\s+[:]\s+|\s+[:]|[:]\s+/g;
        let mark2 = /\s+[,]\s+|\s+[,]|[,]\s+/g;
        let mark3 = /\s+[{]\s+|\s+[{]|[{]\s+/g;
        let mark4 = /\s+[}]\s+|\s+[}]|[}]\s+/g;
        str = str.replace( mark1, ':' );
        str = str.replace( mark2, ',' );
        str = str.replace( mark3, '{' );
        str = str.replace( mark4, '}' );
        str = $.trim( str );

        while( str.indexOf( "{" ) > -1 ){

            // value 추출.
            let stIdx = str.indexOf( "{" );
            let edIdx = str.indexOf( "}" )+1;
            let sub = str.slice( str.indexOf( "{" )+1, str.indexOf( "}" ) ); // "{" 다음부터 "}" 이전까지 잘라서 다시 파싱한다.
            let value = {};
            parsingControl.parseStrToObj( sub, value );

            // 원본 수정.
            str = cutStr( str, stIdx, edIdx );

            // key 추출
            edIdx = ( str.charAt( str.length-1 ) == ":" ) ? str.length-1 : str.indexOf( ":," );
            stIdx = str.lastIndexOf( ",", edIdx ) == -1 ? 0 : str.lastIndexOf( ",", edIdx )+1;
            let key = str.slice( stIdx, edIdx );

            // 원본 수정
            str = cutStr( str, stIdx, edIdx+2 );

            // key : value 대입.
            obj[ key ] = value;
        }

        str = str.replace( /[,]$/, '' );

        // 단일뎁스 String 을 obj 로 변환.
        let list = str.split( "," );
        let len = list.length;
        let o, k, v;
        let regQuotationMark = /^['|"]|['|"]$/g;
        while( len-- ){
            o = list[len].split( ":" );
            k = parsingControl.trim( o[0] );
            v = list[len].slice(k.length+1, list[len].length );

            if( isNaN(v) ){
                v = v.replace(regQuotationMark, '');
                obj[ k ] = parsingControl.trim( v );
            }else{
                v = parseFloat(v);
                obj[ k ] = v;
            }
        }

        return obj;
    }
    
    //Date 형태 -> 문자열로 변환
    ,parseDateToStr : function(date, standardTxt, twoDigitStatus){
		let yearVal = date.getFullYear();
		let monthVal = date.getMonth() + 1;
		let dateVal = date.getDate();
		if(twoDigitStatus != null && twoDigitStatus){
			monthVal = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
			dateVal = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();	
		}
		
		return (yearVal + standardTxt + monthVal + standardTxt + dateVal);
	}
}