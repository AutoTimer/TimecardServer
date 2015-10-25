(function() {
    'use strict';
    angular.module('displayResults').service('resultsService', resultsService);

    resultsService.$inject = ['$q', '$http'];

    function resultsService($q, $http) {

        return {
            getSummary:function(){
                var deferred = $q.defer();
                var getUrl = "http://localhost:8080/results-summary";
                $http.get(getUrl)
                    .success(function (data) {
                        deferred.resolve(data);
                    }).error(function (data, status) {
                        deferred.reject(data+":"+status);
                    });
                return deferred.promise;
            },
            notifyWinner:function(){
                var deferred = $q.defer();
                var getUrl = "http://localhost:8080/send-text-message";
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

