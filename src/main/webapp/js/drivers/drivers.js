'use strict';

angular.module('app.drivers', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/drivers', {
    templateUrl: 'js/drivers/drivers.html',
//    controller: 'displayResultsCtrl'
  });
}])