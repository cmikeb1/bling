angular.module('bling', ['ui.bootstrap'])
    .controller('BlingGlobalCtrl', function ($scope, $log, $uibModal) {
        $scope.newTransactionModal = function (category) {

            var transactionModalInstance = $uibModal.open({
                animation: true,
                templateUrl: '/ng-bling/transaction-modal.html',
                controller: 'TransactionModalCtrl',
                resolve: {
                    category: function () {
                        return category;
                    }
                }
            });

            transactionModalInstance.result.then(function (transaction) {
                $scope.$broadcast("TransactionCreated", transaction);
            }, function () {
                // modal dismissed
            });
        };

        $scope.$on("AddNewTransaction", function (scope, category) {
            $log.log(category);
            var categoryItem = {
                id: category.id,
                name: category.name
            };
            $log.log(categoryItem);
            $scope.newTransactionModal(categoryItem);
        });
    })
    .controller('TransactionModalCtrl', function ($scope, $uibModalInstance, $log, category, $http) {
        $scope.categories = null;
        $scope.format = 'yyyy/MM/dd';
        $scope.dateModalOpened = false;

        $scope.open = function ($event) {
            $scope.dateModalOpened = true;
        };

        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };

        $http.get("/api/category").success(function (data) {
            $scope.categories = [];
            for (var x = 0; x < data.length; x++) {
                var newCat = {
                    name: data[x].name,
                    id: data[x].id
                };
                $scope.categories.push(newCat);
                if ($scope.transaction.category && $scope.transaction.category.id === newCat.id) {
                    $scope.transaction.category = newCat;
                }
            }
        });

        $scope.transaction = {
            name: null,
            amount: null,
            date: new Date(),
            category: category,
            notes: null
        };

        $scope.create = function () {
            if ($scope.transactionForm.$invalid) {
                $scope.error = "Create a valid transaction idiot.";
            }
            else {
                $http.post("/api/transaction", $scope.transaction).success(function (data) {
                    $uibModalInstance.close(data);
                }).error(function (data) {
                    $scope.error = "Some error occurred when trying to create the transaction.";
                });
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    })
    .controller('PendingCtrl', function ($scope, $http) {
        $http.get("/api/source-transactions").success(function (data) {
            $scope.sourceTransactions = data;
        });
    })
    .controller('MainViewCtrl', function ($scope, $http) {

        var MS_IN_DAY = 86400000;

        $scope.$on("TransactionCreated", function () {
            $scope.refreshSnapshot();
        });

        $scope.refreshSnapshot = function () {
            $http.get("/api/snapshot").success(function (data) {
                $scope.snapshotData = data;
            });
        }

        $scope.refreshSnapshot();

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
            if ($scope.getMainMoneyBarPercentage() <= $scope.getMainTimeBarPercentage())
                return "success";
            else if ($scope.getMainMoneyBarPercentage() > 100)
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

            scope.getCategoryMoneyBarPercentage = function () {
                if (!scope.category)
                    return;

                var amountSpentPercentage = scope.sumTransactionAmounts(scope.category.transactions) / scope.category.budget * 100;
                return Math.round(amountSpentPercentage);
            };

            scope.getTypeForCategoryMoneyBar = function () {

                if (scope.getCategoryMoneyBarPercentage() > scope.percentageThroughPeriod) {
                    return "warning";
                }
                else if (scope.getCategoryMoneyBarPercentage() > 100) {
                    return "danger";
                }

                return "success";
            };

            scope.newTransactionModal = function (categoryName) {
                scope.$emit("AddNewTransaction", categoryName);
            };
        }

        return {
            restrict: 'E',
            link: linkFunction,
            scope: {
                category: '=info',
                percentageThroughPeriod: '=percent'
            },
            templateUrl: '/ng-bling/category.html'
        };
    }).directive('autoFocus', function ($timeout) {
        return {
            restrict: 'AC',
            link: function (_scope, _element) {
                $timeout(function () {
                    _element[0].focus();
                }, 0);
            }
        }
    });