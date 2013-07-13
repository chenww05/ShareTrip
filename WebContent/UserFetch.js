var req;
function validateForm(form) {
	var userId = document.forms["myForm"]["userId"].value;
	if (userId == null || userId == "") {
		alert("Please enter a userId.");
		return false;
	}
	loadXMLDoc("http://localhost:8080/Dynamic/UserFetch?userId=" + userId);

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

				outputMsg += "<th>Username</th><th>Gender</th><th>Password</th><th>Facebook</th>"
						+ "<th>LinkedIn</th>";
				outputMsg += "<tr class=output>";
				outputMsg += "<td class=output>" + doc.username + "</td>";
				outputMsg += "<td class=output>" + doc.gender + "</td>";
				outputMsg += "<td class=output>" + doc.password + "</td>";
				outputMsg += "<td class=output>" + doc.facebookid + "</td>";
				outputMsg += "<td class=output>" + doc.linkedinid + "</td>";

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