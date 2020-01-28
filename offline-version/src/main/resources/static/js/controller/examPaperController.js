// 试卷的控制层
app.controller("examPaperController", function ($scope, $location, $controller, examPaperService) {

    // 导入baseController
    $controller("baseController", {$scope: $scope});

    /**
     * 初始化页面中的数据
     */
    var initPageData = function () {
        $scope.singleChoiceList = []; //单选
        $scope.multipleChoiceList = []; //多选
        $scope.trueOrFalseList = []; //判断
        $scope.fillBlankList = []; //填空
        $scope.shortAnswerList = []; // 简答
        $scope.essayList = []; // 论述
        $scope.analyticalList = []; // 分析
        $scope.isShowScore = true; // 判断是否显示分数
        $scope.isDeleteWrongTopic = false; // 判断是否显示删除错题按钮
        $scope.exerciseCount = 0; // 这里的计数只记录单选多选和判断，用来在刷题页面进行显示分数
    };

    /**
     * 初始化页面
     */
    $scope.initPage = function () {
        var fileId = $location.search()['fileId'];
        var scoreId = $location.search()['scoreId'];
        var whatToDo = $location.search()['whatToDo'];
        if (fileId != null && fileId.length === 32) getExamPaper(fileId);
        else if (scoreId != null && scoreId.length === 32) getWrongTopicByScoreId(scoreId);
        else if (whatToDo != null && whatToDo === "lookAtMyWrongTopic") getAllWrongTopic();
        else exceptionAndClose("参数错误，原因可能是更改了地址栏中的参数");
    };

    /**
     * 判断用户要做什么,用于后台读取过滤参数
     */
    function whatToDo() {
        var url = window.location;
        var whatToDo;
        if (url.toString().indexOf("toViewExamPaper") !== -1) whatToDo = "lookAtQuestion";
        else whatToDo = "doExercise";
        return whatToDo;
    }

    /**
     * 获取整份试卷的方法,该方法看题和做题共用
     */
    var getExamPaper = function (fileId) {
        initPageData();
        Window.fileId = fileId; //放到全局数据中
        var operation = whatToDo();// 用户的行为
        examPaperService.getExamPaper(fileId, operation).success(function (response) {// 开始使用fileId来访问文件
            // 如果访问出现了异常, exist为false的时候即为异常,告诉用户异常信息
            if (!response.exist) if (exceptionAndClose(response.message)) return;
            var topicList = response.examPaperList; // 题目列表
            $scope.topicCount = 0; // 计数,这里的计数是看题页面的计数，看题页面不只有单选，多选和判断
            for (var i in topicList) {// 分离列表
                var topic = topicList[i]; // 每一道题
                topic.topicOrder = parseInt(i) + 1;// 添加题号，这个序号可能会乱序，不过没关系，不会影响使用
                // 如果这道题目正常的话
                if (topic.question != null && topic.question !== '') {
                    setQuestionByType(topic); // 根据试题的类型分配到各类型试题的列表中
                    $scope.topicCount++; // 计数
                }
            }
            // 题目数量如果等于0，提示
            if ($scope.topicCount <= 0) if (exceptionAndClose('这单元没有题哦!')) return;
            // 计算题目的分数，这里的分数只是看题页面的分数，因为看题页面不只有单选，多选和判断
            $scope.topicScore = (100 / $scope.topicCount).toFixed(2);
            // 这里的分数就是做题界面的分数，因为做题界面只可以进行选择题
            $scope.exerciseScore = (100 / $scope.exerciseCount).toFixed(2);
        })
    };

    /**
     *  根据分数的Id查看错题
     * @param scoreId 错题列表
     */
    var getWrongTopicByScoreId = function (scoreId) {
        initPageData();
        examPaperService.getWrongTopicByScoreId(scoreId).success(function (response) {
            getWrongTopicList(response);
        });
    };

    /**
     * 获得所有的错题列表
     */
    var getAllWrongTopic = function () {
        initPageData();
        $scope.isDeleteWrongTopic = true;
        examPaperService.getAllWrongTopic().success(function (response) {
            getWrongTopicList(response);
        });
    };

    /**
     * 获取错题列表
     * @param response 响应对象
     */
    var getWrongTopicList = function (response) {
        $scope.isShowScore = false; // 隐藏元素
        $scope.topicCount = 0; // 计数,这里的计数是看题页面的计数，看题页面不只有单选，多选和判断
        var topicList = response.examPaperList; // 题目列表
        for (var i in topicList) {// 分离列表
            var topic = topicList[i]; // 每一道题
            topic.topicOrder = parseInt(i) + 1;// 添加题号，这个序号可能会乱序，不过没关系，不会影响使用
            if (topic.question != null && topic.question !== '') { // 如果这道题目正常的话
                setQuestionByType(topic); // 根据试题的类型分配到各类型试题的列表中
                $scope.topicCount++; // 计数
            }
        }
        if ($scope.topicCount <= 0) exceptionAndClose("您还没有错题，加油刷题吧！");// 题目数量如果等于0，提示
    };

    /**
     * 根据类型来将每个题目分配到不同的集合中
     * @param topic 题目
     */
    var setQuestionByType = function (topic) {
        if (topic.type != null && topic.type.indexOf("单") !== -1) { // 单选
            $scope.singleChoiceList.push(topic);
            $scope.exerciseCount++;
        } else if (topic.type != null && topic.type.indexOf("多") !== -1) { // 多选
            $scope.multipleChoiceList.push(topic);
            $scope.exerciseCount++;
        } else if (topic.type != null && topic.type.indexOf("断") !== -1) { // 判断
            $scope.trueOrFalseList.push(topic);
            $scope.exerciseCount++;
        } else if (topic.type != null && topic.type.indexOf("填") !== -1) { // 填空
            $scope.fillBlankList.push(topic);
        } else if (topic.type != null && topic.type.indexOf("答") !== -1) { // 简答
            $scope.shortAnswerList.push(topic);
        } else if (topic.type != null && topic.type.indexOf("论") !== -1) { // 论述
            $scope.essayList.push(topic);
        } else { // 分析
            $scope.analyticalList.push(topic);
        }
    };

    /**
     * 得到下面的导航栏，答题卡
     * @returns {Array}
     */
    $scope.getTabBarNum = function () {
        var arr = [];
        for (var i = 0; i < $scope.exerciseCount; i++) arr.push(i + 1);
        return arr;
    };

    /**
     * 根据Id删除错题本上面的一道错题
     * @param event 获取当前元素要用到
     * @param questionId 问题的Id
     */
    $scope.deleteWrongTopicById = function (event, questionId) {
        examPaperService.deleteWrongTopicById(questionId).success(function (response) {
            if (response.success) getAllWrongTopic();
            else alert(response.message);
        })
    };

    /**
     * 显示异常信息并关闭窗口
     * @param message 异常信息
     * @returns {boolean} 是否操作完成
     */
    var exceptionAndClose = function (message) {
        alert(message);
        window.close();
        return true;
    };
});