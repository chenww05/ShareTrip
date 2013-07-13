var req;
function validateForm(form) {
	var eventId = document.forms["myForm"]["eventId"].value;
	if (eventId == null || eventId == "") {
		alert("Please enter a event Id .");
		return false;
	}
	if (isNaN(eventId)) {
		alert("Please enter a valid eventId.");
		return false;
	}
	loadXMLDoc("http://10.16.23.17:8080/Dynamic/EventFetch?eventId=" + eventId);

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
			if (doc.event == 0) {
				document.getElementById("updateArea").innerHTML = "No Results Found";
			} else {
				outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				
				outputMsg += "<th>Event</th><th>Description</th><th>Name</th><th>Location</th>" +
						"<th>Latitude</th><th>Longitude</th><th>Start Time</th>" +
						"<th>End Time</th><th>Price</th><th>Number</th><th>Type</th>";
				 outputMsg += "<tr class=output>";
				 outputMsg += "<td class=output>" + doc.eventId + "</td>";
				 outputMsg += "<td class=output>" + doc.name + "</td>";
				 outputMsg += "<td class=output>" + doc.description + "</td>";
				 outputMsg += "<td class=output>" + doc.location + "</td>";
				 outputMsg += "<td class=output>" + doc.latitude + "</td>";
				 outputMsg += "<td class=output>" + doc.longitude + "</td>";
				 outputMsg += "<td class=output>" + doc.starttime + "</td>";
				 outputMsg += "<td class=output>" + doc.endtime + "</td>";
				 outputMsg += "<td class=output>" + doc.price + "</td>";
				 outputMsg += "<td class=output>" + doc.number + "</td>";
				 outputMsg += "<td class=output>" + doc.type + "</td>";
				 outputMsg += "</tr>";
				
				
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
	
			}
		} else if (req.status == 0) {
			document.getElementById("updateArea").innerHTML = "You should run from a different server";
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