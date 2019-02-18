app.service("examPaperService", function ($http) {

    // 从后台获取整份试卷的信息
    this.getExamPaper = function (fileId, operation) {
        return $http.get("../examPaper/findAll?fileId=" + fileId + "&whatToDo=" + operation);
    };

    // 根据分数的Id来获取对应的错题
    this.getWrongTopicByScoreId = function (scoreId) {
        return $http.get("../wrongTopic/getWrongTopicByScoreId?scoreId=" + scoreId);
    };

    // 获取用户的所有的错题
    this.getAllWrongTopic = function () {
        return $http.get("../wrongTopic/findAll");
    };

    // 根据Id删除错题
    this.deleteWrongTopicById = function (questionId) {
        return $http.get("../wrongTopic/delete?questionId=" + questionId);
    }
});