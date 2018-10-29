/**
 * 
 */

(function($){
	var utils = {
		showAlert : function(msg){
			alert(msg);
		},
		add : function(a, b){
			return a+b;
		}
	}
	window.utils = utils;
})(jQuery);