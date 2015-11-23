angular.module('bling', ['ui.bootstrap'])
    .controller('BlingGlobalCtrl', function ($scope, $http) {

    })
    .controller('PendingCtrl', function ($scope, $http) {

    })
    .controller('MainViewCtrl', function ($scope, $http) {

        var MS_IN_DAY = 86400000;

        $http.get("/api/snapshot").success(function (data) {
            $scope.snapshotData = data;
        });

        var totalAmountPaid = 0;

        $scope.getTotalAmountLeft = function () {
            if (!$scope.snapshotData || !$scope.snapshotData.categories)
                return 0;

            var total = 0;
            totalAmountPaid = 0;
            for (var x = 0; x < $scope.snapshotData.categories.length; x++) {
                var category = $scope.snapshotData.categories[x];
                total += category.budget;
                if (category.transactions) {
                    for (var y = 0; y < category.transactions.length; y++) {
                        total -= category.transactions[y].amount;
                        totalAmountPaid += category.transactions[y].amount;
                    }
                }
            }

            return total;
        };

        $scope.getTotalAmountPaid = function () {
            $scope.getTotalAmountLeft();
            return totalAmountPaid;
        };


        $scope.getMainTimeBarPercentage = function () {
            if (!$scope.snapshotData)
                return;

            var timeElapsedPercentage = $scope.getDaysElapsed() / $scope.getDaysInPeriod() * 100;

            return Math.round(timeElapsedPercentage);

        };

        $scope.getDaysInPeriod = function () {
            if (!$scope.snapshotData)
                return;

            var msInPeriod = $scope.snapshotData.endDay - $scope.snapshotData.startDay;

            return msInPeriod / MS_IN_DAY;
        };

        $scope.getDaysElapsed = function () {
            if (!$scope.snapshotData)
                return;

            var msElapsed = new Date() - $scope.snapshotData.startDay;

            return msElapsed / MS_IN_DAY;
        };

        $scope.getMainMoneyBarPercentage = function () {
            var totalPercentage = ($scope.getTotalAmountPaid() / $scope.getTotalBudget()) * 100;
            return Math.round(totalPercentage);
        };

        $scope.getTypeForMainTimeBar = function () {
            return "info";
        };

        $scope.getTypeForMainMoneyBar = function () {
            if($scope.getMainMoneyBarPercentage() <= $scope.getMainTimeBarPercentage())
                return "success";
            else if($scope.getMainMoneyBarPercentage() > 100)
                return "danger";
            else
                return "warning";

            return "danger";

        };

        $scope.getTotalBudget = function () {
            if (!$scope.snapshotData)
                return;

            var total = 0;
            for (var x = 0; x < $scope.snapshotData.categories.length; x++) {
                total += $scope.snapshotData.categories[x].budget;
            }

            return total;
        };
    })
    .directive('category', function () {

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