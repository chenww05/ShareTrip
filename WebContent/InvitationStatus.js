var req;
function validateForm(form) {
	var receiverId = document.forms["myForm"]["receiverId"].value;
	if (receiverId == null || receiverId == "") {
		alert("Please enter a receiverId.");
		return false;
	}
	loadXMLDoc("http://localhost:8080/Dynamic/InvitationStatus?receiverId="
			+ userId);

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
			if (doc.length == 0) {
				document.getElementById("updateArea").innerHTML = "No Results Found";
			} else {
				outputMsg += "<div id=fb-root></div>";
				outputMsg += "<table class=output>";
				outputMsg += "<tr class=output>";
				for ( var i = 0; i < doc.invitations.length; i++) {
					outputMsg += "<td class=output>"
							+ doc.invitations[i].message + "</td>";
					outputMsg += "<td class=output>"
							+ doc.invitations[i].reiverId + "</td>";
					outputMsg += "<td class=output>"
							+ doc.invitations[i].senderId + "</td>";
					outputMsg += "<td class=output>"
							+ doc.invitations[i].eventId + "</td>";
					outputMsg += "<td class=output>"
							+ doc.invitations[i].status + "</td>";
				}
				outputMsg += "</tr>";

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