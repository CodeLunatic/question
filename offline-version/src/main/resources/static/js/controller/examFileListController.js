// 文件列表
app.controller("examFileListController", function ($scope, $location, $controller, examFileListService) {

    // 导入baseController
    $controller("baseController", {$scope: $scope});

    // 要搜索的文件的名字
    $scope.fileName = "";

    /**
     * 得到题库文件夹下面的所有的Excel文件
     */
    $scope.findPage = function (page, size) {
        var whatToDo = $location.search()['whatToDo'];
        if (whatToDo === "lookAtQuestion") {//whatToDo=lookAtQuestion
            $scope.locationUrl = "toViewExamPaper.html";
        } else if (whatToDo === "doExercise") {//whatToDo=doExercise
            $scope.locationUrl = "toDoExercise.html";
        } else {
            alert("参数传递错误，请不要更改参数");
            window.location.href = "index.html";
            return;
        }
        // 查询列表
        examFileListService.findFileListByFileName($scope.fileName, page, size).success(function (response) {
            $scope.paginationConf.totalItems = response.total;
            $scope.fileList = response.rows;
        })
    }
});