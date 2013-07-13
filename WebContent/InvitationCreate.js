var req;
function validateForm(form) {
	var eventId = document.forms["myForm"]["eventId"].value;
	if (eventId == null || eventId == "") {
		alert("Please enter a eventId.");
		return false;
	}
	
	var senderId = document.forms["myForm"]["senderId"].value;
	var receiverId = document.forms["myForm"]["receiverId"].value;
	var message = document.forms["myForm"]["message"].value;
	var sendurl = "http://localhost:80/Dynamic/InvitationCreate?eventId="
		+ eventId + "&senderId=" + senderId + "&receiverId="
		+ receiverId + "&message=" + message;

	loadXMLDoc(sendurl);

}
function loadXMLDoc(url) {
	req = false;
	if (window.XMLHttpRequest) {
		try {
			req = new XMLHttpRequest();
		} catch (e) {
			req = false;
		}
	} else if (window.ActiveXObject) {
		try {
			req = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			req = false;
		}
	}

	if (req) {
		req.onreadystatechange = processJSON;
		req.open("GET", url, true);
		req.send("");
	}
}
function processJSON() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var doc = JSON.parse(req.responseText);
			var outputMsg = "";
			
				// outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			
		} else {
			document.getElementById("updateArea").innerHTML = "Failed";
		}
	}

}
function processReqChange() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var outMsg = req.responseXML;
		} else {
			var outMsg = "Failed";
		}
		document.getElementById("updateArea").innerHTML = outMsg;
	}

}