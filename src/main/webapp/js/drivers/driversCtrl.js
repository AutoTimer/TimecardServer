(function() {
    'use strict';

    angular.module('app.drivers').controller('driversCtrl', driversCtrl);

    driversCtrl.$inject = ['driversService'];

    function driversCtrl(driversService) {
        var driversCtrl = this;

        var promise = driversService.getDrivers();
        promise.then(function(data){
            driversCtrl.drivers = data;
        },function(reason){

        });
    }
}());

