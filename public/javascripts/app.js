$(document).ready(function() {
	// init prettify.js
	$("pre code").parent().each(function() {
		$(this).addClass('prettyprint');
	});
	prettyPrint();
});
