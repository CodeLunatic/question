app.controller("scoreListController", function ($scope, $controller, scoreListService) {
    // 导入baseController
    $controller("baseController", {$scope: $scope});
    // 默认搜索的日期
    $scope.date = "";

    $scope.findPage = function (page, size) {
        scoreListService.getScoreList($scope.date, page, size).success(function (response) {
            $scope.paginationConf.totalItems = response.total;
            $scope.scoreList = response.rows;
        })
    };

    $scope.deleteAll = function () {
        if (confirm("确认要清空吗？")) {
            scoreListService.deleteAll().success(function (response) {
                if (response.success) $scope.reloadList();
                else alert(response.message);
            })
        }
    };

    $scope.delete = function (id) {
        if (confirm("确认要删除吗？")) {
            scoreListService.delete(id).success(function (response) {
                if (response.success) $scope.reloadList();
                else alert(response.message);
            })
        }
    }
});