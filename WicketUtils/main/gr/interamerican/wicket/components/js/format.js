//id - the id of the component
//pnumber - the value of the specific component (document.getElementById(id).value)
//decimals - the number of decimals we want to display
function format_number(id, pnumber) {
	var snum = new String(pnumber);
	snum = snum.ReplaceAll(".", "");

	//H FormatNumberBy3 στην ουσία τοποθετεί στις σωστές θέσεις τη "."
	var finalValue = FormatNumberBy3(snum, ",", ".");
	document.getElementById(id).value = finalValue;
	return finalValue;
}

String.prototype.ReplaceAll = function(stringToFind, stringToReplace) {
	var temp = this;
	var index = temp.indexOf(stringToFind);
	while (index != -1) {
		temp = temp.replace(stringToFind, stringToReplace);
		index = temp.indexOf(stringToFind);
	}
	return temp;
};

function FormatNumberBy3(num, decpoint, sep) {
	// check for missing parameters and use defaults if so
	if (arguments.length == 2) {
		sep = ".";
	}
	if (arguments.length == 1) {
		sep = ".";
		decpoint = ",";
	}
	// need a string for operations
	num = num.toString();
	// separate the whole number and the fraction if possible
	var a = num.split(decpoint);
	var x = a[0]; // decimal
	var y = a[1]; // fraction
	var z = "";
	var i;

	if (typeof (x) != "undefined") {
		// reverse the digits. regexp works from left to right.
		for (i = x.length - 1; i >= 0; i--)
			z += x.charAt(i);
		// add seperators. but undo the trailing one, if there
		z = z.replace(/(\d{3})/g, "$1" + sep);
		if (z.slice(-sep.length) == sep)
			z = z.slice(0, -sep.length);
		x = "";
		// reverse again to get back the number
		for (i = z.length - 1; i >= 0; i--)
			x += z.charAt(i);
		// add the fraction back in, if it was there
		if (typeof (y) != "undefined" && y.length > 0)
			x += decpoint + y;
	}
	return x;
}