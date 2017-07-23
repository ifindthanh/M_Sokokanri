function makeDatePickerJP()
{	
	$.datepicker.regional.ja = {
	closeText: "閉じる",
	prevText: "&#x3C;前",
	nextText: "次&#x3E;",
	currentText: "今日",
	monthNames: [ "1月","2月","3月","4月","5月","6月",
	"7月","8月","9月","10月","11月","12月" ],
	monthNamesShort: [ "1月","2月","3月","4月","5月","6月",
	"7月","8月","9月","10月","11月","12月" ],
	dayNames: [ "日曜日","月曜日","火曜日","水曜日","木曜日","金曜日","土曜日" ],
	dayNamesShort: [ "日","月","火","水","木","金","土" ],
	dayNamesMin: [ "日","月","火","水","木","金","土" ],
	weekHeader: "週",
	dateFormat: "yy/mm/dd",
	firstDay: 0,
	isRTL: false,
	showMonthAfterYear: true,
	yearSuffix: "年" };
	
	$.datepicker.setDefaults( $.datepicker.regional.ja );
}

function getBrowserViewSize() {
	var myWidth = 0, myHeight = 0;
   	if( typeof( window.innerWidth ) == 'number' ) {
   	    //Non-IE
   	    myWidth = window.innerWidth;
   	    myHeight = window.innerHeight;
   	} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
   	    //IE 6+ in 'standards compliant mode'
   	    myWidth = document.documentElement.clientWidth;
   	    myHeight = document.documentElement.clientHeight;
   	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
   	    //IE 4 compatible
   	    myWidth = document.body.clientWidth;
   	    myHeight = document.body.clientHeight;
   	}
   	var obj = {
   	    width:  myWidth,
   	    height: myHeight
   	};
    return obj;
}

// Validates that the input string is a valid date formatted as "mm/dd/yyyy"
function isValidDate(dateString)
{
    // First check for the pattern
    if(!/^\d{4}\/\d{1,2}\/\d{1,2}$/.test(dateString))
        return false;

    // Parse the date parts to integers
    var parts = dateString.split("/");
    var year = parseInt(parts[0], 10);
    var month = parseInt(parts[1], 10);
    var day = parseInt(parts[2], 10);

    // Check the ranges of month and year
    if(year < 1000 || year > 3000 || month == 0 || month > 12)
        return false;

    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
};