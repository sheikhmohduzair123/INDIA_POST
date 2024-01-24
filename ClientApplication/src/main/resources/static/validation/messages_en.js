/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: bn_BD (Bengali, Bangladesh)
 */
$.extend( $.validator.messages, {
	required: "This field is required.",
    remote: "Please fix this field.",
    email: "Please enter a valid email address.",
    url: "Please enter a valid URL.",
    date: "Please enter a valid date.",
    dateISO: "Please enter a valid date (ISO).",
    number: "Please enter a valid number.",
    digits: "Please enter only digits.",
    creditcard: "Please enter a valid credit card number.",
    equalTo: "Please enter the same value again.",
    maxlength: $.validator.format("Please enter no more than {0} characters."),
    minlength: $.validator.format("Please enter at least {0} characters."),
    rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
    range: $.validator.format("Please enter a value between {0} and {1}."),
    maxWords: $.validator.format("Please enter a value less than or equal to {0}."),
    minWords: $.validator.format("Please enter a value greater than or equal to {0}."),
    step: $.validator.format( "Please enter a multiple of {0}." ),
    letterswithbasicpunc: "Letters or punctuation only please.",
    alphanumeric: "Letters, numbers, and underscores only please.",
    lettersonly:"Letters only please",
    nowhitespace: "No white space please",
    notEqual: "Please enter a different value",
    valid_name: "Letters, space and '.' only please.",
    mobiles: "Enter 10 digits. Only digits allowed.",
    alphawithspace: "Letters or punctuation only please.",
    nowhitespaceinstartandend: "No special character allowed. No whitespace allowed in front and end.",
    parcelContentValidation: "Special Character Not Allowed",
    address1: "Special Character Not Allowed",
    address2: "Special Character Not Allowed",
    nospecialcharacter: "Special Character Not Allowed",
	positiveDecimalNumber: "Only Positive Decimal Number Allowed",
    pswdcheck: "Password must contain 1 upper case letter, 1 lower case letter, 1 digit, 1 special character and length must be between 6 & 15."
} );