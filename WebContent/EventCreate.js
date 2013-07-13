var req;
function validateForm(form) {
	var name = document.forms["myForm"]["name"].value;
	if (name == null || name == "") {
		alert("Please enter a name.");
		return false;
	}
	if (!isNaN(name)) {
		alert("Please enter a valid name.");
		return false;
	}

	var description = document.forms["myForm"]["description"].value;
	var price = document.forms["myForm"]["price"].value;
	var starttime = document.forms["myForm"]["starttime"].value;
	var endtime = document.forms["myForm"]["endtime"].value;
	var number = document.forms["myForm"]["number"].value;
	var type = document.forms["myForm"]["type"].value;
	var location = document.forms["myForm"]["location"].value;
	var latitude = document.forms["myForm"]["latitude"].value;
	var longitude = document.forms["myForm"]["longitude"].value;

	loadXMLDoc("http://10.16.23.17:8080/Dynamic/EventCreate?name="
			+ name + "&description=" + description + "&price="
			+ price + "&starttime=" + starttime + "&endtime=" + endtime 
			+ "&number=" + number + "&type=" + type + "&location=" + location
			+ "&latitude=" + latitude + "&longitude=" + longitude);

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
				outputMsg += "<td class=output>" + doc.eventId + "</td>";
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