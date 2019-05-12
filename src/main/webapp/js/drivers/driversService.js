(function() {
    'use strict';
    angular.module('app.drivers').service('driversService', driversService);

    driversService.$inject = ['$q', '$http'];

    function driversService($q, $http) {

        return {
            getDrivers:function(){
              var deferred = $q.defer();
              var getUrl = "driver";
              $http.get(getUrl)
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data, status) {
                    deferred.reject(data+":"+status);
                });
              return deferred.promise;
            },

            create:function(driver){
                var deferred = $q.defer();
                var url = "driver";
                var config = {
                  headers : {
                    'Content-Type': 'application/json'
                  }
                }

                $http.post(url, driver, config)
                    .success(function (data) {
                        deferred.resolve(data);
                    }).error(function (data, status) {
                        deferred.reject(data+":"+status);
                    });
                return deferred.promise;
            },

            delete:function(driver){
              if(!confirm("Are you sure you want to delete this driver?")){
                return;
              };
              var deferred = $q.defer();
              var url = "driver";
              var config = {
                data: driver,
                headers : {
                  'Content-Type': 'application/json'
                }
              }

              $http.delete(url, config)
                .success(function (data) {
                  deferred.resolve(data);
                }).error(function (data, status) {
                  deferred.reject(data+":"+status);
                });
              return deferred.promise;
            }
        }

    }
}());

