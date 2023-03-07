/** 작성자 JM
 *  작성일 22/05/12
 */
 
 let TimeControl = {
	/*
	시간 비교해주는 함수
	param : targetTime, compareTime
	return : int;	
	-1 : targetTime이 과거인 경우
	 0 : 같은 경우
	 1 : targetTime이 미래인 경우
	*/
	//시간 비교해주는 함수
	compareTime(targetTime, compareTime){
		let result = 0;
		
		let standardDay = "2022/04/06";
		
		let targetValue = new Date(standardDay + " " + targetTime);
		let compareValue = new Date(standardDay + " " + compareTime);
		
		if(targetValue < compareValue){
			result = -1;
		} else if(targetValue > compareValue){
			result = 1;
		} else {
			result = 0;
		}
		
		return result;
	}
}