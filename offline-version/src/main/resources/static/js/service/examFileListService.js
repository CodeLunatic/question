app.service("examFileListService", function ($http) {

    this.findFileListByFileName = function (fileName, page, size) {
        return $http.get("../examFileList/findPage?fileName=" + fileName + "&page=" + page + "&size=" + size);
    }
});