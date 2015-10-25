angular.module('displayResults').filter('seconds', function () {
    return function (value) {
        var millis = value%1000;
        var seconds = (value-millis)/1000;

        var millRemainder = millis%10;
        millis = (millis-millRemainder)/10;

        if (millis<10){
            return seconds+":0"+millis;
        }
        return seconds+":"+millis;
    };
});

