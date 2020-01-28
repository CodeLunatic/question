app.service("scoreListService", function ($http) {

    // 从后台获取整套分数信息
    this.getScoreList = function (date, page, size) {
        var url = "../scoreList/findPage?page=" + page + "&size=" + size;
        if (date !== '') url += "&date=" + date;
        return $http.get(url);
    };

    this.deleteAll = function () {
        return $http.get("../scoreList/deleteAll");
    };

    this.delete = function (id) {
        return $http.get("../scoreList/delete?id=" + id);
    }
});