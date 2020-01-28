var lastSendTime; // 全局变量，记录上次发送消息的时间
var isEmpty = false; // 消息为空提示每次只显示一次
var username;
var img;

// 添加一个提示信息的方法
function makeTips(tips) {
    var message = $("#message");
    message.append("<div class='tips animated fadeIn'>" + tips + "</div>");
    message.scrollTop(message[0].scrollHeight);
}

// 页面加载
$(function () {
    username = getCookie("randomUserName");
    img = getCookie("randomAvatar");
    if (username === '') {
        $.post(
            "chat/username",
            {},
            function (res) {
                username = res;
                setCookie("randomUserName", username, 1024);
            },
            "text"
        );
    }
    if (img === '') {
        $.post(
            "chat/avatar",
            {},
            function (res) {
                img = res;
                setCookie("randomAvatar", img, 1024);
            },
            "text"
        )
    }
    window.setTimeout(function () {
        makeTips("恭喜你获得键盘侠称号!");
    }, 30 * 60 * 1000);

    // 页面加载的时候根据浏览器的高度确定消息盒子的高度
    $("#message").css({'height': window.innerHeight - 88 + "px"});
    // 注册一个点击事件，点击发送消息
    $("#send").click(function () {
        sendMessage();
    });

    // 页面加载后焦点需要在输入框中
    $("#messageContent").focus();
});

// 浏览器发生缩放的时候调整消息盒子的高度
window.onresize = function () {
    $("#message").css({'height': window.innerHeight - 88 + "px"});
};

// 发送消息的方法
function sendMessage() {
    var messageContent = $("#messageContent");
    // 获得要发送的消息
    var message = messageContent.val();
    // 判断消息的内容
    if (message == null || message.trim() === '') {
        if (isEmpty) {
            return;
        }
        makeTips("输入的内容不能为空");
        isEmpty = true;
        return;
    }
    isEmpty = false;
    message = stopHtmlInject(message);
    // 发送时间的显示操作
    if (lastSendTime == null) {
        lastSendTime = Date.now();
    }
    // 30秒显示一次发送时间
    if (lastSendTime + 30000 < Date.now()) {
        lastSendTime = null;
        makeTips(getSendTimeString());
    }
    // 拼接发送消息的HTML
    var sendMessageHtml = "<div class='clearfloat animated bounceInRight'><div class='right'><span class=\"username\">" + username + "</span><br><div class='chat-message'>" + message + "</div><div class='chat-avatars'><img src='img/" + img + "' alt='头像'></div></div></div>";
    // 添加HTML到页面中
    var messages = $("#message");
    messages.append(sendMessageHtml);
    // 清空聊天窗口
    messageContent.val("");
    //聊天框默认最底部
    messages.scrollTop(messages[0].scrollHeight);
    // 返回发送的消息
    return message;
}

// 获取发送消息的时间字符串
function getSendTimeString() {
    var date = new Date();
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, "0");
    var day = date.getDate().toString().padStart(2, "0");
    var hour = date.getHours().toString().padStart(2, "0");
    var minute = date.getMinutes().toString().padStart(2, "0");
    var second = date.getSeconds().toString().padStart(2, "0");
    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}

// 接收消息的方法
function getMessage(username, avatar, message) {
    isEmpty = false;
    if (lastSendTime == null) {
        lastSendTime = Date.now();
    }
    // 30秒显示一次接收消息的时间
    if (lastSendTime + 30000 < Date.now()) {
        lastSendTime = null;
        makeTips(getSendTimeString());
    }
    message = stopHtmlInject(message);
    var messages = $("#message");
    var getMessageHtml = "<div class='clearfloat animated bounceInLeft'><div class='left'><div class='chat-avatars'><img src='img/" + avatar + "' alt='头像'></div><span class=\"username\">" + username + "</span><br><div class='chat-message'>" + message + "</div></div>";
    messages.append(getMessageHtml);
    //聊天框默认最底部
    messages.scrollTop(messages[0].scrollHeight);
}

// 进行WebSocket操作

var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    var url = window.location;
    url = url.host;
    websocket = new WebSocket("ws://" + url + "/chat");
} else {
    makeTips("当前浏览器不支持群发");
}

//连接发生错误的回调方法
websocket.onerror = function () {
    makeTips("无法连接到服务器");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    // 消息框中显示欢迎你的到来
    makeTips("欢迎你的到来!");
    // 小提示
    makeTips("DELETE键可以清屏!");
    $.post(
        "chat/getOnlineCount",
        {},
        function (res) {
            // 当前在线
            if (res <= 1) {
                makeTips("当前只有你自己在线!");
            } else {
                makeTips("当前在线" + res + "人");
            }
            // 显示当前时间
            makeTips(getSendTimeString());
        },
        "json"
    )
};

//接收到消息的回调方法
websocket.onmessage = function (event) {
    var userInfo = JSON.parse(event.data);
    getMessage(userInfo.username, userInfo.avatar, userInfo.message);
};

//连接关闭的回调方法
websocket.onclose = function () {
    makeTips("已关闭连接");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    websocket.close();
};

// 监听键盘事件
window.onkeydown = function (event) {
    // 发送信息
    if (window.event && event.key === "Enter") {
        var message = sendMessage();
        if (message != null) {
            var userInfo = {};
            userInfo.username = username;
            userInfo.avatar = img;
            userInfo.message = message;
            websocket.send(JSON.stringify(userInfo));
        }
    }
    // 清屏
    if (window.event && event.key === "Delete") {
        $("#message").html("");
        // 显示当前时间
        makeTips(getSendTimeString());
    }
};

// 前台防止HTML注入
function stopHtmlInject(message) {
    return message.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}
