let printUtil = {

		zoomValue : 100
		,targetContent : null
		, callback: null
		, isClose: false
		, printSize: 'sizeA4'
		, direction: 'column'
		
		,printStart : function( targetList, zoom, isClose, printSize, direction, callback ){
		
			var _this = this;
			if( callback != null ){
				this.callback = callback;
			}
			if(isClose != null) this.isClose = isClose;
			if( zoom != null ) {
				_this.zoomValue = (zoom/100);
			}
			
			if(printSize != null ) _this.printSize = 'size' + printSize;
			if(printSize != null && direction != null ) _this.direction = direction;
			
			this.targetContentList = targetList;

			//print 세팅함수 호출
			window.onbeforeprint = function () {				
				_this.setPrint();
				
			}
			
			//print후 콘텐츠 원복 함수 호출
			window.onafterprint = function () {
				_this.unsetPrint();
			}
			
			window.print();
			
		}

		//print contents 세팅
		,setPrint : function(){
			var _this = this;
			

			//print Contents 담을 area Create
			var printArea = $(document.body).clone().attr('id','printArea');
			$('html').append(printArea);
			$('#printArea').addClass(_this.printSize);
			$('#printArea').addClass(_this.direction);
			$('#printArea').css('zoom',_this.zoomValue);
			$('#printArea').find('> *').css('display','none','important');
			
			var printSizeArea = '<div id="printSizeArea"></div>'
			$('#printArea').append(printSizeArea);
			
			//기존 위치 표시할 div Create 및 printArea에 Contents add
			$(_this.targetContentList).each(function(idx){
				var wrapContent ='<div id="printOriginPos' + idx + '"></div>'; 
				$(this).wrap(wrapContent);
				$('#printSizeArea').append( $(this) );
			});
			
			$(document.body).not('#printArea').css('display','none','important');
			
			var  browser = navigator.userAgent.toLowerCase();
			if ( -1 != browser.indexOf('trident') ){
				//웹 브라우저 컨트롤 생성
                var webBrowser = '<OBJECT ID="previewWeb" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';

                //웹 페이지에 객체 삽입
                $('#printArea').append( webBrowserv );
			}
			
		}  
			
		//print contents 원복
		,unsetPrint : function(){
			var _this = this;
			
			//contents 기존위치로 이동 및 위치 표시를 위한 div Delete 마지막에는 printArea 삭제
			var cnt = ($(_this.targetContentList).length - 1);
			$(_this.targetContentList).each(function(idx){
				$('#printOriginPos' + idx ).append( $(this) );
				$(this).unwrap();
				
				if(idx == cnt){
					$(document.body).css('display','');
					$('#printArea').remove();
					if( _this.callback != null ){
						_this.callback();
					}
					
				}
				
			})
			
		}
		
		,onCompletePrint : function() {
		}

}