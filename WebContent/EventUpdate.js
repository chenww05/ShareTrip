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

	var sendURL = "http://localhost:80/Dynamic/EventUpdate?id=" + id;
	var price = document.forms["myForm"]["price"].value;
	if (price != null && price != "" && !isNaN(price))
		sendURL = sendURL + "&price=" + price;
	var starttime = document.forms["myForm"]["starttime"].value;
	if (starttime != null && starttime != "" && !isNaN(starttime))
		sendURL = sendURL + "&starttime=" + starttime;
	var endtime = document.forms["myForm"]["endtime"].value;
	if (endtime != null && endtime != "" && !isNaN(endtime))
		sendURL = sendURL + "&endtime=" + endtime;
	var location = document.forms["myForm"]["location"].value;
	if (location != null && location != "" && !isNaN(location))
		sendURL = sendURL + "&location=" + location;
	var latitude = document.forms["myForm"]["latitude"].value;
	if (latitude != null && latitude != "" && !isNaN(latitude))
		sendURL = sendURL + "&latitude=" + latitude;
	var longitude = document.forms["myForm"]["longitude"].value;
	if (longitude != null && longitude != "" && !isNaN(longitude))
		sendURL = sendURL + "&longitude=" + longitude;
	alert(sendURL);
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