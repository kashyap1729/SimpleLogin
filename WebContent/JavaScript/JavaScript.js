function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

function sendUrlParameters() {
	//window.location.replace("http://tiublrboaapp037.sciblr.in.ibm.com:31000/myfilegateway");
	var urlname =getUrlParameter('customReturnUrl');
	document.getElementsByName("url")[0].setAttribute("value",urlname);
	
}

