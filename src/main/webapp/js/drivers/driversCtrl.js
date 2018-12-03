angular.module('app.drivers')
  .controller('driversCtrl',
    ['$scope',
     'driversService',
     function($scope, driversService){
       var driversCtrl = this;

       function loadDrivers(){
           var promise = driversService.getDrivers();
                promise.then(function(data){
                  driversCtrl.drivers = data;
                },function(reason){
                  console.log(reason);
                });
       }

       $scope.create = function(driver){
         var promise = driversService.create(driver);
         promise.then(
           function(data){
             driversCtrl.newDriver = data;
             loadDrivers();
           },
           function(reason){
             console.log(reason);
           });
       }

       $scope.reset = function(formModel){
         angular.copy({},formModel);
       }

       loadDrivers();
     }
    ]
  );

