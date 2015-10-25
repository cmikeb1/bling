angular.module('bling', [])
// BadController will fail to instantiate, due to relying on automatic function annotation,
// rather than an explicit annotation
    .controller('BlingGlobalCtrl', function($scope, $http) {
        $http.get("/api/snapshot").success(function (data) {
            $scope.snapshotData = data;
        });
    })
    .directive('category', function() {
        return {
            restrict: 'E',
            scope: {
                category: '=info'
            },
            templateUrl: '/ng-bling/category.html'
        };
    });