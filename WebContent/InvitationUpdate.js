var req;
function validateForm(form) {

	var invitationId = document.forms["myForm"]["invitationId"].value;
	if (invitationId == null || invitationId == "") {
		alert("Please enter a invitationId.");
		return false;
	}
	if (isNaN(invitationId)) {
		alert("Please enter a valid id.");
		return false;
	}
	var status = document.forms["myForm"]["status"].value;
	var sendURL = "http://localhost:8080/Dynamic/InvitationUpdate?invitationId=" + id
	+ "&status=" + status;
	
	loadXMLDoc(sendURL);
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
			document.getElementById("updateArea").innerHTML = "Done";
		} else {
			document.getElementById("updateArea").innerHTML = "Sorry";
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