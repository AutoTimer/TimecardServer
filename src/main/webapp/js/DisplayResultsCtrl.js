(function() {
    'use strict';

    angular.module('displayResults').controller('displayResultsCtrl', displayResultsCtrl);

    displayResultsCtrl.$inject = ['resultsService'];

    function displayResultsCtrl(resultsService) {
        var displayResults = this;


        var summaryPromise = resultsService.getSummary();
        summaryPromise.then(function(data){
            displayResults.results = data;
            var largestNumberOfRuns = 0;
            displayResults.currentLargestIndex = 0;
            angular.forEach(displayResults.results,function(value, key)
            {
                if(value.layouts.length>largestNumberOfRuns){
                    largestNumberOfRuns = value.timesTaken.length;
                    displayResults.currentLargestIndex = key;
                }
            });
        },function(reason){

        });

        displayResults.notifyWinner = function(){
            resultsService.notifyWinner();
        }
    }
}());

