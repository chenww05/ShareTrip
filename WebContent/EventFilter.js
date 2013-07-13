var req;
function validateForm(form) {
	var priceUp = document.forms["myForm"]["priceUp"].value;
	var priceDown = document.forms["myForm"]["priceDown"].value;
	var radius = document.forms["myForm"]["radius"].value;
	var latitude = document.forms["myForm"]["latitude"].value;
	var longitude = document.forms["myForm"]["longitude"].value;
	var number = document.forms["myForm"]["number"].value;
	var startTime = document.forms["myForm"]["startTime"].value;
	var endTime = document.forms["myForm"]["endTime"].value;

	loadXMLDoc("http://10.16.23.17:8080/Dynamic/EventFilter?priceUp=" + priceUp
			+ "&priceDown=" + priceDown + "&radius=" + radius + "&latitude="
			+ latitude + "&longitude=" + longitude + "&number=" + number
			+ "&startTime=" + startTime + "&endTime=" + endTime);

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
			if (doc.eventId.length == 0) {
				document.getElementById("updateArea").innerHTML = "No Results Found";
			} else {

				outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";

				outputMsg += "<th>EventId</th>";
						
				for ( var i = 0; i < doc.eventId.length; i++) {
					outputMsg += "<tr class=output>";
					outputMsg += "<td class=output>" + doc.eventId[i]
							+ "</td>";
					outputMsg += "</tr>";
				}
				outputMsg += "</table>";
				document.getElementById("updateArea").innerHTML = outputMsg;
			}
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