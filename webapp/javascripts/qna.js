var formList = document.querySelectorAll('.answerWrite input[type=submit]');
for ( var j=0 ; j < formList.length ; j++) {
	formList[j].addEventListener('click', writeAnswers, false);
}

function writeAnswers(e) {
	 e.preventDefault();
	 
	 var answerForm = e.currentTarget.form;
	 var url = "/api/addanswer.next";
	 var params = "questionId=" + answerForm[0].value + "&writer=" + answerForm[1].value + "&contents=" + answerForm[2].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 location.reload(true);
		 }
	 }
	 request.send(params);
}


var deleteList = document.querySelectorAll('.deleteBtn');
for ( var j=0 ; j < deleteList.length ; j++) {
	deleteList[j].addEventListener('click', deleteAnswer, false);
}

function deleteAnswer(e) {
	 e.preventDefault();
	 var url = "/api/deleteanswer.next";
	 var answerId = e.currentTarget.attributes['data-answerId'].value;
	 var questionId = e.currentTarget.attributes['data-questionId'].value;
	 var commentNo = e.currentTarget.attributes['data-commentNo'].value;
	 var params = "questionId=" + questionId + "&answerId=" + answerId;
	 
	 var parentNode = document.getElementById("comments")
	 var childNode = document.getElementById("comment-"+commentNo);
	 
	 var el = document.querySelector(".comments > h3");
	 var commentCnt = el.innerHTML.split(" : ")[1]-1;
	 
	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 
	 request.onreadystatechange = function() {
		 if(request.readyState == 4 && request.status == 200) {
			 el.innerHTML = "댓글 수 : " + commentCnt;
			 parentNode.removeChild(childNode);
		 }
	 }
	 request.send(params);
}



