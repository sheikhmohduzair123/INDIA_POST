/*
 * This file will contain global methods which are generic in nature.
 */

$(function () {
            
	//use this class for specific case and global is not implemented
	$(".blockCopyPaste").on("paste cut paste", function (e) {
                e.preventDefault();
                return false;
     });
	
	//not needed as we are blocking copy paste at global level for all inputs
//	$("#confirmNewPassword").on("paste cut paste", function (e) {
//        e.preventDefault();
//        return false;
//	});
	
	//following will disable copy paste in entire system for all text boxes
	$('input').on("paste cut paste", function (e) {
        e.preventDefault();
        return false;
	});
	
	//to block special characters from input fields
	$('input').on('keypress', function (event) {
	    var regex = new RegExp("^[a-zA-Z0-9@-_.$#%*!-\/\\-\\s]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});
	
	
	//more methods will go here
        
});