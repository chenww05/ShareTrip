var req;
function validateForm(form) {

	var userId = document.forms["myForm"]["userId"].value;
	var eventId = document.forms["myForm"]["eventId"].value;
	var sendurl = "http://10.16.23.17:8080/Dynamic/SignUp?userId="
		+ userId + "&eventId=" + eventId;
	alert(sendurl);
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
			if (doc.hotels.hotel.length == 0) {
				document.getElementById("updateArea").innerHTML = "No Results Found";
			} else {
				// outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				outputMsg += "<th>Image</th><th>Name</th><th>Location</th><th>Rating out of 5</th><th>Reviews</th><th>Post to Facebook</th>";
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			}
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
var req;
function validateForm(form) {

	var userId = document.forms["myForm"]["userId"].value;
	var eventId = document.forms["myForm"]["eventId"].value;
	var sendurl = "http://10.16.23.17:8080/ShareIt/SignUp?userId="
		+ userId + "&eventId=" + eventId;
	alert(sendurl);
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
			if (doc.hotels.hotel.length == 0) {
				document.getElementById("updateArea").innerHTML = "No Results Found";
			} else {
				// outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				outputMsg += "<th>Image</th><th>Name</th><th>Location</th><th>Rating out of 5</th><th>Reviews</th><th>Post to Facebook</th>";
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			}
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