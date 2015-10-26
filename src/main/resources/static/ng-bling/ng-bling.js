angular.module('bling', [])
// BadController will fail to instantiate, due to relying on automatic function annotation,
// rather than an explicit annotation
    .controller('BlingGlobalCtrl', function ($scope, $http) {
        $http.get("/api/snapshot").success(function (data) {
            $scope.snapshotData = data;
        });

        $scope.getNetAmount = function () {
            if (!$scope.snapshotData || !$scope.snapshotData.categories)
                return 0;

            var total = 0;
            for (var x = 0; x < $scope.snapshotData.categories.length; x++) {
                var category = $scope.snapshotData.categories[x];
                total += category.budget;
                if (category.transactions) {
                    for (var y = 0; y < category.transactions.length; y++) {
                        total -= category.transactions[y].amount;
                    }
                }
            }

            return total;
        }
    })
    .
    directive('category', function () {

        function linkFunction(scope) {
            scope.sumTransactionAmounts = function (transactions) {
                if (!transactions)
                    return 0;
                var total = 0;
                for (var x = 0; x < transactions.length; x++) {
                    total += transactions[x].amount;
                }

                return total;
            };
        }

        return {
            restrict: 'E',
            link: linkFunction,
            scope: {
                category: '=info'
            },
            templateUrl: '/ng-bling/category.html'
        };
    });