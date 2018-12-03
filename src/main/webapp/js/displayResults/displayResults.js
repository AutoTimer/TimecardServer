'use strict';

angular.module('app.displayResults', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/displayResults', {
    templateUrl: 'js/displayResults/displayResults.html',
    controller: 'displayResultsCtrl'
  });
}])