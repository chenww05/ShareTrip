var req;
function validateForm(form) {
	var id = document.forms["myForm"]["eventId"].value;
	if (id == null || id == "") {
		alert("Please enter a id.");
		return false;
	}
	if (isNaN(id)) {
		alert("Please enter a valid id.");
		return false;
	}

	loadXMLDoc("http://localhost:80/Dynamic/GetSignUp?id=" + id);

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
				outputMsg += "<th>Event Id</th>";
				outputMsg += "<td class=output>" + doc.eventid + "</td>";
				var array = String(doc.userids).split(/,/);
				for(var i = 0; i < array.length; i++){
					outputMsg += "<td class=output>" + array[i] + "</td>";
				}
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