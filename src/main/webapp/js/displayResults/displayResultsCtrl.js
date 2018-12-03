(function() {
    'use strict';

    angular.module('app.displayResults').controller('displayResultsCtrl', displayResultsCtrl);

    displayResultsCtrl.$inject = ['resultsService'];

    function displayResultsCtrl(resultsService) {
        var displayResults = this;

        var summaryPromise = resultsService.getSummary();
        summaryPromise.then(function(data){
            displayResults.resultSummaryResponse = data;
        },function(reason){

        });
    }
}());

