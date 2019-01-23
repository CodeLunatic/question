//基础控制层
app.controller('baseController', function ($scope) {

    // 重新加载列表 数据
    $scope.reloadList = function () {
        // 切换页码
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    // 用于解决reloadList方法重复调用两次的一个全局变量
    var currentPage = 0;
    var itemsPerPage = 10;

    // 分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            // 如果当前页等于上一次赋值的当前页（全局变量中的当前页），那么就不要往下执行了
            if (currentPage === $scope.paginationConf.currentPage && itemsPerPage === $scope.paginationConf.itemsPerPage) return;
            // 将当前页存储到全局变量中的当前页中
            currentPage = $scope.paginationConf.currentPage;
            itemsPerPage = $scope.paginationConf.itemsPerPage;
            $scope.reloadList();//重新加载 这个方法会重复调用两次，这对于我是一个问题，所以需要解决， 2018/10/17日已成功解决
        }
    };

    // 用作日期的格式化
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "H+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    // 用作字符串转时间戳
    $scope.getTimeStamp = function (date) {
        date = date.substring(0, 19);
        date = date.replace(/-/g, '/');
        return new Date(date).getTime();
    };

    //获取当前时间，格式YYYY-MM-DD
    $scope.getNowFormatDate = function () {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) month = "0" + month;
        if (strDate >= 0 && strDate <= 9) strDate = "0" + strDate;
        return year + seperator1 + month + seperator1 + strDate;
    }
});