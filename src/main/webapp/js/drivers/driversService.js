(function() {
    'use strict';
    angular.module('app.drivers').service('driversService', driversService);

    driversService.$inject = ['$q', '$http'];

    function driversService($q, $http) {

        return {
            getDrivers:function(){
                var deferred = $q.defer();
                var getUrl = "http://localhost:8080/driver";
                $http.get(getUrl)
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

