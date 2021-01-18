/**
 * 日付の入力に対する制限
 */

//1582年問題 取得->切出->比較->処理 format yyyy-mm-dd
$(function(){
  $('input[name="date"]').on('input', function(){
	var saleDate = $(this).val();
	var limitYear = 1582;
	var limitMonth = 10;
	var limitDay = 14;
	var outputMessage = "1582年10月15日以降が入力できます。"
	//var sliceYear = saleDate.slice(0,3);
	var sliceDate = saleDate.split('-');
	if(sliceDate[0] < limitYear){
		$('#errorMessage').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] < limitMonth){
		$('#errorMessage').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] == limitMonth && sliceDate[2] <= limitDay){
		$('#errorMessage').text(outputMessage);
	}else{
		messagePlace.text("");
	}


	console.log(sliceDate[0] < limitYear);
	console.log(sliceDate[0] == limitYear && sliceDate[1] < limitMonth);
	console.log(sliceDate[1] == limitMonth && sliceDate[2] <= limitDay);
  });
});