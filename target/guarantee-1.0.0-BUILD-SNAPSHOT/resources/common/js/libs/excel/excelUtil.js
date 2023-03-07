let excelUtil = {
	s2ab : function(s){
		var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
        var view = new Uint8Array(buf);  //create uint8array as viewer
        for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
        return buf;
	}
	
	,excelDownload : function(infoList, dataList){
		let _this = this;
		
		let wb = XLSX.utils.book_new();
		
		wb.Props = {
			Title: infoList[0],
        	Subject: infoList[1],
        	Author: infoList[2],
        	Manager: infoList[3],
        	Company: infoList[4],
        	Category: infoList[5],
        	Keywords: infoList[6],
        	Comments: infoList[7],
        	LastAuthor: infoList[8],
        	CreatedDate: new Date()
		}
		
		let sheetName = infoList[9];
		
		wb.SheetNames.push(sheetName);
		
		let ws = XLSX.utils.aoa_to_sheet(dataList);
		
		wb.Sheets[sheetName] = ws;
		
		let wbout = XLSX.write(wb, {bookType:'xlsx', type: 'binary'});
		
		let fileName = infoList[10] + '.xlsx';
		
		saveAs(new Blob([_this.s2ab(wbout)],{type:"application/octet-stream"}), fileName);
		
	}
}