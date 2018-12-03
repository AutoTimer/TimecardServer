angular.module('app', [
  'ngRoute',
  'app.displayResults',
  'app.drivers'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/displayResults'});
}]);
